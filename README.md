# Iniciando aplicação
1. Subir aplicativo
```sh
docker-compoose up
```

1. Criando database
```sh
# Opção 1
psql -h localhost -U postgres

# Opção 2
docker exec -it api-db bash
su - postgres
psql
```

```sql
create schema system;
create schema system_audit;
```