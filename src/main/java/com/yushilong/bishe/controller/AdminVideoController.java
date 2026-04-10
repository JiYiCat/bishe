package com.yushilong.bishe.controller;

import com.yushilong.bishe.common.Result;
import com.yushilong.bishe.dto.admin.AdminVideoStatusUpdateRequest;
import com.yushilong.bishe.security.RequireAdmin;
import com.yushilong.bishe.service.AdminVideoService;
import com.yushilong.bishe.vo.admin.AdminVideoListItem;
import com.yushilong.bishe.vo.admin.AdminVideoStatsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/videos")
@RequiredArgsConstructor
@RequireAdmin
public class AdminVideoController {

    private final AdminVideoService adminVideoService;

    @GetMapping
    public Result<List<AdminVideoListItem>> list() {
        return Result.success(adminVideoService.listAllVideos());
    }

    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable("id") Long videoId,
                                     @Valid @RequestBody AdminVideoStatusUpdateRequest req) {
        adminVideoService.updateVideoStatus(videoId, req.getStatus());
        return Result.success();
    }

    @GetMapping("/stats")
    public Result<AdminVideoStatsResponse> stats(
            @RequestParam(name = "days", required = false) Integer days,
            @RequestParam(name = "topN", required = false) Integer topN
    ) {
        return Result.success(adminVideoService.stats(days, topN));
    }
}