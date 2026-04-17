# OAuth2 Client Backend Spring Web App

Spring Boot web app configured as an OAuth2 Client with two registrations:

- `algashop-ecommerce-web` (`authorization_code`)
- `algashop-ecommerce-m2m` (`client_credentials`)

## Configuration applied

- Application port: `9080`
- Authorization Server issuer: `http://algashop-authorization-server:8081`
- Requested scopes:
  - Web client: all scopes registered for this client
  - M2M client: all scopes registered for this client

The properties are in `src/main/resources/application.yaml`.

`post_logout_redirect_uri` configurado: `http://algashop-ecommerce:9080?logout-success`

## Endpoints

- `GET /` -> public Thymeleaf homepage
- `GET /protected-resource` -> protected page that requires OAuth2 login
- `GET /oauth2/authorization/algashop-ecommerce-web` -> starts the web OAuth2 login flow
- `POST /logout` -> starts OIDC logout with post-logout redirect

## Run locally

```program
cd C:\git\algaworks\ems\demo\oauth2-client-backend-spring-web-app
.\gradlew.bat bootRun
```

## Testes

```program
cd C:\git\algaworks\ems\demo\oauth2-client-backend-spring-web-app
.\gradlew.bat test
```

