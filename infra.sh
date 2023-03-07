systemctl stop mongod
docker-compose up -d --force-recreate zookeeper config-app register-app key-cloak mongo-data