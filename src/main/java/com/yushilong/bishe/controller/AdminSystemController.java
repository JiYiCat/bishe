package com.yushilong.bishe.controller;

import com.yushilong.bishe.common.Result;
import com.yushilong.bishe.dto.system.AnnouncementCreateRequest;
import com.yushilong.bishe.dto.system.SystemConfigUpdateRequest;
import com.yushilong.bishe.dto.system.SystemConfigUpsertRequest;
import com.yushilong.bishe.entity.Announcement;
import com.yushilong.bishe.entity.SystemConfig;
import com.yushilong.bishe.entity.SystemLog;
import com.yushilong.bishe.security.RequireAdmin;
import com.yushilong.bishe.security.UserContext;
import com.yushilong.bishe.service.SystemSettingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/system")
@RequiredArgsConstructor
@RequireAdmin
public class AdminSystemController {

    private final SystemSettingService systemSettingService;

    @GetMapping("/configs")
    public Result<List<SystemConfig>> listConfigs() {
        return Result.success(systemSettingService.listConfigs());
    }

    @PutMapping("/configs/{configKey}")
    public Result<Void> updateConfig(@PathVariable("configKey") String configKey,
                                     @Valid @RequestBody SystemConfigUpdateRequest req) {
        Long operatorId = UserContext.getUserId();
        systemSettingService.updateConfig(configKey, req.getConfigValue(), operatorId);
        return Result.success();
    }

    @PutMapping("/configs")
    public Result<Void> updateConfigByBody(@Valid @RequestBody SystemConfigUpsertRequest req) {
        Long operatorId = UserContext.getUserId();
        systemSettingService.updateConfig(req.getConfigKey(), req.getConfigValue(), operatorId);
        return Result.success();
    }

    @GetMapping("/logs")
    public Result<List<SystemLog>> logs(@RequestParam(name = "limit", required = false) Integer limit) {
        return Result.success(systemSettingService.listLogs(limit));
    }

    @PostMapping("/announcements")
    public Result<Void> createAnnouncement(@Valid @RequestBody AnnouncementCreateRequest req) {
        Long operatorId = UserContext.getUserId();
        systemSettingService.createAnnouncement(req, operatorId);
        return Result.success();
    }

    @GetMapping("/announcements")
    public Result<List<Announcement>> listAnnouncements(@RequestParam(name = "limit", required = false) Integer limit) {
        return Result.success(systemSettingService.listAnnouncements(limit));
    }
}