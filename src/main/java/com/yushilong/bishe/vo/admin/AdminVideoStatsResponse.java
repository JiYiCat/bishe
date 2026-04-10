package com.yushilong.bishe.vo.admin;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AdminVideoStatsResponse {
    private Long totalVideos;
    private Long doneVideos;
    private Long failedVideos;
    private List<DailyVideoStatItem> dailyStats;
    private List<ActionTopStatItem> topActions;
}