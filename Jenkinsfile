pipeline {
    agent any

    triggers {
            pollSCM('* * * * *')
    }

    environment {
        APPLICATION_NAME = 'sathishreactive'
    }

    stages {
        stage('Build') {
            steps {
                sh './mvnw clean verify'
            }
        }
    }
}
