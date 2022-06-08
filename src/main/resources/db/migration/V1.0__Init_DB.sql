drop table if exists accounttb;
drop table if exists budget;

create table accounttb
(
    id       serial
        primary key,
    typename varchar(10),
    simageid integer,
    beizhu   varchar(80),
    money    double precision,
    time     varchar(60),
    year     integer,
    month    integer,
    day      integer,
    kind     integer,
    timest   timestamp default CURRENT_TIMESTAMP,
    userid   varchar
);

create table budget
(
    userid varchar not null
        constraint budget_pk
            primary key,
    budget double precision,
    timest timestamp default CURRENT_TIMESTAMP
);