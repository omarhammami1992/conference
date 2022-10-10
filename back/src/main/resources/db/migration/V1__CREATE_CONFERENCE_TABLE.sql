create table conference
(
    id         serial primary key,
    name       varchar(100) not null,
    link       varchar(100) not null,
    price      decimal not null,
    start_date timestamp,
    end_date   timestamp
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

create table price_attending_days
(
   id            serial primary key,
   price         decimal not null,
   attendingDays decimal not null,
   conference_id integer, constraint fk_price_by_attending_days_conference foreign key (conference_id) references conference (id)
);