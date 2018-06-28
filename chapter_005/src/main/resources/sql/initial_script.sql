create database job4j

create table Roles
(
    id integer primary key,
	role_name character (20) not null
);

insert into Roles (id, role_name)
values (0, 'user'),
		(1, 'admin');

create table States
(
    id integer primary key,
	description character (20) not null
);

insert into States (id, description)
values (0, 'open'),
		(1, 'closed');

create table Categories
(
    id integer primary key,
	description character (20) not null
);

insert into Categories (id, description)
values (0, 'bug'),
		(1, 'feature');

create table Users
(
    id serial primary key,
	name character (20) not null,
	role_id integer references Roles(id)
);

insert into Users (name, role_id)
values ('Irvin', 0),
		('Admin', 1);


create table Items
(
    id serial primary key,
	description text not null,
	user_id integer references Users(id),
	state_id integer references States(id),
	category_id integer references Categories(id)
);

insert into Items (description, user_id, state_id, category_id)
values ('titan fall', 1, 0, 0),
		('do smth', 2, 1, 1);

create table Comments
(
    id serial primary key,
	comment text not null,
	item_id integer references Items(id)
);

insert into Comments (comment, item_id)
values ('want this done', 1);


create table Attachments
(
    id serial primary key,
	path text not null,
	item_id integer references Items(id)
);

insert into Attachments (path, item_id)
values ('C://Downloads/myfile.txt', 2);
