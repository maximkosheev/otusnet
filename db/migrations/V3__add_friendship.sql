create table friendship (
    id bigint auto_increment,
    user_id bigint not null,
    friend_id bigint not null,
    constraint friendship_id primary key (id),
    constraint user_fk foreign key(user_id) references OTUSET_USER(id),
    constraint friend_fk foreign key(friend_id) references OTUSET_USER(id)
);
