package com.yushilong.bishe.mapper;

import com.yushilong.bishe.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("SELECT id,email,password_hash AS passwordHash,role,status,login_fail_count AS loginFailCount,locked_until AS lockedUntil,created_at AS createdAt,updated_at AS updatedAt " +
            "FROM sys_user WHERE email = #{email} LIMIT 1")
    User findByEmail(@Param("email") String email);

    @Insert("INSERT INTO sys_user(email,password_hash,role,status,login_fail_count,created_at,updated_at) " +
            "VALUES(#{email},#{passwordHash},#{role},#{status},#{loginFailCount},NOW(),NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Update("UPDATE sys_user SET login_fail_count=#{count}, updated_at=NOW() WHERE id=#{userId}")
    int updateLoginFailCount(@Param("userId") Long userId, @Param("count") Integer count);

    @Update("UPDATE sys_user SET login_fail_count=0, locked_until=NULL, updated_at=NOW() WHERE id=#{userId}")
    int resetLoginFail(@Param("userId") Long userId);
}