create table `OTUSET_USER` (
    `id`        integer primary key auto_increment,
    `firstName` varchar(20)  not null,
    `lastName`  varchar(20)  not null,
    `sex`       varchar(3)   null,
    `age`       integer      not null,
    `interests` varchar(255) null,
    `city`      varchar(255) null
);