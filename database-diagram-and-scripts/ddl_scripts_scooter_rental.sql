CREATE TABLE cities (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);


CREATE TABLE scooter_rental_points (
    id BIGSERIAL PRIMARY KEY,
    city_id BIGINT NOT NULL,
    street VARCHAR(255) NOT NULL,
    building_number VARCHAR(20) NOT NULL,
    CONSTRAINT city_id_fk FOREIGN KEY (city_id) REFERENCES cities (id)
);


CREATE TABLE scooter_models (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL
);


CREATE TABLE scooters (
    id BIGSERIAL PRIMARY KEY,
    model_id BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL,
    price_per_hour REAL NOT NULL,
    passed_hours INT NOT NULL,
    rental_point_id BIGINT NOT NULL,
    CONSTRAINT model_id_fk FOREIGN KEY (model_id) REFERENCES scooter_models (id),
    CONSTRAINT rental_point_id_fk FOREIGN KEY (rental_point_id) REFERENCES scooter_rental_points (id),
    CONSTRAINT chk_status CHECK (status IN ('CHARGING', 'FAULTY', 'AVAILABLE', 'BUSY'))
);


CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);


CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(30) NOT NULL,
    surname VARCHAR(30) NOT NULL,
    age INT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT role_id_fk FOREIGN KEY (role_id) REFERENCES roles (id)
);


CREATE TABLE subscriptions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    balance DOUBLE PRECISION NOT NULL,
    discount_percentage REAL NOT NULL,
    created_on TIMESTAMP NOT NULL,
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users (id)
);


CREATE TABLE ride_sessions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    scooter_id BIGINT NOT NULL,
    ride_hours INT NOT NULL,
    created_on TIMESTAMP NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT scooter_id_fk FOREIGN KEY (scooter_id) REFERENCES scooters (id)
);