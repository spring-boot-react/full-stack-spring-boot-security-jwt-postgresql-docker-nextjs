# Full Stack application with Spring Boot backend, Security with JWT, PostgreSQL database, Docker containerization and a Next.js frontend

<b>Author:</b> <a href="https://github.com/spring-boot-react" target="_blank">Full Stack Developer</a><br>
<b>Created:</b> 2024-03-11<br>
<b>Last updated:</b> 2024-03-11

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