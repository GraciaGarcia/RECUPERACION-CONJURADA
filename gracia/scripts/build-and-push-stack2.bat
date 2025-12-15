@echo off
echo ========================================
echo BUILD Y PUSH - STACK 2
echo Imagen: 60071325/ht-03-gracia-garcia
echo ========================================
echo.

cd ..

echo [1/4] Construyendo imagen Docker...
docker build -t 60071325/ht-03-gracia-garcia:latest -f Dockerfile .

if %errorlevel% neq 0 (
    echo ERROR: Fallo la construccion de la imagen
    pause
    exit /b 1
)

echo.
echo [2/4] Verificando tamano de la imagen...
docker images 60071325/ht-03-gracia-garcia:latest

echo.
echo [3/4] Iniciando sesion en Docker Hub...
echo Por favor ingresa tus credenciales de Docker Hub:
docker login

if %errorlevel% neq 0 (
    echo ERROR: Fallo el login en Docker Hub
    pause
    exit /b 1
)

echo.
echo [4/4] Subiendo imagen a Docker Hub...
docker push 60071325/ht-03-gracia-garcia:latest

if %errorlevel% neq 0 (
    echo ERROR: Fallo el push a Docker Hub
    pause
    exit /b 1
)

echo.
echo ========================================
echo COMPLETADO EXITOSAMENTE
echo ========================================
echo Imagen disponible en:
echo https://hub.docker.com/r/60071325/ht-03-gracia-garcia
echo.
pause
