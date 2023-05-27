# Core

1. Migrar database
```sh
# Entrar no projeto, verificar se existe configuração (flyway.conf) e executar o comando
mvn flyway:migrate
```
1. Iniciar projeto
```sh
# Iniciar dabase
docker-compose up postgres

# Iniciar aplicação
mvn spring-boot:run -pl app
```