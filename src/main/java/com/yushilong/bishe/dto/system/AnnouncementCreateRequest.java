package com.yushilong.bishe.dto.system;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AnnouncementCreateRequest {

    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "内容不能为空")
    private String content;

    @NotBlank(message = "语言不能为空")
    private String lang; // zh/en

    @NotNull(message = "状态不能为空")
    private Integer status; // 0=草稿,1=发布
}