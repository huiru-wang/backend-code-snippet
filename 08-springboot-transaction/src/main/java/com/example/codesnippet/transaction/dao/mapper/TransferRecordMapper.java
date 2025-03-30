package com.example.codesnippet.transaction.dao.mapper;

import com.example.codesnippet.transaction.dao.entity.TransferRecordEntity;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

@Mapper
public interface TransferRecordMapper {

    TransferRecordEntity selectByRecordId(String recordId);

    int insert(TransferRecordEntity transferRecordEntity);

    int updateStatus(String recordId, String status, LocalDateTime updateAt);
}
