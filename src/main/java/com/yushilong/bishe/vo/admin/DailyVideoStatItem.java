package com.yushilong.bishe.vo.admin;

import lombok.Data;

@Data
public class DailyVideoStatItem {
    private String day;   // yyyy-MM-dd
    private Long count;
}