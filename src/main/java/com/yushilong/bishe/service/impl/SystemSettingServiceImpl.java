package com.yushilong.bishe.service.impl;

import com.yushilong.bishe.common.BusinessException;
import com.yushilong.bishe.common.ResultCode;
import com.yushilong.bishe.dto.system.AnnouncementCreateRequest;
import com.yushilong.bishe.entity.Announcement;
import com.yushilong.bishe.entity.SystemLog;
import com.yushilong.bishe.entity.SystemConfig;
import com.yushilong.bishe.mapper.SystemSettingMapper;
import com.yushilong.bishe.service.SystemSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SystemSettingServiceImpl implements SystemSettingService {

    private final SystemSettingMapper systemSettingMapper;
    private static final Set<String> LANG_SET = Set.of("zh", "en");

    @Override
    public List<SystemConfig> listConfigs() {
        return systemSettingMapper.listConfigs();
    }

    @Override
    public void updateConfig(String configKey, String configValue, Long operatorId) {
        if (systemSettingMapper.existsConfigKey(configKey) == 0) {
            throw new BusinessException(ResultCode.NOT_FOUND, "配置项不存在");
        }
        int rows = systemSettingMapper.updateConfig(configKey, configValue);
        if (rows <= 0) {
            throw new BusinessException(ResultCode.BUSINESS_ERROR, "更新配置失败");
        }

        SystemLog log = new SystemLog();
        log.setLevel("INFO");
        log.setModule("SYSTEM");
        log.setContent("更新系统配置: key=" + configKey);
        log.setOperatorId(operatorId);
        systemSettingMapper.insertLog(log);
    }

    @Override
    public List<SystemLog> listLogs(Integer limit) {
        int safeLimit = (limit == null || limit <= 0 || limit > 500) ? 100 : limit;
        return systemSettingMapper.listLogs(safeLimit);
    }

    @Override
    public void createAnnouncement(AnnouncementCreateRequest request, Long operatorId) {
        if (!LANG_SET.contains(request.getLang())) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "语言仅支持 zh 或 en");
        }
        if (request.getStatus() != 0 && request.getStatus() != 1) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "状态仅支持 0 或 1");
        }

        Announcement announcement = new Announcement();
        announcement.setTitle(request.getTitle());
        announcement.setContent(request.getContent());
        announcement.setLang(request.getLang());
        announcement.setStatus(request.getStatus());
        announcement.setCreatedBy(operatorId);

        int rows = systemSettingMapper.insertAnnouncement(announcement);
        if (rows <= 0) {
            throw new BusinessException(ResultCode.BUSINESS_ERROR, "公告创建失败");
        }

        SystemLog log = new SystemLog();
        log.setLevel("INFO");
        log.setModule("SYSTEM");
        log.setContent("创建系统公告: title=" + request.getTitle());
        log.setOperatorId(operatorId);
        systemSettingMapper.insertLog(log);
    }

    @Override
    public List<Announcement> listAnnouncements(Integer limit) {
        int safeLimit = (limit == null || limit <= 0 || limit > 200) ? 50 : limit;
        return systemSettingMapper.listAnnouncements(safeLimit);
    }
}