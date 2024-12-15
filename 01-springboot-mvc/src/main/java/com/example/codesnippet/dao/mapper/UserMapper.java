package com.example.codesnippet.dao.mapper;

import com.example.codesnippet.dao.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    /**
     * 插入
     */
    int insert(@Param("userEntity") UserEntity userEntity);

    /**
     * 查询
     */
    UserEntity selectByUserId(@Param("userId") String userId);
}
