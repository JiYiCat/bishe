package com.yushilong.bishe.vo.video;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VideoUploadResponse {
    private Long videoId;
    private String finalAction;
    private Double cyclingScore;
    private Object topActions;
}