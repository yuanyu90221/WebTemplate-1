package com.exfantasy.template.mybatis.mapper;

import com.exfantasy.template.mybatis.model.UserRole;
import com.exfantasy.template.mybatis.model.UserRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface UserRoleMapper {
    @Insert({
        "insert into user_roles (user_id, role)",
        "values (#{userId,jdbcType=INTEGER}, #{role,jdbcType=VARCHAR})"
    })
    int insert(UserRole record);

    @InsertProvider(type=UserRoleSqlProvider.class, method="insertSelective")
    int insertSelective(UserRole record);

    @SelectProvider(type=UserRoleSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="role", property="role", jdbcType=JdbcType.VARCHAR)
    })
    List<UserRole> selectByExample(UserRoleExample example);
}