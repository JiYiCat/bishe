package com.yushilong.bishe.vo.admin;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AdminFeedbackStatsResponse {
    private Long totalFeedbacks;
    private List<FeedbackDailyStatItem> dailyStats;
    private List<FeedbackCorrectionTopItem> topCorrections;
}