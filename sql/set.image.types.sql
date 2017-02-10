update IMAGE set type = 'STORY' where id in (select image_id from STORY);
update IMAGE set type = 'AFFILIATE' where id in (select image_id from AFFILIATE);
update IMAGE set type = 'AD' where id in (select image_id from SIDE_BAR_AD);
update IMAGE set type = 'PROFILE' where id in (select image_id from Profile);
update IMAGE set type = 'NEWS_ARTICLE' where id in (select image_id from NEWS_ARTICLE);