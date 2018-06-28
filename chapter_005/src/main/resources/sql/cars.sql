create table transmission (
  id serial primary key,
	name varchar(20)
);

create table car_body (
  id serial primary key,
	name varchar(20)
);

create table engine (
  id serial primary key,
	name varchar(20)
);

create table car (
  id serial primary key,
	name varchar(20),
	trans_id integer references transmission(id),
	body_id integer references car_body (id),
	engine_id integer references engine (id)
);

insert into transmission (name)
values ('t1'), ('t2'), ('t3');

insert into car_body (name)
values ('b1'), ('b2'), ('b3');

insert into engine (name)
values ('e1'), ('e2'), ('e3');

insert into car (name, trans_id, body_id, engine_id)
values ('car1', 3, 2, 1), ('car2', 2, 1, 3);

-- вывести список всех машин и все привязанные к ним детали.
select * from car;
-- вывести трансмиссии, которые не используются в машинах
select t.id, t.name from transmission t left join car c on c.trans_id = t.id where c.id is null;
-- вывести кузова, которые не используются в машинах
select cb.id, cb.name from car c right join car_body cb on c.body_id = cb.id where c.id is null;
-- вывести двигатели, которые не используются в машинах
select en.id, en.name from engine en left join car c on c.engine_id = en.id where c.id is null;