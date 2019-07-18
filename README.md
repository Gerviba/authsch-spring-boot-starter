AuthSCH spring-boot starter
===

## How to import

For maven add 

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

and 

```xml

<dependency>
    <groupId>com.github.gerviba</groupId>
    <artifactId>authsch-spring-boot-starter</artifactId>
    <version>1.2.0</version>
</dependency>

```

Be sure to use the **latest** version.


## Default properties


```properties

## DEFAULT CONFIG VALUES

## --------------------------------------------------------------------
## AUTHSCH API
## Most of the times you don't need to modify
hu.bme.sch.auth.api.token-url-base=https://auth.sch.bme.hu/oauth2/token
hu.bme.sch.auth.api.login-url-base=https://auth.sch.bme.hu/site/login
hu.bme.sch.auth.api.api-url-base=https://auth.sch.bme.hu/api

## The credentials that the https://auth.sch.bme.hu/ gives you
hu.bme.sch.auth.api.client-identifier=20_digit
hu.bme.sch.auth.api.client-key=80_chars

## --------------------------------------------------------------------
## DEFAULT CONTROLLER
hu.bme.sch.auth.default-controller.enabled=true
hu.bme.sch.auth.default-controller.webhook-endpoint=/redirect
hu.bme.sch.auth.default-controller.login-endpoint=/login
hu.bme.sch.auth.default-controller.logout-endpoint=/logout

## --------------------------------------------------------------------
## LOGIN SERVICE
## Redirect to these endpoints 
hu.bme.sch.auth.login.login-ok=/
hu.bme.sch.auth.login.login-failed=/login-failed
hu.bme.sch.auth.login.unexpected-error=/error-login
hu.bme.sch.auth.login.logged-out=/logged-out

## Name of the session attribute where the user will be stored
hu.bme.sch.auth.login.session-attribute-name=user

## If not specified a random algorithm will generate one for you
#hu.bme.sch.auth.login.salt-for-unique-id=RANDOM_SALT_GOES_HERE

```

