# --- Etap 1: budowanie aplikacji (JAR powstaje wewnątrz kontenera) ---
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Najpierw sam pom — warstwa z zależnościami zostaje w cache, dopóki pom się nie zmieni
COPY pom.xml .
RUN mvn -B -q dependency:go-offline || true

# Potem kod źródłowy i właściwy build (testy pomijamy — wymagają działającej bazy)
COPY src ./src
RUN mvn -B -q clean package -DskipTests

# --- Etap 2: lekki obraz uruchomieniowy (samo JRE) ---
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
