INSERT INTO user (id, email, join_date, name, nick_name, password, resignation_date, slack_id, total_vacation_time)
VALUES (1, '00hongjun@sixshop.com', '2021-02-01', '최홍준', '이태성', '1234', null, 'qwer', 15);

INSERT INTO user (id, email, join_date, name, nick_name, password, resignation_date, slack_id, total_vacation_time)
VALUES (2, 'ohtaeg@sixshop.com', '2021-04-01', '오태경2', '태태로2', '1234', null, 'asdf', 15);

INSERT INTO user (id, email, join_date, name, nick_name, password, resignation_date, slack_id, total_vacation_time)
VALUES (3, 'ohtaeg@sixshop.com', '2021-04-01', '오태경3', '태태로3', '1234', null, 'asdf', 15);

-- vacation
INSERT INTO vacation (id, start_date_time, end_date_time, type, reason, use_hour, user_id)
VALUES (11, '2021-05-31 09:00', '2021-06-01 18:00', 'BASIC', '개인정비1', 16, 1);

INSERT INTO vacation (id, start_date_time, end_date_time, type, reason, use_hour, user_id)
VALUES (12, '2021-06-02 09:00', '2021-06-02 10:00', 'BASIC', '개인정비2', 1, 1);

INSERT INTO vacation (id, start_date_time, end_date_time, type, reason, use_hour, user_id)
VALUES (13, '2021-05-31 14:00', '2021-06-02 13:00', 'BASIC', '개인정비3', 16, 2);

INSERT INTO vacation (id, start_date_time, end_date_time, type, reason, use_hour, user_id)
VALUES (14, '2021-06-03 14:00', '2021-06-04 13:00', 'BASIC', '개인정비4', 8, 2);

INSERT INTO vacation (id, start_date_time, end_date_time, type, reason, use_hour, user_id)
VALUES (15, '2021-06-01 10:00', '2021-06-01 18:00', 'BASIC', '개인정비5', 7, 3);

INSERT INTO vacation (id, start_date_time, end_date_time, type, reason, use_hour, user_id)
VALUES (16, '2021-06-29 09:00', '2021-07-02 18:00', 'BASIC', '개인정비6', 32, 3);

INSERT INTO vacation (id, start_date_time, end_date_time, type, reason, use_hour, user_id)
VALUES (17, '2021-06-01 09:00', '2021-06-01 10:00', 'BASIC', '개인정비7', 1, 3);

INSERT INTO vacation (id, start_date_time, end_date_time, type, reason, use_hour, user_id)
VALUES (18, '2021-06-11 12:00', '2021-06-12 11:00', 'BASIC', '개인정비8', 7, 4);

INSERT INTO vacation (id, start_date_time, end_date_time, type, reason, use_hour, user_id)
VALUES (8, '2021-06-11 12:00', '2021-06-12 11:00', 'BASIC', '개인정비8', 7, 4);

