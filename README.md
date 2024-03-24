# Full Stack application with Spring Boot backend, Security with JWT, PostgreSQL database, Docker containerization and a Next.js frontend

<b>Author:</b> <a href="https://github.com/spring-boot-react" target="_blank">Full Stack Developer</a><br>
<b>Collaborator(s):</b> <a href="https://github.com/darksos34" target="_blank">Jordy Coder</a><br>
<b>Created:</b> 2024-03-11<br>
<b>Last updated:</b> 2024-03-24

This repository serves as a demonstration on how to create a robust and scalable security application with <b>Spring Security and JWT</b>.<br>
Within this repository, the following technologies are applied:
- [![](https://img.shields.io/badge/Spring%20Boot-8A2BE2)]() [![](https://img.shields.io/badge/release-Feb%2022,%202024-blue)]() [![](https://img.shields.io/badge/version-3.2.3-blue)]()
- [![](https://img.shields.io/badge/JWT-8A2BE2)]() [![](https://img.shields.io/badge/release-Feb%2001,%202024-blue)]() [![](https://img.shields.io/badge/version-0.12.5-blue)]()
- [![](https://img.shields.io/badge/Docker-8A2BE2)]()
<br>

## 1 Installation Prerequisites

- <a href="https://maven.apache.org/download.cgi" target="_blank">Maven</a>
- <a href="https://adoptium.net" target="_blank">Java 21</a> or higher
- <a href="https://www.docker.com/products/docker-desktop/" target="_blank">Docker Desktop</a>
- IDE (of your choice)
<br>

_**Note:** Make sure to disable any local PostgreSQL installation when running this application._

## 2 Project Installation

Open a new command line.

- Clone the repository:

```bash
git clone https://github.com/spring-boot-react/full-stack-spring-boot-security-jwt-postgresql-docker-nextjs.git
```

- Go into the ```backend``` folder:

```bash
cd backend
```

- Build the project using Maven:

```bash
mvn clean install
```

- Run the <strong>Spring Boot</strong> application:

```bash
mvn spring-boot:run
```

The application will start on port: ```http://localhost:8081```

## 3 Setup the PostgreSQL Database

1. Access PGAdmin via ```http://localhost:5050```
2. Set a master password for PGAdmin, for example ```root```
3. Within the _Quick Links_ section, click **Add New Server**

- General tab - Name: ```postgres```
- Connection tab - Host name/address: ```postgres```
- Connection tab - Username: ```username```
- Connection tab - Password: ```password```

## 4 API Endpoints

To avoid as much manual work as possible, a Postman collection is provided for you to import within your Postman installation.<br>
This file can be found in ```src/main/resources/data/postman/import/collection-import.json```

![04-postman-collection](https://github.com/spring-boot-react/full-stack-spring-boot-security-jwt-postgresql-docker-nextjs/blob/main/images/04-postman-collection.jpg)

<i>**NOTE:** Use the tokens, provided to you in the terminal, to access the secured endpoints:</i>

![05-terminal-tokens](https://github.com/spring-boot-react/full-stack-spring-boot-security-jwt-postgresql-docker-nextjs/blob/main/images/05-terminal-tokens.jpg)

## 5 I18N Internationalization

I18N Internationalization is implemented as part of the raised enhancement issue [Implement I18N Internationalization](https://github.com/spring-boot-react/full-stack-spring-boot-security-jwt-postgresql-docker-nextjs/issues/4).

### 5.1 Internationalization with logging

A default logging language is set within the `.env` file.<br>
Key: `LOGGING_LANGUAGE`<br>
The value can be either of the following:
`en`
`de`
`nl`

The already set logging languages will change language automatically.

To use this functionality, simply use following code:
```java
translateService.getLogMessage(String code);
```

`code`: for example `jwt.si.invalid.signature`

The code is the key you use within the `main/resources/i18n` properties files.

<b>NOTE:</b> you can also make use of arguments.

### 5.2 Internationalization with JSON response messages

To use this functionality, see following code example:

```java
translateService.getMessage(String code, String[] args);

private Book findById(int id) {
  return bookRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("book.si.not.found",  new String[]{String.valueOf(id)}));
}
```

The code is the key you use within the `main/resources/i18n` properties files.

<b>NOTE:</b> this example uses arguments, which can be accessed within the properties file accordingly.