use electroscootdb;

INSERT INTO role
(name) VALUES
('ROLE_ADMIN'),
('ROLE_USER');

INSERT INTO user
(username, password, phone, firstname, secondname, email, money) VALUES
('user' , '$2a$12$LdyUx2tdkT23JLV24gXOPOg9qMqeFbG0Logo04GmYtPNQSzV3plY6', '88005553535', 'Vasiliy', 'Ivanov', 'test@mail.ru', 10000),
('admin' , '$2a$12$xHphbMnEuN73QK95kZptTuc5g9MOoBKP3eDXhMw3DP9jN6Z8atVxy', '88005555535', null, null, 'example@mail.ru', 10000);

INSERT INTO user2role
(role_id, user_id) VALUES
(2, 1),
(1, 2);

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