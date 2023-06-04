INSERT INTO commons.roles (id, authority, created_at, deleted_at, updated_at, created_by_user, deleted_by_user, modified_by_user) 
VALUES (default, 'ROLE_ADMIN', current_timestamp, null, null, 1, null, null);

INSERT INTO commons.users (id, username, password, created_at, deleted_at, updated_at, created_by_user, deleted_by_user, modified_by_user) 
VALUES (default, 'admin', '$2y$05$GKJgVoVsZ.4pJHLCsuXpWu/qklGiqAWVwLV9NLvPB9HD0nLQeo3Q6', current_timestamp, null, null, 1, null, null);

INSERT INTO commons.users_roles values (1, 1);