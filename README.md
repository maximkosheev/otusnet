# otusnet
Курсовой проект по Highload Architect

### Сборка проекта:
1) Перейти в каталог проекта
2) Выполнить команду
./gradlew clean build

### Запуск приложения через docker-compose:
1) docker-compose build --build-arg JAR_VERSION="0.0.1-SNAPSHOT"
2) docker-compose up -d

Тестовые данные:
пользователь {"login": "admin", "password": "admin"}