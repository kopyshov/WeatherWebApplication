# Предварительные настройки
Прежде всего необходимо установить СУБД PostgreSQL 15 + PgAdmin 4.
Для запуска в контейнере Tomcat необходимо добавить DataSource в файлах server.xml и context.xml.

# server.xml
Место расположения: ~"путь к Tomcat"\conf\server.xml
В разделе <GlobalNamingResources> добавляем ЕЩЕ один <Resource>

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

# context.xml
Место расположения: ~"путь к Tomcat"\conf\context.xml

В разделе <Context> добавляем ResourceLink
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
