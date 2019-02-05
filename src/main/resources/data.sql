DELETE FROM votes;
DELETE FROM daily_menu_detail;
DELETE FROM daily_menu;
DELETE FROM dishes;
DELETE FROM restaurants;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
('User', 'user@yandex.ru', '{noop}password'),
('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id) VALUES
('ROLE_USER', 100000),
('ROLE_ADMIN', 100001),
('ROLE_USER', 100001);

INSERT INTO restaurants (name) VALUES
('Ресторан 1'), -- 100002
('Ресторан 2'), -- 100003
('Ресторан 3'), -- 100004
('Ресторан 4'), -- 100005
('Ресторан 5'); -- 100006


INSERT INTO dishes (name, price, img_file) VALUES
('Салат Оливье',50,'pictures/dishes/d0f45cc0-46cc-49ce-995f-bcbc4071427b.jpg'), --100007
('Салат Столичный',40,'pictures/dishes/46d394c5-d84c-4080-84c6-6e6a1324ec14.jpg'), --100008
('Салат Цезарь',90,'pictures/dishes/592caf2e-55a8-483e-89e2-24c1f19f34d4.jpg'), --100009
('Стейк',150,'pictures/dishes/74d3e033-5133-4606-8dd9-fb64f3fd1291.JPG'), --100010
('Шашлык',120,'pictures/dishes/shashlik.jpg'), --100011
('Салат Шефский',70,'pictures/dishes/0d1a9e02-cedc-4052-b5cb-fc098943d3d1.jpg'), --100012
('Салат Греческий особенный',80,'pictures/dishes/ab4f5e29-24c0-4cd9-9994-cedb25070970.jpg'), --100013
('Стейк от шефа',140,'pictures/dishes/defc1761-8bf9-4e09-a87c-424873a1c416.jpg'), --100014
('Шашлык шея',130,null), --100015
('Запеченные овощи',110,'pictures/dishes/ovoschi.jpg'), --100016
('Пицца',125,'pictures/dishes/pizza.jpg'), --100017
('Картошка Фри',40,'pictures/dishes/348ae3fa-e1db-45eb-9f99-63a2561efec6.jpg'), --100018
('Картошка запеченная',45,null), --100019
('Стейк с картошкой',170,'pictures/dishes/66b57721-6298-4282-afb9-af94db01f7b2.jpg'), --100020
('Шашлык телятина',135,'pictures/dishes/4db0365d-ac04-40db-af89-60a1f53829d0.jpg'), --100021
('Запеченные овощи в тандыре',70,'pictures/dishes/1651896d-b565-4bd9-9948-13109f90188b.jpg'), --100022
('Курица Гриль',105,'pictures/dishes/c6e5a649-b6cd-4557-ac68-9e09e0f00750.jpg'), --100023
('Сэндвич',50,'pictures/dishes/sendvich.jpg'), --100024
('Суп Харчо',60,'pictures/dishes/sup_harcho.jpg'), --100025
('Салат Шефский обычный',65,'pictures/dishes/92c5dcfe-4bdd-4fcb-82db-6c268c3a2f64.jpg'), --100026
('Салат Греческий',75,'pictures/dishes/1e77cfbb-0f28-4f4e-b6d0-69775fbc407b.jpg'), --100027
('Красный борщ',50,'pictures/dishes/red_soup.jpg'), --100028
('Устрицы',140,'pictures/dishes/ce4b15a9-6542-4a8a-ab01-aa4e4fa6645e.jpg'), --100029
('Кофе американо',25,'pictures/dishes/kofe.jpeg'), --100030
('Кофе эспрессо',30,'pictures/dishes/5a5c54d6-f162-42c4-87fc-f49b768057d9.jpg'), --100031
('Чай черный',20,'pictures/dishes/5b4c3c7f-9af9-4901-a054-be1187e864a3.jpg'), --100032
('Чай зеленый',20,'pictures/dishes/e17bbf9e-638b-43c5-afb9-ce07f7997a52.jpg'), --100033
('Пельмени',80,'pictures/dishes/10b05806-67fc-432b-9b9e-3ac1e5d21e4f.jpg'), --100034
('Вареники',60,'pictures/dishes/vareniki.jpg'), --100035
('Рагу',75,'pictures/dishes/ragu.jpg'); --100036

INSERT INTO daily_menu (date, rest_id) VALUES
(DATE '2018-11-21', 100002), --100037
(DATE '2018-11-21', 100003),
(DATE '2018-11-21', 100004),
(DATE '2018-11-22', 100002),
(DATE '2018-11-22', 100003),
(DATE '2018-11-22', 100004),
(DATE '2018-11-23', 100002),
(DATE '2018-11-23', 100003),
(DATE '2018-11-23', 100004); --100045

INSERT INTO daily_menu_detail (daily_menu_id, dish_id) VALUES
(100037, 100007), --100046
(100037, 100008),
(100038, 100009),
(100038, 100010),
(100039, 100011),
(100039, 100012),
(100040, 100013),
(100040, 100014),
(100041, 100015),
(100041, 100016),
(100042, 100017),
(100042, 100018),
(100043, 100019),
(100043, 100020),
(100044, 100021),
(100044, 100022),
(100045, 100023),
(100045, 100024); --100063


INSERT INTO VOTES (DATE, USER_ID, REST_ID, DATE_TIME) VALUES
(DATE '2018-11-21', 100000, 100002, CURRENT_TIMESTAMP),--100064
(DATE '2018-11-21', 100001, 100003, CURRENT_TIMESTAMP),
(DATE '2018-11-22', 100000, 100002, CURRENT_TIMESTAMP),
(DATE '2018-11-22', 100001, 100004, CURRENT_TIMESTAMP),
(DATE '2018-11-23', 100000, 100003, CURRENT_TIMESTAMP),
(DATE '2018-11-23', 100001, 100003, CURRENT_TIMESTAMP);--100069
