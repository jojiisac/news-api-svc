

pipeline {
   agent any
    stages {
        stage('Build App') { 
            steps {
               
                sh " /var/lib/mavan/bin/mvn -Dmaven.test.failure.ignore=true clean package"
                //junit '*/build/test-results/*.xml'
                step( [ $class: 'JacocoPublisher' ] )
            }
        }
         stage('Build Docker') { 
            steps {
                  withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'dockerhub-credential2', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {

               
               
                    sh "cp ./target/*.jar  ./docker/app.jar"
                    sh "docker build -t jojiisacth/news-api:v5 docker/"
                    sh "docker images"

                    sh " docker login -u ${USERNAME}  -p ${PASSWORD}  "

                    sh "docker push jojiisacth/news-api:v5"
                 }
               
            }
        }
    }
}

