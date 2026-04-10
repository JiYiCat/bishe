package com.yushilong.bishe.vo.admin;

import lombok.Data;

/**
 * 用户行为统计项
 */
@Data
public class UserBehaviorStatItem {
    private String action;
    private Long count;
}