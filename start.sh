./generate-docker.sh $1
docker-compose up -d --force-recreate zookeeper config-app register-app key-cloak mongo-data
sleep 10
docker-compose up -d --force-recreate kafka post-app feed-app profile-app api-gateway