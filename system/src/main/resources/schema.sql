DROP TABLE IF EXISTS `news`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `attachment`;

CREATE TABLE `attachment` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `location` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `news` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `news_author` varchar(255) NOT NULL,
    `news_date` datetime NOT NULL,
    `news_text` varchar(255) NOT NULL,
    `news_title` varchar(255) NOT NULL,
    `news_image_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKtg9s22f2vnv2s6pufjo86q3sr` (`news_image_id`),
    CONSTRAINT `FKtg9s22f2vnv2s6pufjo86q3sr` FOREIGN KEY (`news_image_id`) REFERENCES `attachment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `email` varchar(255) DEFAULT NULL,
    `enabled` bit(1) DEFAULT NULL,
    `locked` bit(1) DEFAULT NULL,
    `password` varchar(255) DEFAULT NULL,
    `user_role` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

