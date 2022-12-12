

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
               
                sh "cp ./target/*.jar  ./docker/app.jar"
                sh "sudo docker build -t jojiisac/news-api docker/"
                sh "docker push jojiisac/news-api"
               
            }
        }
    }
}

