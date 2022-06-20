INSERT INTO ghp_board.news (id, news_author, news_date, news_image_id, news_text, news_title) VALUES (1, 'Michael', '2022-06-01 21:16:20', null, 'Foo Bar', 'Dies ist der dritte Titel');
INSERT INTO ghp_board.news (id, news_author, news_date, news_image_id, news_text, news_title) VALUES (2, 'Lars', '2022-05-01 21:16:20', null, 'Dies ist ein Beispieltext', 'Dies ist der zweite Titel');
INSERT INTO ghp_board.news (id, news_author, news_date, news_image_id, news_text, news_title) VALUES (3, 'Svenja', '2022-04-01 21:16:20', null, 'Das ist noch ein Beispieltext', 'Dies ist der erste Titel');

INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (1, 'michaelstoelting@gmail.com', true, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 1);
