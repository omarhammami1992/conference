create table conference
(
    id         serial primary key,
    name       varchar(100) not null,
    link       varchar(100) not null,
    price      decimal      not null,
    start_date timestamp,
    end_date   timestamp,
    city       varchar(100),
    country    varchar(100),
    full_address    varchar(100),
    latitude   varchar(100),
    longitude  varchar(100)
);

create table price_range
(
    id            serial primary key,
    price         decimal not null,
    start_date    timestamp,
    end_date      timestamp,
    conference_id integer,
    constraint fk_price_range_conference foreign key (conference_id) references conference (id)
);

create table price_group
(
    id            serial primary key,
    price         decimal not null,
    threshold     integer not null,
    conference_id integer,
    constraint fk_price_group_conference foreign key (conference_id) references conference (id)
);

create table price_attending_day
(
    id             serial primary key,
    price          decimal(10, 2) not null,
    attending_days decimal(10, 2) not null,
    conference_id  integer,
    constraint fk_price_by_attending_days_conference foreign key (conference_id) references conference (id)
);