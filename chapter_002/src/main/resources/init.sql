CREATE TABLE IF NOT EXISTS items
(	  forumID integer primary key autoincrement,
    name varchar(200),
    description varchar (200),
    created TIMESTAMP
);

CREATE TABLE IF NOT EXISTS comments
(	  forumID integer primary key autoincrement,
    comment varchar(500),
 	  item_id integer references items(forumID),
    created TIMESTAMP
);