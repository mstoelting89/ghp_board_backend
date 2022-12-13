USE ghp_board;

DROP TABLE IF EXISTS `demand_demand_images`;
DROP TABLE IF EXISTS `blog_blog_images`;
DROP TABLE IF EXISTS `news`;
DROP TABLE IF EXISTS `voting`;
DROP TABLE IF EXISTS `demand`;
DROP TABLE IF EXISTS `voting`;
DROP TABLE IF EXISTS `blog`;
DROP TABLE IF EXISTS `instrument`;
DROP TABLE IF EXISTS `attachment`;
DROP TABLE IF EXISTS `token`;
DROP TABLE IF EXISTS `user`;


CREATE TABLE `attachment` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `location` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `news` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `news_author` varchar(255) NOT NULL,
    `news_date` datetime NOT NULL,
    `news_text` longtext NOT NULL,
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

CREATE TABLE `demand` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `demand_date` datetime NOT NULL,
    `demand_text` longtext NOT NULL,
    `demand_title` varchar(255) NOT NULL,
    `demand_name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `demand_demand_images` (
    `demand_id` bigint(20) NOT NULL,
    `demand_images_id` bigint(20) NOT NULL,
    UNIQUE KEY `UK_37pnqwm7behv61tllgjqg4f5v` (`demand_images_id`),
    KEY `FK9k7ce31lrhx0ljh1xlltp9y6l` (`demand_id`),
    CONSTRAINT `FK9k7ce31lrhx0ljh1xlltp9y6l` FOREIGN KEY (`demand_id`) REFERENCES `demand` (`id`),
    CONSTRAINT `FKnvo7r1f3xmyjdikk2anb2v11o` FOREIGN KEY (`demand_images_id`) REFERENCES `attachment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `voting` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `vote` bit(1) DEFAULT NULL,
    `demand_id` bigint(20) DEFAULT NULL,
    `user_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKibnwq0skydlugpci9x962k2mq` (`demand_id`),
    KEY `FK9bj0a6525byvwq3qgn4uyjpiv` (`user_id`),
    CONSTRAINT `FK9bj0a6525byvwq3qgn4uyjpiv` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FKibnwq0skydlugpci9x962k2mq` FOREIGN KEY (`demand_id`) REFERENCES `demand` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `blog` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `blog_author` varchar(255) NOT NULL,
    `blog_date` datetime NOT NULL,
    `blog_text` longtext NOT NULL,
    `blog_title` varchar(255) NOT NULL,
    `is_public` bit(1) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `blog_blog_images` (
    `blog_id` bigint(20) NOT NULL,
    `blog_images_id` bigint(20) NOT NULL,
    UNIQUE KEY `UK_eh1k2qsd72x8ebkteyjeiaf54` (`blog_images_id`),
    KEY `FKpjrsp48v4nrg53dwg5ilseqv5` (`blog_id`),
    CONSTRAINT `FKcwtcs0h5xroy7ucm6d3x9vu75` FOREIGN KEY (`blog_images_id`) REFERENCES `attachment` (`id`),
    CONSTRAINT `FKpjrsp48v4nrg53dwg5ilseqv5` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `instrument` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `instrument_date` datetime NOT NULL,
    `instrument_title` varchar(255) NOT NULL,
    `instrument_image_id` bigint(20) DEFAULT NULL,
    `donator` varchar(255) DEFAULT null,
    `taken` bit(1) DEFAULT 0,
    PRIMARY KEY (`id`),
    KEY `FK95a9j7ucg5fwottoimwy4rncr` (`instrument_image_id`),
    CONSTRAINT `FK95a9j7ucg5fwottoimwy4rncr` FOREIGN KEY (`instrument_image_id`) REFERENCES `attachment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `token` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `confirmed_at` datetime DEFAULT NULL,
    `created_at` datetime NOT NULL,
    `expires_at` datetime NOT NULL,
    `token` varchar(255) NOT NULL,
    `user_id` bigint(20) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKe32ek7ixanakfqsdaokm4q9y2` (`user_id`),
    CONSTRAINT `FKe32ek7ixanakfqsdaokm4q9y2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



