-- liquibase formatted sql
-- changeset kozyarik:1
create table users
(
    user_id serial primary key ,
    password varchar(10000) not null ,
    username varchar(255)not null unique ,
    first_name varchar(30) not null ,
    last_name varchar(30) not null ,
    phone varchar(12) not null ,
    role varchar(10) not null
);

-- changeset kozyarik:2
create table ads
(
    ad_id serial primary key ,
    user_id int not null references users(user_id) on delete cascade ,
    title text not null ,
    price int not null ,
    description text
);

-- changeset kozyarik:3
create table comments
(
    comment_id serial primary key ,
    user_id int not null references users(user_id) on delete cascade ,
    created_at timestamp not null ,
    comment_text text,
    ad_id int not null references ads(ad_id) on delete cascade
);

-- changeset kozyarik:4
create table images
(
    image_id bigserial primary key
);

-- changeset kozyarik:5
alter table users
add column image_id bigint references images(image_id);

alter table ads
add column image_id bigint references images(image_id);

-- changeset kozyarik:6
insert into users(password, username, first_name, last_name, phone, role,image_id)
values ('$2a$10$adi5GCC6kfVpVEikbVF51.VCv4YpWKyl1fAZW6rEB9aeK.1WCrovG',
        'kozyarik@gmail.com','oxana','kozyar','+79286667402','ADMIN',null);

-- changeset kozyarik:7
insert into users(password,username,first_name,last_name,phone,role)
values ('$2a$10$mT9IYC9Ww05Azkns1eH6auyHQ.rgLCfvcLIwX4YnOodZtR6819yC.','alisa@gmail.com','alisa','alisa','+71234567899','ADMIN');

-- changeset kozyarik:8
insert into users(password,username,first_name,last_name,phone,role)
values ('$2a$10$hxQwyvq.qAPQa0f/yDmZ5eP5U8cUjpbBFbTetUvLE0fcNRh/sTCnS','katerina@gmail.com','katerina','katerina','+79876543210','USER');

insert into users(password, username, first_name, last_name, phone, role)
values ('$2a$10$u9O5SJptut4JNwrqNobyHeXFarSthXmHfrnWCnrhxyHbR5xlRftvK','roman@gmail.com','roman','roman','+71234567890','ADMIN');
