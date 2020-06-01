#!/bin/bash

os_name=$(cat /etc/os-release | grep ID | sed 's/ID=//g' | head -n 1)
package_manager="apt"
installation_command="install"

function install_maven(){
	echo "Install maven"
	sudo $package_manager $installation_command maven
}

function install_node(){
		echo "Angular: Install Nodejs"

		if [ $os_name = "ubuntu" ] 
		then
			curl -sL https://deb.nodesource.com/setup_13.x | sudo -E bash -
			sudo apt-get install -y nodejs
		else
			echo "Install nodejs manually!"
			exit
		fi
}

mvn -v || install_maven
node -v || install_node

if [ ! -f target/websocket-0.2.jar ]
then
	echo "Packing Java Project to .jar file"
	mvn package -DskipTests
fi

if [ ! -d angular/node_modules ]
then
	cd angular
	echo "Angular: Install unnecessary packages"
	npm install
	cd ..
fi

echo "Run containers"
sudo docker-compose up --build
