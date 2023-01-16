create table OTUSET_USER
(
    id        bigint auto_increment,
    firstName varchar(20)  not null,
    lastName  varchar(20)  not null,
    sex       varchar(3)   null,
    age       int          not null,
    interests text         null,
    city      varchar(255) null,
    constraint users_pk primary key (id)
)
comment 'пользователи социальной сети';

