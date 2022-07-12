create table SocialUser (
    id bigint primary key AUTO_INCREMENT,
    login varchar(25) not null,
    password varchar(255) not null,
    firstName varchar(100) not null,
    lastName varchar(100),
    age int4 default 18,
    sex varchar(10) default 'MALE',
    interests text,
    city varchar(100)
);

create unique index socialuser_login_uk on SocialUser(login);