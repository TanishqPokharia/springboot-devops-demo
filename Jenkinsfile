# jenkins/Jenkinsfile
pipeline {
    agent any
    
    tools {
        maven 'Maven 3.8.1'
    }
    
    environment {
        // Docker Hub credentials ID (configure in Jenkins)
        DOCKER_HUB_CREDENTIALS = 'docker-hub-creds'
        
        // Docker image details
        DOCKER_IMAGE_NAME = 'spring-devops-demo'
        DOCKER_HUB_USERNAME = 'tanishqpokharia' 
        DOCKER_IMAGE_TAG = "${BUILD_NUMBER}"
        DOCKER_IMAGE_FULL = "${DOCKER_HUB_USERNAME}/${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}"
        
        // EC2 deployment details
        EC2_HOST = '13.201.57.158'
        APP_PORT = '9090'
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out source code...'
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                echo 'Building application with Maven...'
                sh '''
                    cd spring-devops-demo
                    mvn clean compile
                '''
            }
        }
        
        stage('Test') {
            steps {
                echo 'Running unit tests...'
                sh '''
                    cd spring-devops-demo
                    mvn test
                '''
            }
            post {
                always {
                    // Publish test results
                    publishTestResults testResultsPattern: 'spring-devops-demo/target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('Package') {
            steps {
                echo 'Packaging application...'
                sh '''
                    cd spring-devops-demo
                    mvn package -DskipTests
                '''
            }
            post {
                success {
                    // Archive the JAR file
                    archiveArtifacts artifacts: 'spring-devops-demo/target/*.jar', allowEmptyArchive: false
                }
            }
        }
        
        stage('Build Docker Image') {
            steps {
                echo "Building Docker image: ${DOCKER_IMAGE_FULL}"
                script {
                    // Copy Dockerfile to the Spring Boot project directory
                    sh '''
                        cp vle6/docker/Dockerfile spring-devops-demo/
                        cp vle6/docker/.dockerignore spring-devops-demo/
                    '''
                    
                    // Build Docker image
                    dir('spring-devops-demo') {
                        sh "docker build -t ${DOCKER_IMAGE_FULL} ."
                        sh "docker tag ${DOCKER_IMAGE_FULL} ${DOCKER_HUB_USERNAME}/${DOCKER_IMAGE_NAME}:latest"
                    }
                }
            }
        }
        
        stage('Push Docker Image') {
            steps {
                echo "Pushing Docker image to Docker Hub..."
                script {
                    withCredentials([usernamePassword(credentialsId: "${DOCKER_HUB_CREDENTIALS}", 
                                                    usernameVariable: 'DOCKER_USERNAME', 
                                                    passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh '''
                            echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin
                            docker push ${DOCKER_IMAGE_FULL}
                            docker push ${DOCKER_HUB_USERNAME}/${DOCKER_IMAGE_NAME}:latest
                        '''
                    }
                }
            }
        }
        
        stage('Deploy to EC2') {
            steps {
                echo "Deploying to EC2 instance..."
                script {
                    // Stop existing container if running
                    sh """
                        ssh -o StrictHostKeyChecking=no ec2-user@${EC2_HOST} '
                            sudo docker stop spring-devops-demo || true
                            sudo docker rm spring-devops-demo || true
                            sudo docker rmi ${DOCKER_HUB_USERNAME}/${DOCKER_IMAGE_NAME}:latest || true
                        '
                    """
                    
                    // Pull and run new container
                    sh """
                        ssh -o StrictHostKeyChecking=no ec2-user@${EC2_HOST} '
                            sudo docker pull ${DOCKER_IMAGE_FULL}
                            sudo docker run -d \\
                                --name spring-devops-demo \\
                                -p ${APP_PORT}:${APP_PORT} \\
                                --restart unless-stopped \\
                                ${DOCKER_IMAGE_FULL}
                        '
                    """
                }
            }
        }
        
        stage('Verify Deployment') {
            steps {
                echo "Verifying deployment..."
                script {
                    // Check if container is running
                    sh """
                        ssh -o StrictHostKeyChecking=no ec2-user@${EC2_HOST} '
                            sudo docker ps | grep spring-devops-demo || echo "Container not running"
                        '
                    """
                    
                    // Wait for application to start
                    sleep(time: 30, unit: "SECONDS")
                    
                    // Health check
                    def healthCheck = sh(
                        script: "curl -f http://${EC2_HOST}:${APP_PORT}/actuator/health || echo 'Health check failed'",
                        returnStdout: true
                    ).trim()
                    
                    echo "Health check result: ${healthCheck}"
                    echo "Application deployed successfully!"
                    echo "Application URL: http://${EC2_HOST}:${APP_PORT}"
                    echo "Health endpoint: http://${EC2_HOST}:${APP_PORT}/actuator/health"
                }
            }
        }
    }
    
    post {
        always {
            echo 'Pipeline execution completed.'
            // Clean up Docker images on Jenkins server to save space
            sh '''
                docker rmi ${DOCKER_IMAGE_FULL} || true
                docker rmi ${DOCKER_HUB_USERNAME}/${DOCKER_IMAGE_NAME}:latest || true
            '''
        }
        success {
            echo 'Pipeline executed successfully!'
            // Send success notification (configure as needed)
        }
        failure {
            echo 'Pipeline execution failed!'
            // Send failure notification (configure as needed)
        }
    }
}