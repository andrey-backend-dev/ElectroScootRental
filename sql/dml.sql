use electroscootdb;

INSERT INTO role
(name) VALUES
('ADMIN'),
('USER');

INSERT INTO user
(username, password, phone, firstname, secondname, email, money) VALUES
('testUser' , 'testPassword', '2134', 'Vasiliy', 'Ivanov', 'test@mail.ru', 10000),
('bladeattheneck' , 'testPassword', '5125', 'Andrey', null, null, 15000),
('usedreamless' , 'testPassword', '4219', null, null, 'example@mail.ru', 0);

INSERT INTO user2role
(role_id, user_id) VALUES
(2, 1),
(1, 2),
(2, 3);

INSERT INTO rentalplace
(name, city, street, house, rating) VALUES
('Метро Люблино', 'Москва', 'Совхозная улица', null, 2),
('Братеевская', 'Москва', 'Братеевская', 10, 5),
('ТРЦ НеКЛЮЧЕВОЙ', 'Москва', 'Ключевая', 30, 3),
('ТРЦ Ключевой', 'Москва', 'Ключевая', 20, 3),
('ТРЦ СПБ', 'Санкт-Петербург' , 'Какая-то' , 42, 0);

INSERT INTO scootermodel
(name, price_per_time, start_price, discount) VALUES
('FX230', 100.0, 300.0, 10),
('ES4921', 50.0, 500.0, 0);

INSERT INTO scooter
(rental_place_name, model, state) VALUES
('Метро Люблино', 'FX230', 'OK'),
('Метро Люблино', 'ES4921', 'BROKEN'),
('Братеевская', 'FX230', 'OK'),
('ТРЦ СПБ', 'ES4921', 'OK');

insert INTO scooterrental
(scooter_id, scooter_taken_at, scooter_passed_at, username, init_rental_place_name, final_rental_place_name, init_price_per_time, init_discount) VALUES
(2, date('2023-07-23 15:00:00'), current_time(), 'bladeattheneck', 'Братеевская', 'Метро Люблино', 100.0, 10);