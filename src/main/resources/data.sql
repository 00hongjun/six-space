INSERT INTO user (id, email, join_date, name, nick_name, password, resignation_date, slack_id, total_vacation_time)
VALUES (1, '00hongjun@sixshop.com', '2021-02-01', '최홍준', '이태오', '1234', null, 'U01NUKKL074', 100);

INSERT INTO user (id, email, join_date, name, nick_name, password, resignation_date, slack_id, total_vacation_time)
VALUES (2, 'ohtaeg@sixshop.com', '2021-04-01', '오태경', '태태로', '1234', null, 'U01SXH2CRT7', 10);

-- vacation
INSERT INTO vacation (id, start_date_time, end_date_time, type, reason, use_hour, user_id)
VALUES (1, '2021-05-31 09:00', '2021-06-01 18:00', 'BASIC', '개인정비1', 16, 1);

INSERT INTO vacation (id, start_date_time, end_date_time, type, reason, use_hour, user_id)
VALUES (2, '2021-06-02 09:00', '2021-06-02 10:00', 'BASIC', '개인정비2', 1, 1);

INSERT INTO vacation (id, start_date_time, end_date_time, type, reason, use_hour, user_id)
VALUES (3, '2021-05-31 14:00', '2021-06-02 13:00', 'BASIC', '개인정비3', 16, 2);

INSERT INTO vacation (id, start_date_time, end_date_time, type, reason, use_hour, user_id)
VALUES (4, '2021-06-03 14:00', '2021-06-04 13:00', 'BASIC', '개인정비4', 8, 2);
