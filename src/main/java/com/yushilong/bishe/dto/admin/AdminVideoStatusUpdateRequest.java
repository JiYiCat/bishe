package com.yushilong.bishe.dto.admin;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdminVideoStatusUpdateRequest {

    @NotBlank(message = "状态不能为空")
    private String status; // PENDING / PROCESSING / DONE / FAILED
}