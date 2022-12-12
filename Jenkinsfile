

pipeline {
   agent any
    stages {
        stage('Build') { 
            steps {
               
                sh " /var/lib/mavan/bin/mvn -Dmaven.test.failure.ignore=true clean package"
                //junit '*/build/test-results/*.xml'
                step( [ $class: 'JacocoPublisher' ] )
            }
        }
    }
}

