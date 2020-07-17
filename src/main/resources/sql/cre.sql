CREATE TABLE Author
(
    id   bigint,
    name character varying
);

CREATE TABLE Book
(
    id        bigint,
    name      character varying,
    author_id bigint
);

insert into Author
values (1, 'Author 1');
insert into Author
values (2, 'Author 2');
insert into Author
values (3, 'Author 3');
insert into Author
values (4, 'Author NIL');

insert into Book
values (11, 'Book 11 (Author 1)', 1);
insert into Book
values (12, 'Book 12 (Author 1)', 1);
insert into Book
values (13, 'Book 13 (Author 1)', 1);

insert into Book
values (21, 'Book 21 (Author 2)', 2);
insert into Book
values (22, 'Book 22 (Author 2)', 2);

insert into Book
values (33, 'Book 33 (Author 3)', 3);
