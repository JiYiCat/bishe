package com.yushilong.bishe.mapper;

import com.yushilong.bishe.vo.admin.AdminUserListItem;
import com.yushilong.bishe.vo.admin.UserBehaviorStatItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminUserMapper {

    @Select("""
        SELECT
            u.id,
            u.email,
            u.role,
            u.status,
            u.login_fail_count AS loginFailCount,
            u.locked_until AS lockedUntil,
            u.created_at AS createdAt,
            IFNULL(v.video_count, 0) AS videoCount
        FROM sys_user u
        LEFT JOIN (
            SELECT user_id, COUNT(*) AS video_count
            FROM video
            GROUP BY user_id
        ) v ON u.id = v.user_id
        ORDER BY u.id DESC
        """)
    List<AdminUserListItem> listUsersWithVideoCount();

    @Update("UPDATE sys_user SET role=#{role}, status=#{status}, updated_at=NOW() WHERE id=#{id}")
    int updateUser(@Param("id") Long id, @Param("role") String role, @Param("status") Integer status);

    @Select("SELECT COUNT(1) FROM sys_user WHERE id=#{id}")
    long existsById(@Param("id") Long id);

    @Delete("DELETE FROM behavior_result WHERE video_id IN (SELECT id FROM video WHERE user_id=#{userId})")
    int deleteBehaviorByUserId(@Param("userId") Long userId);

    @Delete("DELETE FROM video WHERE user_id=#{userId}")
    int deleteVideoByUserId(@Param("userId") Long userId);

    @Delete("DELETE FROM sys_user WHERE id=#{userId}")
    int deleteUserById(@Param("userId") Long userId);

    @Select("""
        SELECT
            br.final_action AS action,
            COUNT(*) AS count
        FROM behavior_result br
        INNER JOIN video v ON br.video_id = v.id
        WHERE v.user_id = #{userId}
        GROUP BY br.final_action
        ORDER BY count DESC
        """)
    List<UserBehaviorStatItem> userBehaviorStats(@Param("userId") Long userId);
}