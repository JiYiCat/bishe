package com.yushilong.bishe.mapper;

import com.yushilong.bishe.entity.Announcement;
import com.yushilong.bishe.entity.SystemConfig;
import com.yushilong.bishe.entity.SystemLog;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SystemSettingMapper {

    @Select("SELECT id, config_key AS configKey, config_value AS configValue, description, updated_at AS updatedAt FROM system_config ORDER BY id ASC")
    List<SystemConfig> listConfigs();

    @Select("SELECT COUNT(1) FROM system_config WHERE config_key=#{configKey}")
    long existsConfigKey(@Param("configKey") String configKey);

    @Update("UPDATE system_config SET config_value=#{configValue}, updated_at=NOW() WHERE config_key=#{configKey}")
    int updateConfig(@Param("configKey") String configKey, @Param("configValue") String configValue);

    @Insert("INSERT INTO system_log(level,module,content,operator_id,created_at) VALUES(#{level},#{module},#{content},#{operatorId},NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertLog(SystemLog log);

    @Select("""
        SELECT id, level, module, content, operator_id AS operatorId, created_at AS createdAt
        FROM system_log
        ORDER BY id DESC
        LIMIT #{limit}
        """)
    List<SystemLog> listLogs(@Param("limit") Integer limit);

    @Insert("INSERT INTO announcement(title,content,lang,status,created_by,created_at) VALUES(#{title},#{content},#{lang},#{status},#{createdBy},NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertAnnouncement(Announcement announcement);

    @Select("""
        SELECT id,title,content,lang,status,created_by AS createdBy,created_at AS createdAt
        FROM announcement
        ORDER BY id DESC
        LIMIT #{limit}
        """)
    List<Announcement> listAnnouncements(@Param("limit") Integer limit);
}