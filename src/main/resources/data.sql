use electroscootdb;

INSERT INTO authority
(name) VALUES
('user_control'),
('scooter_read'),
('scooter_update'),
('scooter_create'),
('scooter_delete'),
('scooter_rental_read'),
('scooter_rental_close'),
('role_read'),
('role_create'),
('role_update'),
('role_delete'),
('rental_place_create'),
('rental_place_delete'),
('rental_place_update');

INSERT INTO role
(name) VALUES
('ROLE_ADMIN'),
('ROLE_USER'),
('ROLE_USER_MODERATOR'),
('ROLE_SCOOTER_MODERATOR'),
('ROLE_SCOOTER_RENTAL_MODERATOR'),
('ROLE_ROLE_MODERATOR'),
('ROLE_RENTAL_PLACE_MODERATOR');

INSERT INTO role2authority
(role_name, authority_name) VALUES
('ROLE_ADMIN', 'user_control'),
('ROLE_ADMIN', 'scooter_read'),
('ROLE_ADMIN', 'scooter_update'),
('ROLE_ADMIN', 'scooter_create'),
('ROLE_ADMIN', 'scooter_delete'),
('ROLE_ADMIN', 'scooter_rental_read'),
('ROLE_ADMIN', 'scooter_rental_close'),
('ROLE_ADMIN', 'role_read'),
('ROLE_ADMIN', 'role_create'),
('ROLE_ADMIN', 'role_delete'),
('ROLE_ADMIN', 'role_update'),
('ROLE_ADMIN', 'rental_place_create'),
('ROLE_ADMIN', 'rental_place_delete'),
('ROLE_ADMIN', 'rental_place_update'),
('ROLE_SCOOTER_MODERATOR', 'scooter_read'),
('ROLE_SCOOTER_MODERATOR', 'scooter_update'),
('ROLE_SCOOTER_MODERATOR', 'scooter_create'),
('ROLE_SCOOTER_MODERATOR', 'scooter_delete'),
('ROLE_SCOOTER_RENTAL_MODERATOR', 'scooter_rental_read'),
('ROLE_SCOOTER_RENTAL_MODERATOR', 'scooter_rental_close'),
('ROLE_RENTAL_PLACE_MODERATOR', 'rental_place_create'),
('ROLE_RENTAL_PLACE_MODERATOR', 'rental_place_delete'),
('ROLE_RENTAL_PLACE_MODERATOR', 'rental_place_update');

INSERT INTO user
(username, password, phone, firstname, secondname, email, money) VALUES
('admin' , '$2a$12$xHphbMnEuN73QK95kZptTuc5g9MOoBKP3eDXhMw3DP9jN6Z8atVxy', '88005555535', null, null, 'example@mail.ru', 10000),
('user' , '$2a$12$LdyUx2tdkT23JLV24gXOPOg9qMqeFbG0Logo04GmYtPNQSzV3plY6', '88005553535', 'Vasiliy', 'Ivanov', 'test@mail.ru', 10000),
('scooter moderator', '$2a$12$RGFKQjMRcqHvlZ1lSbpu3eLTKhl0rkgzrepdHTXvZh2Nqkev4qaM2', '88006663636', null, null, null, 10000),
('rental moderator', '$2a$12$yThnjsTNz.G0vyVhnfpslO1ywRYMCSht.nw/YI72qGN9esZYiE/76', '88006666636', null, null, null, 10000),
('place moderator', '$2a$12$MZZEj86rm8aZxZQpPkr6eO7qqJJsJhe9ChPZkcuy.uNKFI3HHxc8y', '88006666635', null, null, null, 10000);

INSERT INTO user2role
(role_name, user_id) VALUES
('ROLE_ADMIN', 1),
('ROLE_USER', 2),
('ROLE_SCOOTER_MODERATOR', 3),
('ROLE_SCOOTER_RENTAL_MODERATOR', 4),
('ROLE_RENTAL_PLACE_MODERATOR', 5);

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