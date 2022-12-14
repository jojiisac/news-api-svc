
## Build and RUN  and package   locally 

###  Dependancies 
1. mvn 
2. java 11  
3. docker  

Run the follwing 


    # For   compiling 
    mvn   clean compile

    # For testing 
    mvn clean test 

    # For package 
    mvn   clean package 
    
    # For running 
    
    mvn spring-boot:run
    
    # OR  

    mvn   clean package 
    java -jar  ./target/news-service-0.0.6-SNAPSHOT.jar 

    For running with  and diff config file 
        java -jar target/News-api-0.0.4-SNAPSHOT.jar  --spring.config.location=<location to a=config file>
    
 Note  :   Name of jar file will vary as you chnages the version and name if the artifact in the pom.xml 



For accessing the service locally  
Using curl 
    
    curl http://localhost:8080/newsservice/api/v1/search/?q=ddd&N=3&interval=MINUTES
Using post man 
    use the attached collection is 'docs'  folder  

For seeing swagger spec
Use a modern browser to browse http://localhost:8080/swagger-ui/ 


## Build , package  and deply using jenkins 
1. a jenkins server with basic plungiing and the follwing 

    1. mvn 
    2. java 11  
    3. docker  
    4. kubectl 
    5. git 
2. A kubernetes cluster  
3. docker repo 
4. hithub repo 

### step 1   create  hit hub and docker repos 
1. Create a docker repo for storing docker images 
2. Update the file  contents fo the follwong file  with addrress of the docker repo
    File name : Jenkinsfile
    Line # :   21 & 26 

    File name : k8s/deployment.yaml
    Line # :   21
3  create an  git hub repo and upload this src code to hithub repo 
    
 ### step 2   create the follwing three  secrets in git hub 




1.  KUBECONFIG of the k8s cluster   
name "kubeconfigfile"
type :  "secretfile"
contents:  KUBECONFIG of the k8s cluster  where you wanted to deploy the apps 
2.  Updated config file secret of the of the app   
name "appsecret"
type :  "secretfile"
contents: Read below sesction how to create secrets file 
3.  Dockerhub Credential
    name : dockerhub-credential2
    type : Username and password type  
    Contents :  username and password to acces docker repo  
### step 3  jenkins build 
1. build pipeline 
Createa pipeline job using  new github repo  pipeline script file "Jenkinsfile"

2. deploy pipeline 
Create a pipeline job using  new github repo  pipeline script file "JenkinsDeploy"