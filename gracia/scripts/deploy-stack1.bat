@echo off
echo ========================================
echo DEPLOY KUBERNETES - STACK 1
echo ========================================
echo.

cd ..

echo [1/3] Creando namespace...
kubectl apply -f k8s/stack1/graciagarcia-namespace.yml

echo.
echo [2/3] Creando deployment (3 replicas)...
kubectl apply -f k8s/stack1/graciagarcia-deployment.yml

echo.
echo [3/3] Creando service (NodePort)...
kubectl apply -f k8s/stack1/graciagarcia-service.yml

echo.
echo ========================================
echo VERIFICANDO DESPLIEGUE
echo ========================================
echo.

echo Esperando que los pods esten listos...
kubectl wait --for=condition=ready pod -l app=graciagarcia-app -n graciagarcia-namespace --timeout=120s

echo.
echo Estado de los recursos:
kubectl get all -n graciagarcia-namespace

echo.
echo ========================================
echo ACCESO A LA APLICACION
echo ========================================
echo NodePort: http://localhost:30093/v1/api/student
echo.
echo Para crear port-forward al puerto 8094:
echo kubectl port-forward -n graciagarcia-namespace service/graciagarcia-service 8094:80
echo Luego accede a: http://localhost:8094/v1/api/student
echo.
pause
