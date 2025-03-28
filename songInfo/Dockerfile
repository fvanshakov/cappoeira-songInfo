# Используем официальный образ с OpenJDK 17
FROM openjdk:17-jdk-slim

# Создаем рабочую директорию в контейнере
WORKDIR /app

# Копируем JAR-файл в контейнер
COPY your-app*.jar app.jar

# Указываем команду для запуска приложения
CMD ["java", "-jar", "app.jar"]
