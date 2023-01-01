./build.sh
sleep 10
if [ -z "$1" ]
  then
    profile="dev,feign"
fi
docker-compose build --no-cache --build-arg PROFILE=$profile