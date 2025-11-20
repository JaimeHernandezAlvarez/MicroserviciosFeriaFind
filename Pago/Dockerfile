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

# --- EL COMANDO MAESTRO ---
# 1. Crea la carpeta /app/wallet
# 2. Copia los archivos de texto (tnsnames) que subiste a Secret Files (/etc/secrets) hacia /app/wallet
# 3. Decodifica la variable WALLET_B64 y crea el archivo cwallet.sso en /app/wallet
# 4. Arranca la aplicación
ENTRYPOINT ["/bin/sh", "-c", "mkdir -p /app/wallet && cp /etc/secrets/* /app/wallet/ 2>/dev/null || : && echo $WALLET_B64 | base64 -d > /app/wallet/cwallet.sso && java -jar app.jar"]