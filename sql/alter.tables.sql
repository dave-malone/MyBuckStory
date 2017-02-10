ALTER TABLE `mbs`.`STORY` MODIFY COLUMN `text` LONGTEXT CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL;
ALTER TABLE `mbs`.`NEWS_ARTICLE` MODIFY COLUMN `content` LONGTEXT CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL;
ALTER TABLE `mbs`.`EVENT` MODIFY COLUMN `description` LONGTEXT CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL;
ALTER TABLE `mbs`.`Profile` MODIFY COLUMN `interests` LONGTEXT CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
 MODIFY COLUMN `about` LONGTEXT CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
 MODIFY COLUMN `fav_species` LONGTEXT CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
 MODIFY COLUMN `fav_gear` LONGTEXT CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
 MODIFY COLUMN `fav_music` LONGTEXT CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
 MODIFY COLUMN `fav_movies` LONGTEXT CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL;
ALTER TABLE `mbs`.`STORY_RATING` DROP PRIMARY KEY, ADD PRIMARY KEY  USING BTREE(`VOTER_ID`, `STORY_ID`);
ALTER TABLE `mbs`.`STORY_RATING` MODIFY COLUMN `id` BIGINT(20) DEFAULT NULL, MODIFY COLUMN `elt` BIGINT(20) DEFAULT NULL;

ALTER TABLE `mbs`.`Contest` MODIFY COLUMN `description` LONGTEXT CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL;
ALTER TABLE `mbs`.`Contest` MODIFY COLUMN `rules` LONGTEXT CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL;
ALTER TABLE `mbs`.`PRIZE` MODIFY COLUMN `description` LONGTEXT CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL;
ALTER TABLE `mbs`.`AFFILIATE` MODIFY COLUMN `other` LONGTEXT CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL;
ALTER TABLE `mbs`.`SIDE_BAR_AD` MODIFY COLUMN `raw_code` LONGTEXT CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL;
ALTER TABLE `mbs`.`Video` MODIFY COLUMN `description` LONGTEXT CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL;
