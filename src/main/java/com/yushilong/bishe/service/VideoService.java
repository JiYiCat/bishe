package com.yushilong.bishe.service;

import com.yushilong.bishe.dto.video.BehaviorFeedbackRequest;
import com.yushilong.bishe.vo.video.VideoHistoryItem;
import com.yushilong.bishe.vo.video.VideoUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {
    VideoUploadResponse uploadAndRecognize(MultipartFile file);
    List<VideoHistoryItem> myHistory();

    // 合并新增
    void deleteMyVideo(Long videoId);
    void submitFeedback(Long videoId, BehaviorFeedbackRequest request);
}