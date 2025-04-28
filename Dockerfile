# Etapa de construção
FROM maven:3.9.3-eclipse-temurin-17 AS builder

WORKDIR /usr/app

# Copia o arquivo pom.xml e o diretório de código fonte
COPY pom.xml ./
COPY src ./src

# Executa a fase de construção do Maven
RUN mvn clean package -DskipTests

# Etapa de produção
FROM eclipse-temurin:17-alpine-3.21

# Copia o JAR construído na etapa anterior
COPY --from=builder /usr/app/target/*.jar /opt/app/application.jar

# Cria um grupo e um usuário para rodar a aplicação
RUN addgroup -S spring && adduser -S spring -G spring

USER spring:spring

CMD ["java", "-jar", "/opt/app/application.jar"]