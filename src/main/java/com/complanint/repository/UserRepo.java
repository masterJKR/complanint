package com.complanint.repository;

import com.complanint.Entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepo {
    public int save(User user);

    User findById(String userId);
}
