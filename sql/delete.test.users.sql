delete from story_category where story_id not in (select id from story where author_id = 1);
delete from story where author_id <> 1;
delete from image where author_id <> 1;
delete from footer_link where author_id <> 1;
delete from side_bar_ad where author_id <> 1;
delete from profile where user_id not in (select id from user where user_name = 'admin');
delete from user_role where user_id not in (select id from user where user_name = 'admin');
delete from user where user_name <> 'admin';
