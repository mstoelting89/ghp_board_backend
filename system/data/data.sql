USE ghp_board;

INSERT INTO ghp_board.news (id, news_author, news_date, news_image_id, news_text, news_title) VALUES (1, 'Michael', '2022-06-01 21:16:20', null, 'Foo Bar', 'Dies ist der dritte Titel');
INSERT INTO ghp_board.news (id, news_author, news_date, news_image_id, news_text, news_title) VALUES (2, 'Lars', '2022-05-01 21:16:20', null, 'Dies ist ein Beispieltext', 'Dies ist der zweite Titel');
INSERT INTO ghp_board.news (id, news_author, news_date, news_image_id, news_text, news_title) VALUES (3, 'Svenja', '2022-04-01 21:16:20', null, 'Das ist noch ein Beispieltext', 'Dies ist der erste Titel');

INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (1, 'michaelstoelting@gmail.com', true, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 0);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (2, 'lars@gmail.com', true, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 1);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (3, 'svenja@gmail.com', true, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 1);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (4, 'test4@gmail.com', false, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 0);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (5, 'test5@gmail.com', true, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 1);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (6, 'test6@gmail.com', true, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 2);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (7, 'test7@gmail.com', false, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 0);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (8, 'test8@gmail.com', true, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 1);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (9, 'test9@gmail.com', false, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 2);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (10, 'test10@gmail.com', true, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 0);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (11, 'test11@gmail.com', true, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 1);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (12, 'test12@gmail.com', false, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 2);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (13, 'test13@gmail.com', true, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 0);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (14, 'test14@gmail.com', false, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 1);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (15, 'test15@gmail.com', false, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 2);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (16, 'test16@gmail.com', true, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 0);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (17, 'test17@gmail.com', false, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 2);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (18, 'test18@gmail.com', true, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 1);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (19, 'test19@gmail.com', false, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 2);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (20, 'test20@gmail.com', true, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 1);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (21, 'test21@gmail.comm', true, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 1);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (22, 'test22@gmail.com', false, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 2);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (23, 'test23@gmail.com', true, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 1);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (24, 'test24@gmail.com', false, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 2);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (25, 'test25@gmail.com', true, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 2);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (26, 'test26@gmail.com', false, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 2);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (27, 'test27@gmail.com', true, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 2);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (28, 'test28@gmail.com', false, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 2);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (29, 'test29@gmail.com', true, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 2);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (30, 'test30@gmail.com', true, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 1);



INSERT INTO ghp_board.demand (id, demand_title, demand_text, demand_date, demand_name) VALUES (1, 'Test Anfrage 1', 'Dies ist ein Beispiel', '2022-06-01 21:16:20', 'Lars');
INSERT INTO ghp_board.demand (id, demand_title, demand_text, demand_date, demand_name) VALUES (2, 'Test Anfrage 2', 'Dies ist ein Beispiel 1', '2022-05-01 21:16:20', 'Lars');
INSERT INTO ghp_board.demand (id, demand_title, demand_text, demand_date, demand_name) VALUES (3, 'Test Anfrage 3', 'Dies ist ein Beispiel 2', '2022-04-01 21:16:20', 'Lars');

INSERT INTO ghp_board.blog (id, blog_author, blog_date, blog_text, blog_title, is_public) VALUES (1, 'Lars', '2022-06-01 21:16:20', 'Dies ist der Text von Blogartikel 1', 'Blogartikel 1', true);
INSERT INTO ghp_board.blog (id, blog_author, blog_date, blog_text, blog_title, is_public) VALUES (2, 'Lars', '2022-05-01 21:16:20', 'Dies ist der Text von Blogartikel 2', 'Blogartikel 2', true);
INSERT INTO ghp_board.blog (id, blog_author, blog_date, blog_text, blog_title, is_public) VALUES (3, 'Lars', '2022-04-01 21:16:20', 'Dies ist der Text von Blogartikel 3', 'Blogartikel 3', true);

INSERT INTO ghp_board.instrument (id, instrument_date, instrument_title, instrument_image_id) VALUES (1, '2022-06-01 21:16:20', 'Instrument 1', null);
INSERT INTO ghp_board.instrument (id, instrument_date, instrument_title, instrument_image_id) VALUES (2, '2022-05-01 21:16:20', 'Instrument 2', null);
INSERT INTO ghp_board.instrument (id, instrument_date, instrument_title, instrument_image_id) VALUES (3, '2022-04-01 21:16:20', 'Instrument 3', null);