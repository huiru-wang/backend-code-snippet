use code_snippet;

SHOW tables;

DESC code_snippet.t_user;

DESC code_snippet.t_order;

DESC code_snippet.t_order_detail;

INSERT INTO code_snippet.t_user (id, user_id, username, password,phone_no, email, created_at, updated_at) 
VALUES (1, 'admin', 'admin','admin','16666666666','admin@gmail.com', NOW(), NOW());

select * from code_snippet.t_user;