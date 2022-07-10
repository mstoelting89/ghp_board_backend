USE ghp_board;

INSERT INTO ghp_board.news (id, news_author, news_date, news_image_id, news_text, news_title) VALUES (1, 'Michael', '2022-06-01 21:16:20', null, 'Foo Bar', 'Dies ist der dritte Titel');
INSERT INTO ghp_board.news (id, news_author, news_date, news_image_id, news_text, news_title) VALUES (2, 'Lars', '2022-05-01 21:16:20', null, 'Dies ist ein Beispieltext', 'Dies ist der zweite Titel');
INSERT INTO ghp_board.news (id, news_author, news_date, news_image_id, news_text, news_title) VALUES (3, 'Svenja', '2022-04-01 21:16:20', null, 'Das ist noch ein Beispieltext', 'Dies ist der erste Titel');

INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (1, 'michaelstoelting@gmail.com', true, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 1);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (2, 'lars@gmail.com', true, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 1);
INSERT INTO ghp_board.user (id, email, enabled, locked, password, user_role) VALUES (3, 'svenja@gmail.com', true, false, '$2a$10$xOPlSqT9VwCTx1RQZFRxQ.HQxMEQMlkw5P7nCQcBfe/BavxD.ZShO', 1);

INSERT INTO ghp_board.demand (id, demand_title, demand_text, demand_date, demand_name) VALUES (1, 'Test Anfrage 1', 'Dies ist ein Beispiel', '2022-06-01 21:16:20', 'Lars');
INSERT INTO ghp_board.demand (id, demand_title, demand_text, demand_date, demand_name) VALUES (2, 'Test Anfrage 2', 'Dies ist ein Beispiel 1', '2022-05-01 21:16:20', 'Lars');
INSERT INTO ghp_board.demand (id, demand_title, demand_text, demand_date, demand_name) VALUES (3, 'Test Anfrage 3', 'Dies ist ein Beispiel 2', '2022-04-01 21:16:20', 'Lars');

INSERT INTO ghp_board.blog (id, blog_author, blog_date, blog_text, blog_title, is_public) VALUES (1, 'Lars', '2022-06-01 21:16:20', 'Dies ist der Text von Blogartikel 1', 'Blogartikel 1', true);
INSERT INTO ghp_board.blog (id, blog_author, blog_date, blog_text, blog_title, is_public) VALUES (2, 'Lars', '2022-05-01 21:16:20', 'Dies ist der Text von Blogartikel 2', 'Blogartikel 2', true);
INSERT INTO ghp_board.blog (id, blog_author, blog_date, blog_text, blog_title, is_public) VALUES (3, 'Lars', '2022-04-01 21:16:20', 'Dies ist der Text von Blogartikel 3', 'Blogartikel 3', true);

INSERT INTO ghp_board.instrument (id, instrument_date, instrument_title, instrument_image_id) VALUES (1, '2022-06-01 21:16:20', 'Instrument 1', null);
INSERT INTO ghp_board.instrument (id, instrument_date, instrument_title, instrument_image_id) VALUES (2, '2022-05-01 21:16:20', 'Instrument 2', null);
INSERT INTO ghp_board.instrument (id, instrument_date, instrument_title, instrument_image_id) VALUES (3, '2022-04-01 21:16:20', 'Instrument 3', null);