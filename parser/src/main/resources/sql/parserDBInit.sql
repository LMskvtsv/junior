create table  if not exists job_offers (
	id serial primary key,
	forum_id text  unique not null,
	title text,
	href text,
	created timestamp
)
