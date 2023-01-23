create table `OTUSET_USER` (
                               `id`        integer primary key auto_increment,
                               `login`     varchar(255) not null,
                               `password`  varchar(255) not null,
                               `firstName` varchar(20)  not null,
                               `lastName`  varchar(20)  not null,
                               `sex`       varchar(3)   null,
                               `age`       integer      not null,
                               `interests` varchar(255) null,
                               `city`      varchar(255) null
);