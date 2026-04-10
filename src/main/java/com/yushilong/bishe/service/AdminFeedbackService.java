package com.yushilong.bishe.service;

import com.yushilong.bishe.vo.admin.AdminFeedbackItem;
import com.yushilong.bishe.vo.admin.AdminFeedbackStatsResponse;

import java.util.List;

public interface AdminFeedbackService {

    List<AdminFeedbackItem> listFeedbacks(Integer limit);

    AdminFeedbackStatsResponse stats(Integer days, Integer topN);
}