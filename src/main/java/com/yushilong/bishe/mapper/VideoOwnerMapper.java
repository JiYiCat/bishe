package com.yushilong.bishe.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface VideoOwnerMapper {

    @Select("SELECT COUNT(1) FROM video WHERE id=#{videoId}")
    long existsVideo(@Param("videoId") Long videoId);

    @Select("SELECT COUNT(1) FROM video WHERE id=#{videoId} AND user_id=#{userId}")
    long isOwner(@Param("videoId") Long videoId, @Param("userId") Long userId);

    @Select("SELECT final_action FROM behavior_result WHERE video_id=#{videoId} LIMIT 1")
    String findPredictedAction(@Param("videoId") Long videoId);

    @Delete("DELETE FROM behavior_result WHERE video_id=#{videoId}")
    int deleteBehaviorByVideoId(@Param("videoId") Long videoId);

    @Delete("DELETE FROM video WHERE id=#{videoId}")
    int deleteVideo(@Param("videoId") Long videoId);

    @Insert("INSERT INTO behavior_feedback(video_id,user_id,predicted_action,corrected_action,comment,created_at) " +
            "VALUES(#{videoId},#{userId},#{predictedAction},#{correctedAction},#{comment},NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertFeedback(com.yushilong.bishe.entity.BehaviorFeedback feedback);
}