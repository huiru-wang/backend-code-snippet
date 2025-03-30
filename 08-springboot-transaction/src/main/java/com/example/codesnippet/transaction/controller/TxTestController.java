package com.example.codesnippet.transaction.controller;

import com.example.codesnippet.transaction.dao.entity.TransferRecordEntity;
import com.example.codesnippet.transaction.domain.TransferService;
import com.example.codesnippet.transaction.model.ServiceResult;
import com.example.codesnippet.transaction.model.TransferRequest;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/")
public class TxTestController {

    @Resource
    private TransferService transferService;

    @GetMapping("/query/{recordId}")
    public TransferRecordEntity queryTransferRecord(@PathVariable String recordId) {
        return transferService.queryTransferRecord(recordId);
    }

    @PostMapping("/txByAnnotation")
    public ServiceResult<String> transferSuccess(@RequestBody TransferRequest transferRequest) {
        String fromUserId = transferRequest.getFromUserId();
        String toUserId = transferRequest.getToUserId();
        Double amount = transferRequest.getAmount();
        boolean rollback = transferRequest.isRollback();
        String transferRecordId = transferService.transferWithAnnotation(fromUserId, toUserId, amount, rollback);
        return ServiceResult.success(transferRecordId);
    }

    @PostMapping("/txByTemplate")
    public ServiceResult<String> transferByTransactionTemplate(@RequestBody TransferRequest transferRequest) {
        String fromUserId = transferRequest.getFromUserId();
        String toUserId = transferRequest.getToUserId();
        Double amount = transferRequest.getAmount();
        boolean rollback = transferRequest.isRollback();
        String transferRecordId = transferService.transferByTransactionTemplate(fromUserId, toUserId, amount, rollback);
        return ServiceResult.success(transferRecordId);
    }

    @PostMapping("/txByManager")
    public ServiceResult<String> transferByTransactionManager(@RequestBody TransferRequest transferRequest) {
        String fromUserId = transferRequest.getFromUserId();
        String toUserId = transferRequest.getToUserId();
        Double amount = transferRequest.getAmount();
        boolean rollback = transferRequest.isRollback();
        String transferRecordId = transferService.transferByTransactionManager(fromUserId, toUserId, amount, rollback);
        return ServiceResult.success(transferRecordId);
    }
}
