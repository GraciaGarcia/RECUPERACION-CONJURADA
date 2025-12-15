# ========================================
# STAGE 1: Build con Maven
# ========================================
FROM maven:3.9-eclipse-temurin-17-alpine AS builder

WORKDIR /app

# Copiar archivos del proyecto
COPY pom.xml .
COPY src ./src

# Compilar y empaquetar (sin tests para ser más rápido)
RUN mvn clean package -DskipTests

# Extraer JAR en capas
RUN mkdir -p target/dependency && \
    cd target/dependency && \
    jar -xf ../*.jar

# ========================================
# STAGE 2: Runtime con JRE
# ========================================
FROM eclipse-temurin:17-jre-alpine AS runtime

# Crear usuario no-root para seguridad
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Establecer directorio de trabajo
WORKDIR /app

# Copiar las capas extraidas del JAR desde el builder
COPY --from=builder /app/target/dependency/BOOT-INF/lib /app/lib
COPY --from=builder /app/target/dependency/META-INF /app/META-INF
COPY --from=builder /app/target/dependency/BOOT-INF/classes /app

# Cambiar permisos al usuario no-root
RUN chown -R appuser:appgroup /app

# Cambiar al usuario no-root
USER appuser

# Exponer el puerto de la aplicacion
EXPOSE 8090

# Variables de entorno
ENV JAVA_OPTS="-Xms128m -Xmx256m"

# Comando de inicio optimizado
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -cp /app:/app/lib/* com.example.gracia.GraciaApplication"]

# Healthcheck
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:${PORT:-8090}/v1/api/student || exit 1
