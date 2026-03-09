package com.complanint.repository;

import com.complanint.Entity.ComplainFile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageRepo {
    void save(ComplainFile complainFile);
}
