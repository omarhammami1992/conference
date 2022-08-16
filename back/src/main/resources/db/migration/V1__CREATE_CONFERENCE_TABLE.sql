create table conference
(
    id         serial primary key,
    name       varchar(100) not null,
    link       varchar(100) not null,
    start_date timestamp,
    end_date   timestamp
);
