package com.yushilong.bishe.mapper;

import com.yushilong.bishe.vo.admin.AdminFeedbackItem;
import com.yushilong.bishe.vo.admin.FeedbackCorrectionTopItem;
import com.yushilong.bishe.vo.admin.FeedbackDailyStatItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminFeedbackMapper {

    @Select("""
        SELECT
            bf.id,
            bf.video_id AS videoId,
            bf.user_id AS userId,
            u.email AS userEmail,
            bf.predicted_action AS predictedAction,
            bf.corrected_action AS correctedAction,
            bf.comment,
            bf.created_at AS createdAt
        FROM behavior_feedback bf
        LEFT JOIN sys_user u ON bf.user_id = u.id
        ORDER BY bf.id DESC
        LIMIT #{limit}
        """)
    List<AdminFeedbackItem> listFeedbacks(@Param("limit") Integer limit);

    @Select("SELECT COUNT(1) FROM behavior_feedback")
    Long countTotalFeedbacks();

    @Select("""
        SELECT DATE_FORMAT(created_at, '%Y-%m-%d') AS day, COUNT(*) AS count
        FROM behavior_feedback
        GROUP BY DATE_FORMAT(created_at, '%Y-%m-%d')
        ORDER BY day DESC
        LIMIT #{days}
        """)
    List<FeedbackDailyStatItem> dailyStats(@Param("days") Integer days);

    @Select("""
        SELECT corrected_action AS correctedAction, COUNT(*) AS count
        FROM behavior_feedback
        WHERE corrected_action IS NOT NULL AND corrected_action != ''
        GROUP BY corrected_action
        ORDER BY count DESC
        LIMIT #{topN}
        """)
    List<FeedbackCorrectionTopItem> topCorrections(@Param("topN") Integer topN);
}