#!/bin/bash

function install_maven(){
	echo "Install maven"
	sudo apt install maven
}

function install_node(){
	echo "Angular: Install Nodejs"
	curl -sL https://deb.nodesource.com/setup_13.x | sudo -E bash -
	sudo apt-get install -y nodejs
}

mvn -v || install_maven
node -v || install_node

if [ ! -f target/websocket-0.2.jar ]
then
	echo "Create Spring-Boot package"
	mvn package -DskipTests
fi

if [ ! -d angular/node_modules ]
then
	cd angular
	echo "Install Angular and Nodejs dependences"
	npm install
	cd ..
fi

echo "Run containers"
sudo docker-compose up --build
