# Используем базовый образ для Java 17
FROM openjdk:17-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный JAR-файл в контейнер
COPY target/demo-0.0.1-SNAPSHOT.jar /app/api-gateway.jar

# Открываем порт, на котором будет работать api-gateway
EXPOSE 8081

# Команда для запуска приложения
CMD ["java", "-jar", "api-gateway.jar"]
