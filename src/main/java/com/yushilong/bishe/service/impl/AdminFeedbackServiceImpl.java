package com.yushilong.bishe.service.impl;

import com.yushilong.bishe.mapper.AdminFeedbackMapper;
import com.yushilong.bishe.service.AdminFeedbackService;
import com.yushilong.bishe.vo.admin.AdminFeedbackItem;
import com.yushilong.bishe.vo.admin.AdminFeedbackStatsResponse;
import com.yushilong.bishe.vo.admin.FeedbackCorrectionTopItem;
import com.yushilong.bishe.vo.admin.FeedbackDailyStatItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminFeedbackServiceImpl implements AdminFeedbackService {

    private final AdminFeedbackMapper adminFeedbackMapper;

    @Override
    public List<AdminFeedbackItem> listFeedbacks(Integer limit) {
        int safeLimit = (limit == null || limit <= 0 || limit > 500) ? 100 : limit;
        return adminFeedbackMapper.listFeedbacks(safeLimit);
    }

    @Override
    public AdminFeedbackStatsResponse stats(Integer days, Integer topN) {
        int safeDays = (days == null || days <= 0 || days > 365) ? 7 : days;
        int safeTopN = (topN == null || topN <= 0 || topN > 50) ? 10 : topN;

        Long total = adminFeedbackMapper.countTotalFeedbacks();
        List<FeedbackDailyStatItem> daily = adminFeedbackMapper.dailyStats(safeDays);
        List<FeedbackCorrectionTopItem> top = adminFeedbackMapper.topCorrections(safeTopN);

        return new AdminFeedbackStatsResponse(total, daily, top);
    }
}