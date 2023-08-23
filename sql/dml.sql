use electroscootdb;

INSERT INTO role
(name) VALUES
('ROLE_ADMIN'),
('ROLE_USER');

INSERT INTO user
(username, password, phone, firstname, secondname, email, money) VALUES
('testUser' , '$2y$10$onxQRpmPh9dDB/jy697tuuQPSVh88uOkgAamxgsFKd14YEuWiRI46', '2134', 'Vasiliy', 'Ivanov', 'test@mail.ru', 10000),
('bladeattheneck' , '$2y$10$onxQRpmPh9dDB/jy697tuuQPSVh88uOkgAamxgsFKd14YEuWiRI46', '5125', 'Andrey', null, null, 15000),
('usedreamless' , '$2a$12$irFNLaWVUZaHVyL5l35F1uUj3jBt7I03HKJkKnvbEZ8ZFbW5k0PL2', '4219', null, null, 'example@mail.ru', 0);

INSERT INTO user2role
(role_id, user_id) VALUES
(2, 1),
(1, 2),
(2, 3);

INSERT INTO rentalplace
(name, city, street, house, rating) VALUES
('Metro Lublino', 'Moscow', 'Sovkhoznaya st', null, 2),
('Brateevskaya', 'Moscow', 'Brateevskaya st', 10, 5),
('TRC neKluchevoy', 'Moscow', 'Kluchevaya st', 30, 3),
('TRC Kluchevoy', 'Moscow', 'Kluchevaya st', 20, 3),
('TRC SPB', 'St. Petersburg' , 'Some st' , 42, 0);

INSERT INTO scootermodel
(name, price_per_time, start_price, discount) VALUES
('FX230', 100.0, 300.0, 10),
('ES4921', 50.0, 500.0, 0);

INSERT INTO scooter
(rental_place_name, model, state) VALUES
('Metro Lublino', 'FX230', 'OK'),
('Metro Lublino', 'ES4921', 'BROKEN'),
('Brateevskaya', 'FX230', 'OK'),
('TRC SPB', 'ES4921', 'OK');

insert INTO scooterrental
(scooter_id, scooter_taken_at, scooter_passed_at, username, init_rental_place_name, final_rental_place_name, init_price_per_time, init_discount) VALUES
(2, date('2023-07-23 15:00:00'), current_time(), 'bladeattheneck', 'Brateevskaya', 'Metro Lublino', 100.0, 10);