

pipeline {
   agent any
    stages {
        stage('Build') { 
            steps {
               
                sh "mvn -Dmaven.test.failure.ignore=true clean package"
                //junit '*/build/test-results/*.xml'
                step( [ $class: 'JacocoPublisher' ] )
            }
        }
    }
}

