package com.example.codesnippet.transaction.domain;

import com.example.codesnippet.transaction.dao.entity.TransferRecordEntity;
import com.example.codesnippet.transaction.dao.entity.UserAccountEntity;
import com.example.codesnippet.transaction.dao.mapper.TransferRecordMapper;
import com.example.codesnippet.transaction.dao.mapper.UserAccountMapper;
import com.example.codesnippet.transaction.utils.TransferUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;

@Slf4j
@Service
public class TransferService {

    @Resource
    private UserAccountMapper userAccountMapper;

    @Resource
    private TransferRecordMapper transferRecordMapper;

    public TransferRecordEntity queryTransferRecord(String recordId) {
        return transferRecordMapper.selectByRecordId(recordId);
    }

    /**
     * transfer所有写操作回滚：包括转账记录的创建、用户余额的更新、转账记录的更新
     */
    @Transactional(
            rollbackFor = Throwable.class,
            propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            timeout = 30
    )
    public String transferWithAnnotation(String fromUserId, String toUserId, Double amount, boolean rollback) {
        // 初始化记录
        TransferRecordEntity transferRecordEntity = TransferUtils.buildTransferInitRecord(fromUserId, toUserId, amount);
        transferRecordMapper.insert(transferRecordEntity);
        String recordId = transferRecordEntity.getRecordId();

        doTransfer(fromUserId, toUserId, amount);

        transferRecordMapper.updateStatus(recordId, "SUCCESS", LocalDateTime.now());
        if (rollback) {
            throw new RuntimeException("rollback");
        }

        return recordId;
    }

    @Resource
    private TransactionTemplate transactionTemplate;

    /**
     * 使用transactionTemplate
     */
    public String transferByTransactionTemplate(String fromUserId, String toUserId, Double amount, boolean rollback) {

        // 初始化记录
        TransferRecordEntity transferRecordEntity = TransferUtils.buildTransferInitRecord(fromUserId, toUserId, amount);
        transferRecordMapper.insert(transferRecordEntity);
        String recordId = transferRecordEntity.getRecordId();

        // 事务如果失败，只回滚：金额的扣减、转账记录的更新
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.setTimeout(30); // 超时时间为 30 秒
        transactionTemplate.executeWithoutResult(status -> {
            try {
                doTransfer(fromUserId, toUserId, amount);
            } catch (Throwable e) {
                log.error("transfer rollback");
                status.setRollbackOnly();
                transferRecordMapper.updateStatus(recordId, "FAIL", LocalDateTime.now());
            }
        });
        return recordId;
    }

    @Resource
    private PlatformTransactionManager transactionManager;

    /**
     * 使用transactionManager，好处是更灵活，可以在失败回滚后，增加业务逻辑，需要手动控制事务
     */
    public String transferByTransactionManager(String fromUserId, String toUserId, Double amount, boolean rollback) {

        // 初始化记录
        TransferRecordEntity transferRecordEntity = TransferUtils.buildTransferInitRecord(fromUserId, toUserId, amount);
        transferRecordMapper.insert(transferRecordEntity);
        String recordId = transferRecordEntity.getRecordId();

        // 事务如果失败，只回滚：金额的扣减、转账记录的更新
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        def.setTimeout(30);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            doTransfer(fromUserId, toUserId, amount);
            transferRecordMapper.updateStatus(recordId, "SUCCESS", LocalDateTime.now());
            if (rollback) {
                throw new RuntimeException("rollback");
            }
            transactionManager.commit(status);
        } catch (Exception e) {
            log.error("transfer rollback");
            // 下面rollback执行完，就会回滚当前事务的操作，而后面的update fail会正常执行，不会被回滚，
            transactionManager.rollback(status);
            transferRecordMapper.updateStatus(recordId, "FAIL", LocalDateTime.now());
        }
        return recordId;
    }

    private void doTransfer(String fromUserId, String toUserId, Double amount) {
        // 查询
        UserAccountEntity fromUser = userAccountMapper.selectByUserId(fromUserId);
        UserAccountEntity toUser = userAccountMapper.selectByUserId(toUserId);

        // 转账
        Double fromUserBalance = fromUser.getBalance() - amount;
        Double toUserBalance = toUser.getBalance() + amount;
        userAccountMapper.updateBalance(fromUser.getUserId(), fromUserBalance);
        userAccountMapper.updateBalance(toUser.getUserId(), toUserBalance);
        throw new RuntimeException("rollback");
    }
}
