drop table if exists users cascade;

create table users (
    id bigint auto_increment,
    username varchar(255) not null,
    name varchar(255) not null,
    password varchar(255) not null,
    birthday date not null,
    is_private bit not null,
    created_at datetime not null,
    modified_at datetime not null,
    pronoun varchar(255),
    profile_image_url text,
    bio text,
    primary key (id),
    unique (username)
);