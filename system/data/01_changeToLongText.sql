use ghp_board;

ALTER TABLE blog MODIFY blog_text LONGTEXT;
ALTER TABLE demand MODIFY demand_text LONGTEXT;
ALTER TABLE news MODIFY news_text LONGTEXT;