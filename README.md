# BankAPI
Выполнение задания создания веб-сервиса, реализующего логику работы клиентов с банковскими счетами.

#### Требования:
- JDK 1.8
- Apache Maven 11
- БД Postgres

#### Подготовка к запуску приложения:
-   Создать БД bankapi (для тестирования приложения необходимо так же создать БД test)
-   Исполнить скрипты файлов по пути src/main/resources/sql scripts

#### Запуск приложения из Intellij Idea:
- Запустить класс src/main/java/ru/af/Demo.java
- Зайти на localhost:8080/accounts/

#### Запуск приложения из консоли:
- mvn clean install
- java -jar /target/bankapi-1.0-SNAPSHOT.jar
