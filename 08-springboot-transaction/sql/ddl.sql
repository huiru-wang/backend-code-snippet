-- 用户账户表
CREATE TABLE t_user_account
(
    id        INT PRIMARY KEY AUTO_INCREMENT,
    user_id   VARCHAR(128)   NOT NULL,
    balance   DECIMAL(10, 2) NOT NULL,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user_id)
);

-- 转账记录表
CREATE TABLE t_transfer_record
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    record_id    VARCHAR(128)   NOT NULL,
    from_user_id VARCHAR(128)   NOT NULL,
    to_user_id   VARCHAR(128)   NOT NULL,
    amount       DECIMAL(10, 2) NOT NULL,
    status       VARCHAR(32)    NOT NULL,
    transfer_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
    create_at    DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_at    DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (record_id)
);