create table conference
(
    id         serial primary key,
    name       varchar(100) not null,
    link       varchar(100) not null,
    start_date timestamp,
    end_date   timestamp
);

create table price_range
(
   id         serial primary key,
   price      decimal not null,
   start_date timestamp,
   end_date   timestamp,
   conference_id integer, constraint fk_conference foreign key (conference_id) references conference(id)
)