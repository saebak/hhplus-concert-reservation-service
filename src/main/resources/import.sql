-- 사용자 등록
INSERT INTO USERS (ID, NAME, CREATED_AT) VALUES (1, 'PARK', CURRENT_TIMESTAMP);
INSERT INTO USERS (ID, NAME, CREATED_AT) VALUES (2, 'HA', CURRENT_TIMESTAMP);

-- 사용자 포인트 등록
INSERT INTO USER_POINT (ID, USER_ID, POINT, CREATED_AT, UPDATED_AT, VERSION) VALUES (1, 1, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
INSERT INTO USER_POINT (ID, USER_ID, POINT, CREATED_AT, UPDATED_AT, VERSION) VALUES (2, 2, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);

-- 사용자 토큰 등록
INSERT INTO USER_TOKEN (ID, USER_ID, ACCESS_TOKEN, STATUS, CREATED_AT, UPDATED_AT) VALUES (1, 1, 'UEWQIORJQWIPE', 'WAIT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 콘서트 등록
INSERT INTO CONCERT (ID, TITLE, CONTENT, PRICE, CREATED_AT, UPDATED_AT) VALUES (1, '데스노트', '데스노트 뮤지컬', 20000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO CONCERT (ID, TITLE, CONTENT, PRICE, CREATED_AT, UPDATED_AT) VALUES (2, '하헌우의 개쩌는 자바 강의', '쩔어염', 50000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 콘서트 스케줄 등록
INSERT INTO CONCERT_SCHEDULE (ID, CONCERT_ID, OPEN_DATE, CREATED_AT, UPDATED_AT) VALUES (1, 1, DATEADD('DAY', -1, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SCHEDULE (ID, CONCERT_ID, OPEN_DATE, CREATED_AT, UPDATED_AT) VALUES (2, 1, DATEADD('DAY', -2, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SCHEDULE (ID, CONCERT_ID, OPEN_DATE, CREATED_AT, UPDATED_AT) VALUES (3, 1, DATEADD('DAY', -3, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SCHEDULE (ID, CONCERT_ID, OPEN_DATE, CREATED_AT, UPDATED_AT) VALUES (4, 2, DATEADD('DAY', -2, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SCHEDULE (ID, CONCERT_ID, OPEN_DATE, CREATED_AT, UPDATED_AT) VALUES (5, 2, DATEADD('DAY', -3, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SCHEDULE (ID, CONCERT_ID, OPEN_DATE, CREATED_AT, UPDATED_AT) VALUES (6, 2, DATEADD('DAY', -1, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 콘서트 좌석 등록
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (1, 1,1,1, CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (2, 1,1,2, CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (3, 1,1,3, CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (4, 1,1,4, CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (5, 1,1,5, CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (6, 1,1,6, CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (7, 1,1,7, CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (8, 1,1,8, CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (9, 1,1,9, CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (10, 1,1,10, CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (11, 1,1,11, CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (12, 1,1,12,  CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (13, 1,1,13,  CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (14, 1,1,14,  CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (15, 1,1,15,  CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (16, 1,1,16,  CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (17, 1,1,17,  CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (18, 1,1,18,  CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (19, 1,1,19,  CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (20, 1,1,20,  CURRENT_TIMESTAMP);

INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (21, 1,2,1, CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (22, 1,2,2, CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (23, 1,2,3, CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (24, 1,2,4, CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (25, 1,2,5, CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (26, 1,2,6, CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (27, 1,2,7, CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (28, 1,2,8, CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (29, 1,2,9, CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (30, 1,2,10, CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (31, 1,2,11, CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (32, 1,2,12,  CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (33, 1,2,13,  CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (34, 1,2,14,  CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (35, 1,2,15,  CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (36, 1,2,16,  CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (37, 1,2,17,  CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (38, 1,2,18,  CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (39, 1,2,19,  CURRENT_TIMESTAMP);
INSERT INTO CONCERT_SEAT (ID, CONCERT_ID, SCHEDULE_ID, SEAT_NO, CREATED_AT) VALUES (40, 1,2,20,  CURRENT_TIMESTAMP);

--  INSERT INTO SEAT_RESERVATION (ID, CONCERT_ID, SCHEDULE_ID, SEAT_ID, USER_ID, CREATED_AT) VALUES (1, 1, 1, 5, 10,  DATEADD('DAY', -1, CURRENT_TIMESTAMP));
-- INSERT INTO SEAT_RESERVATION (ID, CONCERT_ID, SCHEDULE_ID, SEAT_ID, USER_ID, CREATED_AT) VALUES (1, 1, 1, 5, 11, DATEADD('DAY', -2, CURRENT_TIMESTAMP));

