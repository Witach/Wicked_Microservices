docker-compose up -d zookeeper config-app register-app key-cloak mongo-data
sleep 10
docker-compose up -d kafka post-app feed-app profile-app