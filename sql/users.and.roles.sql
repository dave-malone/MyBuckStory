insert into ROLE (name) values('ROLE_ADMIN');
insert into ROLE (name) values('ROLE_USER');
insert into `USER` (user_name, `password`, enabled, signup_date, account_non_expired, account_non_locked, credentials_non_expired, accepted_tos) values('admin', '97914118daf75c2951e28578a783dbcc0dbf4e4dd2ed3754640f080beb9fe525', b'1',now(), b'1', b'1', b'1', b'0');
insert into USER_ROLE (user_id, role_id) values(1, 1);
insert into USER_ROLE (user_id, role_id) values(1, 2);
insert into Profile (user_Id, date_of_birth, first_name, last_name, showBirthday, under18, make_public, disable_all_email_notifications) 
  values (1, now(), 'Admin', 'Account', b'0', b'1', b'0', b'0');
  
insert into `USER` (user_name, `password`, enabled, signup_date, account_non_expired, account_non_locked, credentials_non_expired, accepted_tos) values('dave', '97914118daf75c2951e28578a783dbcc0dbf4e4dd2ed3754640f080beb9fe525', b'1',now(), b'1', b'1', b'1', b'0');
insert into USER_ROLE (user_id, role_id) values(2, 1);
insert into USER_ROLE (user_id, role_id) values(2, 2);
insert into Profile (user_Id, date_of_birth, first_name, last_name, showBirthday, under18, make_public, disable_all_email_notifications) 
  values (2, now(), 'Dave', 'Malone', b'0', b'1', b'0', b'0');