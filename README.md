# Core

## Geral
1. Criar usuário padrão
```sh
docker run --rm -ti xmartlabs/htpasswd admin 1q3e > htpasswd
```

## Postgresql
1. Iniciando
```sql
-- Criar role
CREATE ROLE core LOGIN NOSUPERUSER NOCREATEDB NOCREATEROLE NOINHERIT NOREPLICATION CONNECTION LIMIT 10 PASSWORD 'bla123x';

-- Criar database
CREATE DATABASE core OWNER core;
```

1. Sobre constraints
```
The standard names for indexes in PostgreSQL are:

{tablename}_{columnname(s)}_{suffix}

where the suffix is one of the following:

pkey for a Primary Key constraint
key for a Unique constraint
excl for an Exclusion constraint
idx for any other kind of index
fkey for a Foreign key
check for a Check constraint
Standard suffix for sequences is

seq for all sequences
```

## Flyway
### ATENÇÃO: SEMPRE QUE FOR ALTERAR UMA TABELA, ALTERAR TAMBÉM A DE AUDITORIA
### Entrar no projeto, verificar se existe configuração (flyway.conf) e executar o comando

1. Migrar projeto
```sh
mvn flyway:migrate
```

1. Desfazer migrations
```sh
mvn flyway:clean
```

## Iniciar projeto
```sh
# Iniciar dabase
docker-compose up postgres

# Iniciar aplicação
mvn spring-boot:run -pl app
```