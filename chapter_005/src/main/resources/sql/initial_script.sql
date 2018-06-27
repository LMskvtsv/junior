create database job4j

create table Roles
(
    forumID integer primary key,
	role_name character (20) not null
);

insert into Roles (forumID, role_name)
values (0, 'user'),
		(1, 'admin');

create table States
(
    forumID integer primary key,
	description character (20) not null
);

insert into States (forumID, description)
values (0, 'open'),
		(1, 'closed');

create table Categories
(
    forumID integer primary key,
	description character (20) not null
);

insert into Categories (forumID, description)
values (0, 'bug'),
		(1, 'feature');

create table Users
(
    forumID serial primary key,
	name character (20) not null,
	role_id integer references Roles(forumID)
);

insert into Users (name, role_id)
values ('Irvin', 0),
		('Admin', 1);


create table Items
(
    forumID serial primary key,
	description text not null,
	user_id integer references Users(forumID),
	state_id integer references States(forumID),
	category_id integer references Categories(forumID)
);

insert into Items (description, user_id, state_id, category_id)
values ('titan fall', 1, 0, 0),
		('do smth', 2, 1, 1);

create table Comments
(
    forumID serial primary key,
	comment text not null,
	item_id integer references Items(forumID)
);

insert into Comments (comment, item_id)
values ('want this done', 1);


create table Attachments
(
    forumID serial primary key,
	path text not null,
	item_id integer references Items(forumID)
);

insert into Attachments (path, item_id)
values ('C://Downloads/myfile.txt', 2);
