# Используем официальный образ с OpenJDK 17
FROM eclipse-temurin:17-jdk

# Создаем рабочую директорию в контейнере
WORKDIR /app

# Копируем JAR-файл в контейнер
COPY app*.jar app.jar

# Указываем команду для запуска приложения
CMD ["java", "-jar", "app.jar"]
