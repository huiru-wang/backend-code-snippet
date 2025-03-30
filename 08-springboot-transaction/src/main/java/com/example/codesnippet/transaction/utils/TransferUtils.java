package com.example.codesnippet.transaction.utils;

import com.example.codesnippet.transaction.dao.entity.TransferRecordEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class TransferUtils {

    public static TransferRecordEntity buildTransferInitRecord(String fromUserId, String toUserId, Double amount) {
        TransferRecordEntity transferRecordEntity = new TransferRecordEntity();
        transferRecordEntity.setRecordId(UUID.randomUUID().toString().replace("-", ""));
        transferRecordEntity.setFromUserId(fromUserId);
        transferRecordEntity.setToUserId(toUserId);
        transferRecordEntity.setAmount(amount);
        transferRecordEntity.setStatus("INIT");
        transferRecordEntity.setTransferAt(LocalDateTime.now());
        transferRecordEntity.setCreateAt(LocalDateTime.now());
        transferRecordEntity.setUpdateAt(LocalDateTime.now());
        return transferRecordEntity;
    }


}
