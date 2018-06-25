CREATE TABLE IF NOT EXISTS items
(	  id integer primary key autoincrement,
    name varchar(200),
    description varchar (200),
    created TIMESTAMP
);

CREATE TABLE IF NOT EXISTS comments
(	  id integer primary key autoincrement,
    comment varchar(500),
 	  item_id integer references items(id),
    created TIMESTAMP
);