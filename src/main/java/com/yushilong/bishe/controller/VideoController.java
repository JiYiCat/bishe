package com.yushilong.bishe.controller;

import com.yushilong.bishe.common.Result;
import com.yushilong.bishe.dto.video.BehaviorFeedbackRequest;
import com.yushilong.bishe.security.RequireLogin;
import com.yushilong.bishe.service.VideoService;
import com.yushilong.bishe.vo.video.VideoHistoryItem;
import com.yushilong.bishe.vo.video.VideoUploadResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/video")
@RequiredArgsConstructor
@RequireLogin
public class VideoController {

    private final VideoService videoService;

    @PostMapping("/upload")
    public Result<VideoUploadResponse> upload(@RequestPart("file") MultipartFile file) {
        return Result.success(videoService.uploadAndRecognize(file));
    }

    @GetMapping("/history")
    public Result<List<VideoHistoryItem>> history() {
        return Result.success(videoService.myHistory());
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteMyVideo(@PathVariable("id") Long videoId) {
        videoService.deleteMyVideo(videoId);
        return Result.success();
    }

    @PostMapping("/{id}/feedback")
    public Result<Void> feedback(@PathVariable("id") Long videoId,
                                 @Valid @RequestBody BehaviorFeedbackRequest request) {
        videoService.submitFeedback(videoId, request);
        return Result.success();
    }
}