CREATE TABLE IF NOT EXISTS public.items
(	id serial primary key,
    name varchar(200),
    description varchar (200),
    created timestamp default now()
);

CREATE TABLE IF NOT EXISTS public.comments
(	id serial primary key,
    comment varchar(500),
 	item_id integer references items(id),
    created timestamp default now()
);