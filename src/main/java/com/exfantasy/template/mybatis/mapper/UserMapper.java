package com.exfantasy.template.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.exfantasy.template.mybatis.model.User;
import com.exfantasy.template.mybatis.model.UserExample;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}