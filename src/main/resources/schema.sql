DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS vote;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS user_roles;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE restaurants (
  id          BIGINT DEFAULT global_seq.nextval PRIMARY KEY,
  name        VARCHAR(255)    NOT NULL
);

CREATE TABLE dishes (
  id          BIGINT DEFAULT global_seq.nextval PRIMARY KEY,
  name        VARCHAR(255)    NOT NULL,
  date        DATE            NOT NULL,
  price       DECIMAL(10, 2)  DEFAULT 0,
  rest_id     BIGINT          NOT NULL,
  CONSTRAINT dish_unique_idx UNIQUE (rest_id, date, name),
  FOREIGN KEY (rest_id) REFERENCES restaurants (id) ON DELETE CASCADE
);
/*CREATE UNIQUE INDEX dishes_rest_index ON dishes (rest_id);*/

CREATE TABLE users
(
  id         BIGINT DEFAULT global_seq.nextval PRIMARY KEY,
  name       VARCHAR(255)     NOT NULL,
  email      VARCHAR(255)     NOT NULL,
  password   VARCHAR(255)     NOT NULL,
  enabled    BOOLEAN          DEFAULT TRUE,
  registered TIMESTAMP,
  CONSTRAINT users_email_unique_idx UNIQUE (email)
);
CREATE UNIQUE INDEX users_email_idx ON users (email);

CREATE TABLE vote (
  id            BIGINT DEFAULT global_seq.nextval PRIMARY KEY,
  user_id       BIGINT          NOT NULL,
  restaurant_id BIGINT          NOT NULL,
  date          DATE            NOT NULL,
  CONSTRAINT user_date_unique_idx UNIQUE (user_id, date),
  FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
/*CREATE UNIQUE INDEX vote_rest_index ON vote (rest_id);
CREATE UNIQUE INDEX vote_user_index ON vote (user_id);*/

CREATE TABLE user_roles
(
  user_id    INTEGER          NOT NULL,
  role       VARCHAR(255),
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY ( user_id ) REFERENCES USERS (id) ON DELETE CASCADE
);


