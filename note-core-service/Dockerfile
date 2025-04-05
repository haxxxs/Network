# Используем базовый образ для Java 17
FROM openjdk:17-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный JAR-файл в контейнер
COPY target/demo-0.0.1-SNAPSHOT.jar /app/core-service.jar
COPY ../.env /app/.env

# Открываем порт, на котором будет работать core-service
EXPOSE 8080

# Команда для запуска приложения
CMD ["java", "-jar", "core-service.jar"]
