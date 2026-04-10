package com.yushilong.bishe.dto.video;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AiRecognizeResponse {

    @JsonProperty("final_action")
    private String finalAction;

    @JsonProperty("cycling_score")
    private Double cyclingScore;

    @JsonProperty("top_actions")
    private List<Map<String, Object>> topActions;
}