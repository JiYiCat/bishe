package com.yushilong.bishe.mapper;

import com.yushilong.bishe.entity.Video;
import com.yushilong.bishe.vo.video.VideoHistoryItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VideoMapper {

    @Insert("INSERT INTO video(user_id,original_name,filename,file_path,file_size,status,uploaded_at,updated_at) " +
            "VALUES(#{userId},#{originalName},#{filename},#{filePath},#{fileSize},#{status},NOW(),NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Video video);

    @Update("UPDATE video SET status=#{status}, updated_at=NOW() WHERE id=#{videoId}")
    int updateStatus(@Param("videoId") Long videoId, @Param("status") String status);

    @Select("SELECT v.id AS videoId, v.original_name AS originalName, v.status, v.uploaded_at AS uploadedAt, " +
            "r.final_action AS finalAction, r.cycling_score AS cyclingScore, r.top_actions_json AS topActionsJson " +
            "FROM video v LEFT JOIN behavior_result r ON v.id = r.video_id " +
            "WHERE v.user_id = #{userId} ORDER BY v.id DESC")
    List<VideoHistoryItem> listByUserId(@Param("userId") Long userId);
}