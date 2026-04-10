package com.yushilong.bishe.service.impl;

import com.yushilong.bishe.common.BusinessException;
import com.yushilong.bishe.common.ResultCode;
import com.yushilong.bishe.mapper.AdminVideoMapper;
import com.yushilong.bishe.service.AdminVideoService;
import com.yushilong.bishe.vo.admin.ActionTopStatItem;
import com.yushilong.bishe.vo.admin.AdminVideoListItem;
import com.yushilong.bishe.vo.admin.AdminVideoStatsResponse;
import com.yushilong.bishe.vo.admin.DailyVideoStatItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminVideoServiceImpl implements AdminVideoService {

    private final AdminVideoMapper adminVideoMapper;
    private static final Set<String> ALLOWED_STATUS = Set.of("PENDING", "PROCESSING", "DONE", "FAILED");

    @Override
    public List<AdminVideoListItem> listAllVideos() {
        return adminVideoMapper.listAllVideos();
    }

    @Override
    public void updateVideoStatus(Long videoId, String status) {
        if (!ALLOWED_STATUS.contains(status)) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "状态仅支持 PENDING/PROCESSING/DONE/FAILED");
        }
        if (adminVideoMapper.existsVideo(videoId) == 0) {
            throw new BusinessException(ResultCode.NOT_FOUND, "视频不存在");
        }

        int rows = adminVideoMapper.updateVideoStatus(videoId, status);
        if (rows <= 0) {
            throw new BusinessException(ResultCode.BUSINESS_ERROR, "更新视频状态失败");
        }
    }

    @Override
    public AdminVideoStatsResponse stats(Integer days, Integer topN) {
        int safeDays = (days == null || days <= 0 || days > 365) ? 7 : days;
        int safeTopN = (topN == null || topN <= 0 || topN > 50) ? 10 : topN;

        Long total = adminVideoMapper.countTotalVideos();
        Long done = adminVideoMapper.countDoneVideos();
        Long failed = adminVideoMapper.countFailedVideos();

        List<DailyVideoStatItem> daily = adminVideoMapper.dailyVideoStats(safeDays);
        List<ActionTopStatItem> topActions = adminVideoMapper.topActions(safeTopN);

        return new AdminVideoStatsResponse(total, done, failed, daily, topActions);
    }
}