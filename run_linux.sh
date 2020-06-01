#!/bin/bash

echo "Create Spring-Boot package"
mvn package -DskipTests

echo "Install Angular and Nodejs dependences"
cd angular
npm install
cd ..

echo "Run containers"
sudo docker-compose up --build