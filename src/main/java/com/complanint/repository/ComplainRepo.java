package com.complanint.repository;

import com.complanint.Entity.Complain;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ComplainRepo {
    void save(Complain complain);

    Complain find(long userId);
}
