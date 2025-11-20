# --- ETAPA 1: CONSTRUCCIÓN (BUILD) ---
# Usamos una imagen que ya tiene Maven y Java 17 instalados
FROM maven:3.8.5-openjdk-17 AS build

# Creamos una carpeta de trabajo dentro del servidor
WORKDIR /app

# Copiamos el archivo pom.xml de tu carpeta Pago al servidor
COPY pom.xml .

# Copiamos todo el código fuente (la carpeta src)
COPY src ./src

# Ejecutamos Maven para crear el archivo .jar (saltando los tests para ir más rápido)
RUN mvn clean package -DskipTests

# --- ETAPA 2: EJECUCIÓN (RUN) ---
# Ahora usamos una imagen más ligera solo con Java (sin Maven) para que pese menos
FROM eclipse-temurin:17-jdk-alpine

# Definimos dónde vivirá la app
WORKDIR /app

# Copiamos el archivo .jar que se creó en la ETAPA 1 a esta nueva etapa
# OJO: Aquí asumo que tu jar se llama igual que el artifactId en tu pom.xml
# El asterisco *.jar hace que copie cualquier archivo .jar que encuentre
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto (Render lo usa internamente)
EXPOSE 8080

# El comando final: ¡Arranca la aplicación!
ENTRYPOINT ["java", "-jar", "app.jar"]