package com.yushilong.bishe.dto.video;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BehaviorFeedbackRequest {

    @NotBlank(message = "纠正行为不能为空")
    private String correctedAction;

    private String comment;
}