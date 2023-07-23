-- liquibase formatted sql

-- changeset kozyar:1
create table "img"
(
    "id"         BIGINT auto_increment
        primary key,
    "image"      bytea,
    "media_type" CHARACTER VARYING(255),
    "size"       BIGINT
);