package com.exfantasy.template.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.exfantasy.template.mybatis.model.UserRole;
import com.exfantasy.template.mybatis.model.UserRoleExample;

@Mapper
public interface UserRoleMapper {
    int insert(UserRole record);

    int insertSelective(UserRole record);

    List<UserRole> selectByExample(UserRoleExample example);
}