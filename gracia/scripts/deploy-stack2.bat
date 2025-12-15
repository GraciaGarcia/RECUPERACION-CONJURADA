@echo off
echo ========================================
echo DEPLOY KUBERNETES - STACK 2
echo ========================================
echo.

cd ..

echo [1/4] Creando namespace...
kubectl apply -f k8s/stack2/graciagarcia-03-namespace.yml

echo.
echo [2/4] Creando secret (puerto flexible)...
kubectl apply -f k8s/stack2/graciagarcia-03-secret.yml

echo.
echo [3/4] Creando deployment (3 replicas, puerto 9090)...
kubectl apply -f k8s/stack2/graciagarcia-03-deployment.yml

echo.
echo [4/4] Creando service (NodePort)...
kubectl apply -f k8s/stack2/graciagarcia-03-service.yml

echo.
echo ========================================
echo VERIFICANDO DESPLIEGUE
echo ========================================
echo.

echo Esperando que los pods esten listos...
kubectl wait --for=condition=ready pod -l app=graciagarcia-03-app -n graciagarcia-03-namespace --timeout=120s

echo.
echo Estado de los recursos:
kubectl get all -n graciagarcia-03-namespace

echo.
echo ========================================
echo ACCESO A LA APLICACION
echo ========================================
echo NodePort: http://localhost:30903/v1/api/student
echo.
pause
