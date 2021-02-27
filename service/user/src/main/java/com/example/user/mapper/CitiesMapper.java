package com.example.user.mapper;

import com.example.userApi.domain.Cities;

public interface CitiesMapper {
    int deleteByPrimaryKey(String cityid);

    int insert(Cities record);

    int insertSelective(Cities record);

    Cities selectByPrimaryKey(String cityid);

    int updateByPrimaryKeySelective(Cities record);

    int updateByPrimaryKey(Cities record);
}