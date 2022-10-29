# This is a project that I wrote in a team with two interns during an internship at Andersen


# RMT-Mobile-New-Backend

This repository is created for the backend development of the RMN mobile project

## How to deploy the project locally

### Startup sequence
Microservice startup order is important:
1. First, you need to run api-gateway/config-server.
2. Next, run api-gateway/service-discovery
3. Run api-gateway/web-api-gateway
4. After that you can run services in any order.

While starting specific microservices you need to activate relevant profiles.
For config-server you need to activate profile 'native'. How does one activate a profile? In the IntelliJ IDEA, in the "Active profiles:" field, write 'native'.
Other microservices should be started with 'localhost' profile.
If you need to make arrangements with DevOps Engineer, Spring profiles could be activated via command line interface with an argument '-Dspring.profiles.active=dev'.

To run config-server you need to add an environment variable GIT_REPO_URI_LOCAL that holds an absolute path to the project on the hard disk. You should use forward slashes, even if you are using Windows.
Example:
```cmd
GIT_REPO_URI_LOCAL=C:/Projects/rmt-mobile-new-backend
```

Additionally you need to launch Apache Kafka. No specific setup is necessary. You can find set up and running instructions for Kafka on the internet (side note. Apache Kafka requires Apache ZooKeeper)

### Databases

rmn_mobile is the default user for all microservices. To create it, start psql on the command line and run this script:

```postgresql
create user rmn_mobile;
alter user rmn_mobile with encrypted password 'rmn_mobile';
```

Every Maven module has its "docs" folder. There you will find scripts that create databases for the microservices. Run those through psql too.

---
## Как запустить проект локально

### Порядок запуска
Порядок запуска микросервисов важен:
1. Сначала запустить api-gateway/config-server.
2. Дальше запустить api-gateway/service-discovery
3. Запустить api-gateway/web-api-gateway
4. Дальше можно запускать в любом порядке.

Запускать нужно с активацией профилей.
Для config-server нужно активировать профиль 'native'. Как активировать профиль? В IntelliJ IDEA в параметрах запуска микросервиса в поле 'Active profiles' написать 'native'.
Остальные микросервисы запускать с профилем 'localhost'.
Если нужно будет договариваться с девопсом, то профили spring через коммандную строку активируются с настройкой '-Dspring.profiles.active=dev'.

В параметры запуска config-server нужно ещё добавить переменную окружения (Environment variables) GIT_REPO_URI_LOCAL, которая должна указываеть на абсолютный путь к проекту на диске. Слеши использовать forward, даже если вы используете винду.
Пример:
```cmd
GIT_REPO_URI_LOCAL=C:/Projects/rmt-mobile-new-backend
```

Ещё нужно запустить Apache Kafka. Там никаких специальных настроек не нужно. Инструкцию по установке и запуску Kafka вы найдёте в интернете (если что для Apache Kafka нужен ещё Apache Zookeeper).

### БД

Во всех микросервисах в качестве пользователя по-умолчанию указан rmn_mobile. Чтобы его создать, зайдите в psql через коммандную строку и запустите это:

```postgresql
create user rmn_mobile;
alter user rmn_mobile with encrypted password 'rmn_mobile';
```

В каждом Maven модуле есть своя директория docs, в которой находятся скрипты для создания бд. Их тоже запускать в psql.
