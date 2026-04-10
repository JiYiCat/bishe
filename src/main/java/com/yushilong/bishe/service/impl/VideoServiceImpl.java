package com.yushilong.bishe.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yushilong.bishe.common.BusinessException;
import com.yushilong.bishe.common.ResultCode;
import com.yushilong.bishe.dto.video.AiRecognizeResponse;
import com.yushilong.bishe.dto.video.BehaviorFeedbackRequest;
import com.yushilong.bishe.entity.BehaviorFeedback;
import com.yushilong.bishe.entity.BehaviorResult;
import com.yushilong.bishe.entity.Video;
import com.yushilong.bishe.mapper.BehaviorResultMapper;
import com.yushilong.bishe.mapper.VideoMapper;
import com.yushilong.bishe.mapper.VideoOwnerMapper;
import com.yushilong.bishe.security.UserContext;
import com.yushilong.bishe.service.VideoService;
import com.yushilong.bishe.vo.video.VideoHistoryItem;
import com.yushilong.bishe.vo.video.VideoUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    @Value("${app.upload-dir}")
    private String uploadDir;

    @Value("${app.ai-url}")
    private String aiUrl;

    private final VideoMapper videoMapper;
    private final BehaviorResultMapper behaviorResultMapper;
    private final VideoOwnerMapper videoOwnerMapper;
    private final ObjectMapper objectMapper;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public VideoUploadResponse uploadAndRecognize(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "上传文件不能为空");
        }

        Long userId = getCurrentUserId();

        try {
            Path dir = Paths.get(uploadDir).toAbsolutePath();
            Files.createDirectories(dir);

            String originalName = file.getOriginalFilename() == null ? "unknown.mp4" : file.getOriginalFilename();
            String filename = UUID.randomUUID() + "_" + originalName;
            Path target = dir.resolve(filename);
            file.transferTo(target.toFile());

            Video video = new Video();
            video.setUserId(userId);
            video.setOriginalName(originalName);
            video.setFilename(filename);
            video.setFilePath(target.toString());
            video.setFileSize(file.getSize());
            video.setStatus("PROCESSING");
            videoMapper.insert(video);

            AiRecognizeResponse aiResp = callAi(target.toFile());

            BehaviorResult result = new BehaviorResult();
            result.setVideoId(video.getId());
            result.setFinalAction(aiResp.getFinalAction());
            result.setCyclingScore(aiResp.getCyclingScore());
            result.setTopActionsJson(toJson(aiResp.getTopActions()));
            behaviorResultMapper.insert(result);

            videoMapper.updateStatus(video.getId(), "DONE");

            return new VideoUploadResponse(
                    video.getId(),
                    aiResp.getFinalAction(),
                    aiResp.getCyclingScore(),
                    aiResp.getTopActions()
            );
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(ResultCode.FILE_UPLOAD_ERROR, "视频处理失败：" + e.getMessage());
        }
    }

    @Override
    public List<VideoHistoryItem> myHistory() {
        Long userId = getCurrentUserId();
        return videoMapper.listByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMyVideo(Long videoId) {
        Long userId = getCurrentUserId();

        if (videoOwnerMapper.existsVideo(videoId) == 0) {
            throw new BusinessException(ResultCode.NOT_FOUND, "视频不存在");
        }
        if (videoOwnerMapper.isOwner(videoId, userId) == 0) {
            throw new BusinessException(ResultCode.FORBIDDEN, "无权删除他人视频");
        }

        videoOwnerMapper.deleteBehaviorByVideoId(videoId);
        int rows = videoOwnerMapper.deleteVideo(videoId);
        if (rows <= 0) {
            throw new BusinessException(ResultCode.BUSINESS_ERROR, "删除视频失败");
        }
    }

    @Override
    public void submitFeedback(Long videoId, BehaviorFeedbackRequest requestDto) {
        Long userId = getCurrentUserId();

        if (videoOwnerMapper.existsVideo(videoId) == 0) {
            throw new BusinessException(ResultCode.NOT_FOUND, "视频不存在");
        }
        if (videoOwnerMapper.isOwner(videoId, userId) == 0) {
            throw new BusinessException(ResultCode.FORBIDDEN, "无权反馈他人视频");
        }

        BehaviorFeedback feedback = new BehaviorFeedback();
        feedback.setVideoId(videoId);
        feedback.setUserId(userId);
        feedback.setPredictedAction(videoOwnerMapper.findPredictedAction(videoId));
        feedback.setCorrectedAction(requestDto.getCorrectedAction());
        feedback.setComment(requestDto.getComment());

        int rows = videoOwnerMapper.insertFeedback(feedback);
        if (rows <= 0) {
            throw new BusinessException(ResultCode.BUSINESS_ERROR, "提交反馈失败");
        }
    }

    private AiRecognizeResponse callAi(File file) {
        try {
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new FileSystemResource(file));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<AiRecognizeResponse> response =
                    restTemplate.postForEntity(aiUrl, entity, AiRecognizeResponse.class);

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new BusinessException(ResultCode.AI_INFER_ERROR, "AI服务返回异常");
            }
            return response.getBody();
        } catch (Exception e) {
            throw new BusinessException(ResultCode.AI_INFER_ERROR, "调用AI失败：" + e.getMessage());
        }
    }

    private String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return "[]";
        }
    }

    private Long getCurrentUserId() {
        Long uid = UserContext.getUserId();
        if (uid == null) {
            throw new BusinessException(ResultCode.UNAUTHORIZED, "未登录");
        }
        return uid;
    }
}