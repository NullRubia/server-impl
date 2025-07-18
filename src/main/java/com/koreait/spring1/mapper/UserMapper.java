package com.koreait.spring1.mapper;

import com.koreait.spring1.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void insertUser(User user);
    User findByUserid(String userid);

    void updateUser(User user);
}