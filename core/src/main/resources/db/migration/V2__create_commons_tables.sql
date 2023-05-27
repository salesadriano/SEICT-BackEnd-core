CREATE SEQUENCE public.revinfo_seq
	INCREMENT BY 50
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE TABLE public.revinfo (
	rev int4 NOT NULL,
	revtstmp int8 NULL,
	CONSTRAINT revinfo_pkey PRIMARY KEY (rev)
);

CREATE TABLE commons.base_entity (
	created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT current_timestamp,
	deleted_at TIMESTAMP WITHOUT TIME ZONE NULL,
	updated_at TIMESTAMP WITHOUT TIME ZONE NULL,
	created_by_user int8 NOT NULL,
	deleted_by_user int8 NULL,
	modified_by_user int8 NULL
);

CREATE TABLE commons.roles (
	id bigserial NOT NULL,
	authority varchar(255) NOT NULL,
	CONSTRAINT roles_pkey PRIMARY KEY (id)
) INHERITS (commons.base_entity);

CREATE TABLE commons.users (
	id bigserial NOT NULL,
	"password" varchar(255) NOT NULL,
	username varchar(255) NOT NULL,
	CONSTRAINT users_pkey PRIMARY KEY (id)
) INHERITS (commons.base_entity);

CREATE TABLE commons.users_roles (
	user_id int8 NOT NULL,
	role_id int8 NOT NULL,
	CONSTRAINT users_roles_user_id_fkey FOREIGN KEY (user_id) REFERENCES commons.users(id),
	CONSTRAINT users_roles_role_id_fkey FOREIGN KEY (role_id) REFERENCES commons.roles(id)
);

CREATE TABLE commons_audit.roles_audit (
	id int8 NOT NULL,
	rev int4 NOT NULL,
	revtype int2 NULL,
	created_at timestamp(6) NULL,
	created_by_user int8 NULL,
	deleted_at timestamp(6) NULL,
	deleted_by_user int8 NULL,
	modified_by_user int8 NULL,
	updated_at timestamp(6) NULL,
	authority varchar(255) NULL,
	CONSTRAINT roles_audit_pkey PRIMARY KEY (rev, id),
	CONSTRAINT roles_audit_rev_fkey FOREIGN KEY (rev) REFERENCES public.revinfo(rev)
);

CREATE TABLE commons_audit.users_audit (
	id int8 NOT NULL,
	rev int4 NOT NULL,
	revtype int2 NULL,
	created_at timestamp(6) NULL,
	created_by_user int8 NULL,
	deleted_at timestamp(6) NULL,
	deleted_by_user int8 NULL,
	modified_by_user int8 NULL,
	updated_at timestamp(6) NULL,
	"password" varchar(255) NULL,
	username varchar(255) NULL,
	CONSTRAINT users_audit_pkey PRIMARY KEY (rev, id),
	CONSTRAINT users_audit_rev_fkey FOREIGN KEY (rev) REFERENCES public.revinfo(rev)
);

CREATE TABLE commons_audit.users_roles_audit (
	rev int4 NOT NULL,
	user_id int8 NOT NULL,
	role_id int8 NOT NULL,
	revtype int2 NULL,
	CONSTRAINT users_roles_audit_pkey PRIMARY KEY (rev, user_id, role_id),
	CONSTRAINT users_roles_audit_rev_fkey FOREIGN KEY (rev) REFERENCES public.revinfo(rev)
);