create table SocialUser (
    id bigint primary key AUTO_INCREMENT,
    login varchar(25),
    password varchar(255),
    firstName varchar(100),
    lastName varchar(100),
    age int4,
    sex varchar(10),
    interests text,
    city varchar(100)
);