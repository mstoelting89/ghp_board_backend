USE ghp_board;

INSERT INTO ghp_board.news (id, news_author, news_date, news_image_id, news_text, news_title) VALUES (1, 'Michael', '2022-06-01 21:16:20', null, 'Foo Bar', 'Dies ist der dritte Titel');
INSERT INTO ghp_board.news (id, news_author, news_date, news_image_id, news_text, news_title) VALUES (2, 'Lars', '2022-05-01 21:16:20', null, 'Dies ist ein Beispieltext', 'Dies ist der zweite Titel');
INSERT INTO ghp_board.news (id, news_author, news_date, news_image_id, news_text, news_title) VALUES (3, 'Svenja', '2022-04-01 21:16:20', null, 'Das ist noch ein Beispieltext', 'Dies ist der erste Titel');

INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (1, 'michaelstoelting@gmail.com', true, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 1);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (2, 'lars@gmail.com', true, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 1);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (3, 'svneja@gmail.com', true, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 1);

INSERT INTO ghp_board.demand (id, demand_title, demand_text, demand_date) VALUES (1, 'Test Anfrage 1', 'Dies ist ein Beispiel', '2022-06-01 21:16:20');
INSERT INTO ghp_board.demand (id, demand_title, demand_text, demand_date) VALUES (2, 'Test Anfrage 2', 'Dies ist ein Beispiel 1', '2022-05-01 21:16:20');
INSERT INTO ghp_board.demand (id, demand_title, demand_text, demand_date) VALUES (3, 'Test Anfrage 3', 'Dies ist ein Beispiel 2', '2022-04-01 21:16:20');