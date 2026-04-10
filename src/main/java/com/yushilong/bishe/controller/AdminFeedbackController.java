package com.yushilong.bishe.controller;

import com.yushilong.bishe.common.Result;
import com.yushilong.bishe.security.RequireAdmin;
import com.yushilong.bishe.service.AdminFeedbackService;
import com.yushilong.bishe.vo.admin.AdminFeedbackItem;
import com.yushilong.bishe.vo.admin.AdminFeedbackStatsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/feedbacks")
@RequiredArgsConstructor
@RequireAdmin
public class AdminFeedbackController {

    private final AdminFeedbackService adminFeedbackService;

    @GetMapping
    public Result<List<AdminFeedbackItem>> list(@RequestParam(name = "limit", required = false) Integer limit) {
        return Result.success(adminFeedbackService.listFeedbacks(limit));
    }

    @GetMapping("/stats")
    public Result<AdminFeedbackStatsResponse> stats(
            @RequestParam(name = "days", required = false) Integer days,
            @RequestParam(name = "topN", required = false) Integer topN
    ) {
        return Result.success(adminFeedbackService.stats(days, topN));
    }
}