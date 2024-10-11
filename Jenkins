pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Use the correct branch name for your repository
                git branch: 'master', url: 'https://github.com/ntnmnk/GeektrustBackend_RideSharing.git'
            }
        }

        stage('Build') {
            steps {
                // Use 'bat' for Windows compatibility
                bat './gradlew clean build'
            }
        }

        stage('Package') {
            steps {
                // Use 'bat' for Windows compatibility
                bat './gradlew bootJar'
            }
        }

        stage('Deploy') {
            steps {
                // Update the SCP command as necessary
                bat 'winscp.com /command "open scp://your-username@192.168.140.215" "put build/libs/*.jar C:\\" "exit"'
            }
        }
    }

    post {
        success {
            echo 'Build and Deploy succeeded!'
        }
        failure {
            echo 'Build or Deploy failed!'
        }
    }
}
