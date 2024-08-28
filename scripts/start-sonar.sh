#!/usr/bin/env bash

docker rm -f ericsson-sonarqube

docker run -d --name ericsson-sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest

mvn clean install -dskipTests

mvn clean test

mvn clean verify sonar:sonar -Dsonar.projectKey=Open-API-Developer-Portal -Dsonar.projectName="Open API Developer Portal" -Dsonar.host.url=http://localhost:9000 -Dsonar.token=sqp_c7e7a10f0fa2ae319216fa6ea9a7237e9365cfd0