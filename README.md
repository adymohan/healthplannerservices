# healthplannerAPI

Install Apcha Maven 3.6+

Goto the root of this project directry and execute,
mvn clean package -DskipTests

Install Docker on your maching 

Build the image:
docker build -t <IMAGE_NAME> .

Run the container:
docker run -it --name=<CONTAINER_NAME> -p 8080:8080 <IMAGE_NAME>:latest

Once testing is succfull push the image to DockerHub or IBM Cloud Registry and from there deploy on Openshift Cluster
