

#  News Search Micro Service 
####  A simple micro service for searching news  acroos globe
This service finds relevant news for a particular keyword (Ex: “apple”). Group the results in buckets based on ‘publishedAt’ date intervals (last N minutes, hours, days, weeks, months, years) specified by the inputs – N & Interval


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


## Build and and package  using jenkins 
 
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





    create

    


    kubectl create code . 
generic ms --from-file=C:\Users\jojii\Downloads\actuatordemo\src\main\resources\application.yaml


________________________________________________________







#  News Search Micro Service 


####  A simple micro service for searching news  acroos globe

This service finds relevant news for a particular keyword (Ex: “apple”). Group the results in buckets based on ‘publishedAt’ date intervals (last N minutes, hours, days, weeks, months, years) specified by the inputs – N & Interval


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
        java -jar target/News-api-0.0.4-SNAPSHOT.jar  --spring.config.location=D:\joji\news-api\src\main\resources\application.yaml
    
 Note  :   Name of jar file will vary as you chnages the version and name if the artifact in the pom.xml 



For accessing the service locally  
    
    curl http://localhost:8080/newsservice/api/v1/search/?q=ddd&N=3&interval=MINUTES




    


    



 



## Build and and package  using jenkins 
ruthe following 




How to expose 


docker run -d -p 8081:8081 jojiisac/news-api





##News Search  Service 


How to run 


    # compiling 
    mvn   clean compile
    # For testing 
    mvn clean test 
    # for package 
    mvn   clean package 
    
    # for running 
    mvn spring-boot:run
    # OR  
    mvn   clean package 
    java -jar  ./target/news-service-0.0.1-SNAPSHOT.jar


    For accessing the service 
    
    curl http://localhost:8080/
    # NOte see details using paost man  collection in the resources 

    curl http://localhost:8081/newsservice/api/v1/search/?q=ddd&N=3&interval=MINUTES


java -jar target/News-api-0.0.4-SNAPSHOT.jar  --spring.config.location=D:\joji\news-api\src\main\resources\application.yaml

    


    


    kubectl create secret generic ms --from-file=C:\Users\jojii\Downloads\actuatordemo\src\main\resources\application.yaml

