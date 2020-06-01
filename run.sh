#!/bin/bash

if [ -z $(which mvn) ]
then
	echo "Install maven"
	sudo apt install maven
fi

if [ -z $(ls target/websocket-0.2.jar) ]
then
	echo "Packing Java Project to .jar file"
	mvn package -DskipTests
fi

if [ ! -d angular/node_modules ]
then
	if [ -z $(which node) ]
	then
		echo "Angular: Install Nodejs"
		curl -sL https://deb.nodesource.com/setup_13.x | sudo -E bash -
		sudo apt-get install -y nodejs
	fi
	cd angular
	echo "Angular: Install unnecessary packages"
	npm install
	cd ..
fi

echo "Run containers"
sudo docker-compose up --build
