docker-compose down
./generate-docker.sh $1
docker-compose up -d --force-recreate zookeeper config-app register-app key-cloak mongo-data