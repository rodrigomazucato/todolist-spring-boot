# Etapa 1: Build
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app
COPY pom.xml ./
RUN mvn dependency:go-offline # Baixa as dependências antes de copiar o código

COPY src ./src
RUN mvn clean package -DskipTests # Compila o código sem rodar testes

# Etapa 2: Runtime (Imagem final mais leve)
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app
COPY --from=build /app/target/todolist-1.0.0.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]