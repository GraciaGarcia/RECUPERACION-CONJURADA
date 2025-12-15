@echo off
echo ========================================
echo PROBANDO ENDPOINTS - STACK 1
echo ========================================
echo.

echo [1] GET - Obtener todos los estudiantes:
curl http://localhost:8091/v1/api/student
echo.
echo.

echo [2] POST - Crear estudiante:
curl -X POST http://localhost:8091/v1/api/student ^
  -H "Content-Type: application/json" ^
  -d "{\"dni\":\"87654321\",\"firstName\":\"Gracia\",\"lastName\":\"Garcia\",\"promotion\":232}"
echo.
echo.

echo [3] GET - Verificar que se creo:
curl http://localhost:8091/v1/api/student
echo.
echo.

echo [4] Probar Nginx (puerto 8092):
curl http://localhost:8092/v1/api/student
echo.
echo.

echo ========================================
echo PRUEBAS COMPLETADAS
echo ========================================
pause
