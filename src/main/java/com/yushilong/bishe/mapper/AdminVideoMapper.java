package com.yushilong.bishe.mapper;

import com.yushilong.bishe.vo.admin.ActionTopStatItem;
import com.yushilong.bishe.vo.admin.AdminVideoListItem;
import com.yushilong.bishe.vo.admin.DailyVideoStatItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminVideoMapper {

    @Select("""
        SELECT
            v.id AS videoId,
            v.user_id AS userId,
            u.email AS userEmail,
            v.original_name AS originalName,
            v.status,
            v.file_size AS fileSize,
            v.uploaded_at AS uploadedAt,
            br.final_action AS finalAction,
            br.cycling_score AS cyclingScore
        FROM video v
        LEFT JOIN sys_user u ON v.user_id = u.id
        LEFT JOIN behavior_result br ON br.video_id = v.id
        ORDER BY v.id DESC
        """)
    List<AdminVideoListItem> listAllVideos();

    @Select("SELECT COUNT(1) FROM video WHERE id=#{videoId}")
    long existsVideo(@Param("videoId") Long videoId);

    @Update("UPDATE video SET status=#{status}, updated_at=NOW() WHERE id=#{videoId}")
    int updateVideoStatus(@Param("videoId") Long videoId, @Param("status") String status);

    @Select("SELECT COUNT(1) FROM video")
    Long countTotalVideos();

    @Select("SELECT COUNT(1) FROM video WHERE status='DONE'")
    Long countDoneVideos();

    @Select("SELECT COUNT(1) FROM video WHERE status='FAILED'")
    Long countFailedVideos();

    @Select("""
        SELECT DATE_FORMAT(uploaded_at, '%Y-%m-%d') AS day, COUNT(*) AS count
        FROM video
        GROUP BY DATE_FORMAT(uploaded_at, '%Y-%m-%d')
        ORDER BY day DESC
        LIMIT #{days}
        """)
    List<DailyVideoStatItem> dailyVideoStats(@Param("days") Integer days);

    @Select("""
        SELECT final_action AS action, COUNT(*) AS count
        FROM behavior_result
        WHERE final_action IS NOT NULL AND final_action != ''
        GROUP BY final_action
        ORDER BY count DESC
        LIMIT #{topN}
        """)
    List<ActionTopStatItem> topActions(@Param("topN") Integer topN);
}