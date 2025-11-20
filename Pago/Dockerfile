# --- ETAPA 1: BUILD (Igual que antes) ---
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Copiamos la carpeta wallet para que Maven la tenga (por si acaso)
COPY wallet ./wallet
RUN mvn clean package -DskipTests

# --- ETAPA 2: RUN (Aquí está la magia simple) ---
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# 1. Copiamos el JAR
COPY --from=build /app/target/*.jar app.jar

# 2. Copiamos la carpeta wallet DIRECTAMENTE desde tu código
#    Docker la tomará de tu carpeta 'wallet' y la pondrá en /app/wallet del servidor
COPY wallet /app/wallet

# 3. Puerto
EXPOSE 8080

# 4. Arrancamos (Sin scripts raros, solo Java puro)
ENTRYPOINT ["java", "-jar", "app.jar"]