create table user
(
    id        bigint auto_increment
        primary key,
    email     varchar(255) null,
    enabled   bit          null,
    locked    bit          null,
    password  varchar(255) null,
    user_role int          null
);

INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (1, 'michaelstoelting@gmail.com', true, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 1);
