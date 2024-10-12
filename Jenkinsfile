pipeline {
    agent any
    stages {
        stage('Clone Repository') {
            steps {
                script {
                    // Change to the C:\ directory before cloning
                    bat 'cd C:\\deploy\\ && git clone https://github.com/ntnmnk/GeektrustBackend_RideSharing.git'
                }
            }
        }
        stage('Build') {
            steps {
                script {
                    // Navigate to the cloned repository directory and build the project
                    bat 'cd C:\\deploy\\GeektrustBackend_RideSharing && gradle clean build'
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    // Find the JAR file in the build/libs directory
                    def jarFilePath = 'C:\\deploy\\GeektrustBackend_RideSharing\\build\\libs\\'
                    def jarName = ''

                    // List files in the directory and filter for JAR files
                    def files = bat(script: "dir /B ${jarFilePath}*.jar", returnStdout: true).trim().split('\n')
                    if (files) {
                        // Get the first JAR file found (you can adjust the logic if needed)
                        jarName = files[0]
                        echo "Found JAR file: ${jarName}"
                    } else {
                        error 'No JAR file found!'
                    }

                    // Deploy your application using the found JAR file
                    bat "cd ${jarFilePath} && java -jar ${jarName}"
                }
            }
        }
        stage('Run Background Process') {
            steps {
                script {
                    // Run a command in the background (PowerShell example)
                    bat 'start /B powershell -Command "echo Background process started; Start-Sleep -Seconds 60; echo Background process completed"'
                }
            }
        }
    }
    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
