package com.yushilong.bishe.mapper;

import com.yushilong.bishe.entity.BehaviorResult;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface BehaviorResultMapper {

    @Insert("INSERT INTO behavior_result(video_id,final_action,cycling_score,top_actions_json,created_at) " +
            "VALUES(#{videoId},#{finalAction},#{cyclingScore},#{topActionsJson},NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(BehaviorResult result);
}