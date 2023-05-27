CREATE TABLE app.people (
	id bigserial NOT NULL,
	name varchar(255) NOT NULL,
	user_id int8 NOT NULL,
	CONSTRAINT people_pkey PRIMARY KEY (id),
	CONSTRAINT people_user_id_key UNIQUE (user_id),
	CONSTRAINT people_user_id_fkey FOREIGN KEY (user_id) REFERENCES commons.users(id)
) INHERITS (commons.base_entity);

CREATE TABLE app_audit.people_audit (
	id int8 NOT NULL,
	rev int4 NOT NULL,
	revtype int2 NULL,
	created_at timestamp(6) NULL,
	created_by_user int8 NULL,
	deleted_at timestamp(6) NULL,
	deleted_by_user int8 NULL,
	modified_by_user int8 NULL,
	updated_at timestamp(6) NULL,
	"name" varchar(255) NULL,
	user_id int8 NULL,
	CONSTRAINT people_audit_pkey PRIMARY KEY (rev, id),
	CONSTRAINT people_audit_rev_fkey FOREIGN KEY (rev) REFERENCES public.revinfo(rev)
);