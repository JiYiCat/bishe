package com.yushilong.bishe.service;

import com.yushilong.bishe.vo.admin.AdminVideoListItem;
import com.yushilong.bishe.vo.admin.AdminVideoStatsResponse;

import java.util.List;

public interface AdminVideoService {

    List<AdminVideoListItem> listAllVideos();

    void updateVideoStatus(Long videoId, String status);

    AdminVideoStatsResponse stats(Integer days, Integer topN);
}