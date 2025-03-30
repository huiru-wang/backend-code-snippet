package com.example.codesnippet.transaction.dao.mapper;

import com.example.codesnippet.transaction.dao.entity.UserAccountEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAccountMapper {

    UserAccountEntity selectByUserId(String userId);

    int updateBalance(String userId, Double balance);
}
