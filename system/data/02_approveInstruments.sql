use ghp_board;

ALTER TABLE instrument ADD COLUMN donator varchar(255) DEFAULT null;
ALTER TABLE instrument ADD COLUMN taken bit(1) DEFAULT 0;