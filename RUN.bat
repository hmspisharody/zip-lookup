call .\mvnw clean install -DskipTests

call docker rmi zip-lookup:1.0
call docker build -t harikrishnanms/zip-lookup:1.0 .

call docker-compose -f docker-compose.yml up --detach

