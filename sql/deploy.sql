/*
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS covers;
*/


CREATE TABLE IF NOT EXISTS covers(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS books(
    id BIGSERIAL PRIMARY KEY,
    isbn VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255),
    pages INTEGER,
    cover_id BIGINT REFERENCES covers,
    price DECIMAL (6,2) DEFAULT 0.0 NOT NULL,
    deleted BOOLEAN DEFAULT false NOT NULL
);

/*
TRUNCATE books CASCADE;
TRUNCATE covers CASCADE;
*/

INSERT INTO covers (name)
VALUES ('SOFT'),
       ('HARD'),
       ('ANOTHER');

INSERT INTO books (isbn, title, author, pages, cover_id, price)
VALUES ('123-4-12-34541-3', 'Wolf and ...', 'Volkov', 121, (SELECT id FROM covers WHERE name = 'HARD'), 12.25),
       ('187-4-12-34546-2', 'Jook and ...', 'Ivanov', 241, (SELECT id FROM covers WHERE name = 'SOFT'), 54.55),
       ('154-2-68-52541-3', 'Monqey and ...', 'Petrov', 652, (SELECT id FROM covers WHERE name = 'HARD'), 32),
       ('586-3-12-34547-4', 'Snake and ...', 'Sidorov', 356, (SELECT id FROM covers WHERE name = 'ANOTHER'), 15),
       ('458-7-85-21548-8', 'Bus and ...', 'Nosov', 785, (SELECT id FROM covers WHERE name = 'HARD'), 6),
       ('586-2-12-45547-6', 'Java and ...', 'Zaycev', 12, (SELECT id FROM covers WHERE name = 'SOFT'), 74),
       ('142-5-12-25541-3', 'List and ...', 'Gusev', 562, (SELECT id FROM covers WHERE name = 'HARD'), 121.66),
       ('123-4-89-65541-6', 'Cat and ...', 'Ovechkin', 111, (SELECT id FROM covers WHERE name = 'ANOTHER'), 52),
       ('987-4-12-34541-4', 'Dog and ...', 'Ivanov', 325, (SELECT id FROM covers WHERE name = 'HARD'), 75),
       ('654-1-12-34541-6', 'Fox and ...', 'Petrov', 163, (SELECT id FROM covers WHERE name = 'SOFT'), 71),
       ('963-2-12-34541-3', 'Rabbit and ...', 'Konev', 254, (SELECT id FROM covers WHERE name = 'HARD'), 13),
       ('752-1-12-34541-5', 'Alise and ...', 'Slonov', 365, (SELECT id FROM covers WHERE name = 'HARD'), 124.74),
       ('874-1-12-45541-3', 'Sun and ...', 'Gusev', 23, (SELECT id FROM covers WHERE name = 'SOFT'), 44),
       ('475-2-12-34541-7', 'Elefant and ...', 'Rukov', 45, (SELECT id FROM covers WHERE name = 'HARD'), 10),
       ('896-5-15-24541-3', 'Wolrld and ...', 'Nogov', 128, (SELECT id FROM covers WHERE name = 'ANOTHER'), 20),
       ('451-2-75-34541-2', 'Checken and ...', 'Vilkin', 256, (SELECT id FROM covers WHERE name = 'SOFT'), 63.46),
       ('143-2-65-34541-1', 'Car and ...', 'Strelkin', 357, (SELECT id FROM covers WHERE name = 'HARD'), 17),
       ('457-8-15-34541-7', 'Taxi and ...', 'Okov', 452, (SELECT id FROM covers WHERE name = 'HARD'), 25),
       ('479-6-32-34541-3', 'Man and ...', 'Volkov', 222, (SELECT id FROM covers WHERE name = 'SOFT'), 11.47),
	   ('987-6-15-44541-9', 'Grench and ...', 'Suhov', 452, (SELECT id FROM covers WHERE name = 'HARD'), 132);

/*
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
*/


CREATE TABLE IF NOT EXISTS roles(
    id BIGSERIAL PRIMARY KEY,
    role VARCHAR(255)
);

CREATE TABLE  IF NOT EXISTS users(
    id BIGSERIAL PRIMARY KEY,
    lastName VARCHAR(255) NOT NULL,
    firstName VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    login VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    age INTEGER,
    roles_id BIGINT REFERENCES roles,
    deleted BOOLEAN DEFAULT false NOT NULL
);

/*
TRUNCATE users CASCADE;
TRUNCATE roles CASCADE;
*/

INSERT INTO roles (role)
VALUES ('ADMIN'),
       ('MANAGER'),
       ('CUSTOMER');

INSERT INTO users (lastName, firstName, email, login, password, age, roles_id)
VALUES ('Ivanov', 'Ivan', 'Ivanov@gmail.com', 'Ivan1', 'I1', 33, (SELECT id FROM roles WHERE role = 'ADMIN')),
       ('Petrov', 'Petr', 'Petrov@gmail.com', 'Petr1', 'P1', 30, (SELECT id FROM roles WHERE role = 'MANAGER')),
       ('Sidorov', 'Ivan', 'Sidorov@gmail.com', 'Ivan2', 'I2', 25, (SELECT id FROM roles WHERE role = 'MANAGER')),
       ('Volkov', 'Gena', 'Volkov@gmail.com', 'Gena1', 'G1', 41, (SELECT id FROM roles WHERE role = 'MANAGER')),
       ('Zaycev', 'Vasya', 'Zaycev@gmail.com', 'Vasya', 'IV1', 63, (SELECT id FROM roles WHERE role = 'CUSTOMER')),
       ('Gusev', 'John', 'Gusev@gmail.com', 'John1', 'J1', 12, (SELECT id FROM roles WHERE role = 'CUSTOMER')),
       ('Lisov', 'Yan', 'Lisov@gmail.com', 'Yan1', 'Y1', 62, (SELECT id FROM roles WHERE role = 'CUSTOMER')),
       ('Krotov', 'Pavel', 'Krotov@gmail.com', 'Pavel1', 'P1', 11, (SELECT id FROM roles WHERE role = 'CUSTOMER')),
       ('Ladov', 'Sahsa', 'Ladov@gmail.com', 'Sahsa1', 'S1', 25, (SELECT id FROM roles WHERE role = 'CUSTOMER')),
       ('Dubov', 'Dima', 'Dubov@gmail.com', 'Dima1', 'D1', 16, (SELECT id FROM roles WHERE role = 'CUSTOMER')),
       ('Sadov', 'Kolya', 'Sadov@gmail.com', 'Kolya1', 'K1', 25, (SELECT id FROM roles WHERE role = 'CUSTOMER')),
       ('Medov', 'Vitia', 'Medov@gmail.com', 'Vitia1', 'V1', 36, (SELECT id FROM roles WHERE role = 'CUSTOMER')),
       ('Hudov', 'Jane', 'Hudov@gmail.com', 'Jane1', 'J1', 23, (SELECT id FROM roles WHERE role = 'CUSTOMER')),
       ('Kotov', 'Murzik', 'Kotov@gmail.com', 'Murzik1', 'M1', 45, (SELECT id FROM roles WHERE role = 'CUSTOMER')),
       ('Somov', 'Dima', 'Somov@gmail.com', 'Dima2', 'D2', 12, (SELECT id FROM roles WHERE role = 'CUSTOMER')),
       ('Urov', 'Ivan', 'Urov@gmail.com', 'Ivan3', 'I3', 56, (SELECT id FROM roles WHERE role = 'CUSTOMER')),
       ('Saharov', 'Gena', 'Saharov@gmail.com', 'Gena2', 'G2', 57, (SELECT id FROM roles WHERE role = 'CUSTOMER')),
       ('Podov', 'Misha', 'Podov@gmail.com', 'Misha1', 'M1', 52, (SELECT id FROM roles WHERE role = 'CUSTOMER')),
       ('Futov', 'Sasha', 'Futov@gmail.com', 'Sasha2', 'S2', 22, (SELECT id FROM roles WHERE role = 'CUSTOMER')),
	   ('Klimov', 'Oleg', 'Klimov@gmail.com', 'Oleg1', 'O1', 42, (SELECT id FROM roles WHERE role = 'CUSTOMER'));


