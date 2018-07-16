create table  if not exists musicTypes (
	id integer primary key,
	typeName text unique
);

create table  if not exists roles (
	id integer primary key,
	roleName text unique
);

create table  if not exists adresses (
	id integer primary key,
	country text,
	city text,
	street text,
	building text,
	flat text
);

create table  if not exists users (
	id integer primary key,
	login text,
	password text,
	addressId integer,
	musicTypeId integer,
	roleId integer,
	foreign key(addressId) REFERENCES adresses(id),
	foreign key(musicTypeId) REFERENCES musycTypes(id),
	foreign key(roleId) REFERENCES roles(id)
);

insert or replace into roles (id, roleName)
values (1, 'admin');

insert or replace into roles (id, roleName)
values(2, 'user');

insert or replace into roles (id, roleName)
values(3, 'mandatory');

insert or replace into musicTypes (id, typeName)
values(1, 'ROCK');

insert or replace into musicTypes (id, typeName)
values(2, 'ALTERNATIVE');

insert or replace into musicTypes (id, typeName)
values(3, 'DARKWAVE');

insert or replace into roles (id, roleName)
values (1, 'admin');

insert or replace into adresses (id, country ,
	city ,
	street ,
	building ,
	flat )
values (1, 'Russia', 'Moscow', 'Begovaya', '11', '23');

insert or replace into users (id, login,
	password ,
	addressId ,
	musicTypeId ,
	roleId )
values (1, 'root', 'root', 1, 3, 1);

