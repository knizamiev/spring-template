--liquibase formatted sql
--changeset k_nizamiev:2020-04-20-initial
--comment Тестовая таблица


create table users (
	id bigint primary key,
	name varchar(32) not null,
	gender varchar(10) not null,
	deleted boolean not null default false,
	date timestamp not null
);
create sequence users_seq;

create table users_history (
	id bigint primary key,
	user_id bigint not null,
    status varchar(10) not null
);
create sequence users_history_seq;