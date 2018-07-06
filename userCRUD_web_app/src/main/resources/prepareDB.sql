create table  if not exists roles (
	id integer primary key,
	role_name text
);

insert or replace into roles (id, role_name)
values (1, 'admin');

insert or replace into roles (id, role_name)
values(2, 'user');

create table  if not exists users (
	id text unique not null,
	name text,
	login text,
	password text,
	email text,
	role_id integer,
	created timestamp,
	foreign key(role_id) REFERENCES roles(id)
);

