<h1 align="center">Weather Application</h1>

![Java](https://img.shields.io/badge/java-59666C.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Jakarta EE](https://img.shields.io/badge/jakarta_ee-59666C?style=for-the-badge&labelColor=white)
![MVC(S)](https://img.shields.io/badge/mvc(s)-59666C?style=for-the-badge&labelColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![JPA](https://img.shields.io/badge/jpa-59666C?style=for-the-badge&labelColor=white)
![Postgres](https://img.shields.io/badge/postgres-59666C.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![HTTP](https://img.shields.io/badge/http-59666C?style=for-the-badge&labelColor=white)
![Rest Api](https://img.shields.io/badge/REST%20API-59666C?style=for-the-badge&labelColor=white)
![JSON](https://img.shields.io/badge/json-59666C?style=for-the-badge&labelColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-59666C?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-59666C.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![HTML](https://img.shields.io/badge/HTML-59666C?style=for-the-badge&logo=html5&logoColor=white)
![CSS](https://img.shields.io/badge/CSS-59666C?&style=for-the-badge&logo=css3&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/thymeleaf-59666C?style=for-the-badge&logo=thymeleaf)
![JUnit5](https://img.shields.io/badge/junit-59666C?style=for-the-badge&logo=junit5&logoColor=white)





## Предварительные настройки
Прежде всего необходимо установить СУБД.
Для запуска в контейнере Tomcat необходимо добавить DataSource в файлах server.xml и context.xml.

### server.xml
Место расположения: ~"путь к Tomcat"\conf\server.xml
В разделе GlobalNamingResources добавляем ЕЩЕ один Resource (пример настройки)

	  <Resource name="jdbc/postgres" 
      global="jdbc/postgres" 
      auth="Container" 
      type="javax.sql.DataSource" 
      driverClassName="org.postgresql.Driver" 
      url="jdbc:postgresql://localhost:5432/postgres" 
      username="postgres" 
      password="postgres" 
      
      maxTotal="100" 
      maxIdle="20" 
      minIdle="5" 
      maxWaitMillis="10000"/>

### context.xml
Место расположения: ~"путь к Tomcat"\conf\context.xml

В разделе Context добавляем ResourceLink (пример настройки)
```
<ResourceLink name="jdbc/postgres"
global="jdbc/postgres"
auth="Container"
type="javax.sql.DataSource" />
```

Теперь базу данных можно использовать в IntelijIDEA

- Открываем Database -> New -> DataSource
- Выбираем PostgreSQL
- Вводим логин пароль -> тестируем соединение
- Apply и обновляем Database
Теперь запускаем приложение с помощью Tomcat.
Необходимо добавить пользователя - либо с помощью INSERT в console либо заходим в таблицу users и жмем Add row ("+").
