# Folosim o imagine oficială Java bazată pe Alpine Linux pentru o dimensiune redusă
FROM eclipse-temurin:17-jdk

# Setăm directorul de lucru în interiorul containerului
WORKDIR /app

# Copiem fișierele Maven Wrapper și pom.xml
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn ./.mvn
COPY pom.xml .

# Asigurăm că mvnw are permisiuni de execuție (chiar dacă pe Mac-ul tău le are deja, e bine să fie explicit pentru Docker)
RUN chmod +x mvnw

# Copiem codul sursă
COPY src ./src

# Construim aplicația Spring Boot
RUN ./mvnw clean install -DskipTests

# Odată construit, fișierul JAR executabil va fi în target/
# ARG JAR_FILE=target/*.jar     <-- Comentează/Șterge
# COPY ${JAR_FILE} app.jar      <-- Comentează/Șterge

# Expunem portul pe care Spring Boot rulează (implicit 8080)
EXPOSE 8080

# Comanda pentru a rula aplicația Spring Boot
# ENTRYPOINT ["java", "-jar", "app.jar"] <-- Comentează/Șterge