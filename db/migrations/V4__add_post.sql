create table post (
    id bigint auto_increment,
    author_id bigint not null,
    create_dt datetime not null default now(),
    data text,
    constraint friendship_id primary key (id),
    constraint author_fk foreign key(author_id) references OTUSET_USER(id)
);
