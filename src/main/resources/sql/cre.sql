DROP TABLE IF EXISTS Book;
DROP TABLE IF EXISTS Author;
DROP SEQUENCE IF EXISTS hibernate_seq;

CREATE SEQUENCE hibernate_seq START WITH 100;

CREATE TABLE Author
(
    id          bigint PRIMARY KEY DEFAULT nextval('hibernate_seq'),
    name        character varying,
    second_name character varying
);

CREATE TABLE Book
(
    id        bigint PRIMARY KEY DEFAULT nextval('hibernate_seq'),
    name      character varying,
    author_id bigint,
    FOREIGN KEY (author_id) REFERENCES author (id) ON DELETE CASCADE
);
