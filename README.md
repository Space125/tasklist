# Tasklist

This application helps you organize creating and accessing tasks for users.

## Environment

To run this application you need to create `.env` file in root directory with next environments:

- `POSTGRES_HOST` - host of Postgresql database
- `POSTGRES_PORT` - port of Postgresql database
- `POSTGRES_USER` - username for Postgresql database
- `POSTGRES_PASSWORD` - password for Postgresql database
- `POSTGRES_DB` - name of Postgresql database
- `POSTGRES_SCHEMA` - name of Postgresql schema
- `JWT_SECRET_KEY` - secret string for JWT token
- `JWT_ACCESS_EXPIRED` - access token expiration in hours
- `JWT_REFRESH_EXPIRED` - refresh token expiration in days

## Technological stack

Spring Boot 3, Security, Scheduler, JWT, JDBC, Mybatis, JPA, Liquibase, PostgreSQL, MinIO, Radis, GraphQL, Swagger,
Docker
