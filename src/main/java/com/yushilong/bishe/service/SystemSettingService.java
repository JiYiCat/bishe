package com.yushilong.bishe.service;

import com.yushilong.bishe.dto.system.AnnouncementCreateRequest;
import com.yushilong.bishe.entity.Announcement;
import com.yushilong.bishe.entity.SystemConfig;
import com.yushilong.bishe.entity.SystemLog;

import java.util.List;

public interface SystemSettingService {

    List<SystemConfig> listConfigs();

    void updateConfig(String configKey, String configValue, Long operatorId);

    List<SystemLog> listLogs(Integer limit);

    void createAnnouncement(AnnouncementCreateRequest request, Long operatorId);

    List<Announcement> listAnnouncements(Integer limit);
}