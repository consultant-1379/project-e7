#!/bin/bash

# Define color codes
YELLOW="\e[1;33m"
GREEN="\e[1;32m"
RESET="\e[0m"

clean_project() {
    echo -e "${YELLOW}[INFO] Cleaning the project...${RESET}"
    kubectl delete daemonsets,replicasets,services,deployments,pods,rc,ingress --all --all-namespaces
    kubectl delete namespace openapi-developer-portal-app-namespace
    docker rm -f $(docker ps -a -q)
    docker rmi -f $(docker images -a -q)
    docker network rm openapi-developer-portal-network
    echo -e "${GREEN}[INFO] Project cleaned successfully${RESET}"
    echo -e ""
}

clean_namespace() {
    echo -e "${YELLOW}[INFO] Cleaning the namespace...${RESET}"
    kubectl delete namespace openapi-developer-portal-app-namespace
    echo -e "${GREEN}[INFO] Namespace cleaned successfully${RESET}"
    echo -e ""
}

pull_mongo() {
    docker pull mongo
}

build_network() {
    echo -e "${YELLOW}[INFO] Building Docker network...${RESET}"
    docker network create openapi-developer-portal-network
    echo -e "${GREEN}[INFO] Docker network built successfully${RESET}"
    echo -e ""
}

build_package() {
    echo -e "${YELLOW}[INFO] Building package...${RESET}"
    cd openapi-developer-portal-backend
    mvn clean install -DskipTests
    cd ..
    echo -e "${GREEN}[INFO] Package built successfully${RESET}"
    echo -e ""
}

build_sonar() {
    echo -e "${YELLOW}[INFO] Building SonarQube...${RESET}"
    docker stop sonarqube
    docker rm sonarqube
    docker pull sonarqube
    docker run -d --name sonarqube -p 9000:9000 sonarqube
    echo -e "${GREEN}[INFO] SonarQube built successfully${RESET}"
    echo -e ""
}

build_images() {
    echo -e "${YELLOW}[INFO] Building Docker images app...${RESET}"

    cd openapi-developer-portal-backend
    docker build -t ericsson/openapi-developer-portal-backend .
    cd ..

    cd openapi-developer-portal-frontend
    docker build -t ericsson/openapi-developer-portal-frontend .
    cd ..

    echo -e "${GREEN}[INFO] Backend Docker image built successfully${RESET}"
    echo -e ""
}

build_namespace() {
    echo -e "${YELLOW}[INFO] Creating Kubernetes namespace...${RESET}"
    kubectl create namespace openapi-developer-portal-app-namespace
    echo -e "${GREEN}[INFO] Kubernetes namespace created successfully${RESET}"
    echo -e ""

    echo -e "${YELLOW}[INFO] Applying Kubernetes configuration...${RESET}"
    kubectl apply -f kubernetes
    echo -e "${GREEN}[INFO] Kubernetes configuration applied successfully${RESET}"
    echo -e ""

    echo -e "${YELLOW}[INFO] Getting services in the namespace...${RESET}"
    kubectl get services --namespace openapi-developer-portal-app-namespace
    echo -e "${GREEN}[INFO] Services retrieved successfully${RESET}"
    echo -e ""

    echo -e "${YELLOW}[INFO] Getting pods in the namespace...${RESET}"
    kubectl get pods --namespace openapi-developer-portal-app-namespace
    echo -e "${GREEN}[INFO] Pods retrieved successfully${RESET}"
    echo -e ""
}

forward_backend() {
    echo -e "${YELLOW}[INFO] Port forwarding to the backend service...${RESET}"
    kubectl port-forward service/openapi-developer-portal-backend-service 8080:8080 --namespace openapi-developer-portal-app-namespace
    echo -e "${GREEN}[INFO] Port forwarding started successfully${RESET}"
    echo -e ""
}

forward_frontend() {
    echo -e "${YELLOW}[INFO] Port forwarding to the frontend service...${RESET}"
    kubectl port-forward service/openapi-developer-portal-frontend-service 3000:3000 --namespace openapi-developer-portal-app-namespace
    echo -e "${GREEN}[INFO] Port forwarding started successfully${RESET}"
    echo -e ""
}

forward_sonarqube() {
    echo -e "${YELLOW}[INFO] Port forwarding to the sonarqube service...${RESET}"
    kubectl port-forward service/sonarqube-service 9000:9000 --namespace openapi-developer-portal-app-namespace
    echo -e "${GREEN}[INFO] Port forwarding started successfully${RESET}"
    echo -e ""
}

scale_backend() {
    echo -e "${YELLOW}[INFO] Scaling backend deployment...${RESET}"
    kubectl scale deployments openapi-developer-portal-backend-deploy --replicas=10 --namespace openapi-developer-portal-app-namespace
    echo -e "${GREEN}[INFO] Backend deployment scaled to 10 replicas${RESET}"
    echo -e ""
}

wait_for_pods() {
    echo -e "${YELLOW}[INFO] Waiting for pods to be ready...${RESET}"
    kubectl wait --for=condition=ready pod --all --timeout=60s --namespace openapi-developer-portal-app-namespace
    echo -e "${GREEN}[INFO] Pods are ready${RESET}"
    echo -e ""
}

clean_flag=false

while getopts "cs" opt; do
    case $opt in
    c)
        clean_flag=true
        ;;
    \?)
        echo "Invalid option: -$OPTARG" >&2
        exit 1
        ;;
    esac
done

if $clean_flag; then
    clean_namespace
fi

pull_mongo

build_sonar
build_network
build_package
build_images
build_namespace

wait_for_pods

# scale_backend

forward_backend &
forward_frontend &
# forward_sonarqube &

wait
