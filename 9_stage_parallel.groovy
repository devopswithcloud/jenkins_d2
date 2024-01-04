// Parallel
pipeline {
    agent any 
    stages {
        stage ('Stages Running in Parallel') {
            parallel {
                stage ('SonarScan') {
                    steps {
                        echo "Executing Sonar Scan"
                        sleep 10
                    }
                }
                stage ('FortifyScan') {
                    steps {
                        echo "Executing Fortify Scan"
                        sleep 10
                    }
                }
                stage ('Checkmarx Scan') {
                    steps {
                        echo "Executing Checkmarx Scan"
                        sleep 10
                    }
                }
            }
        }

    }
}

// 
pipeline {
    agent any 
    stages {
        stage ('Build') {
            steps {
                echo "Building the Cart service"
            }
        }
        stage ('Stages Running in Parallel') {
            parallel {
                stage ('SonarScan') {
                    steps {
                        echo "Executing Sonar Scan"
                        sleep 10
                    }
                }
                stage ('FortifyScan') {
                    steps {
                        echo "Executing Fortify Scan"
                        sleep 10
                       // error "Simulating error during fortify"
                    }
                }
                stage ('Checkmarx Scan') {
                    steps {
                        echo "Executing Checkmarx Scan"
                        sleep 10
                    }
                }
            }
        }
        stage ('Deploy to Dev') {
            steps {
                echo "Deploying cart service to dev"
            }
        }
        stage ('Deploy to Test') {
            steps {
                echo "Deploying cart service to test"
            }
        }
    }
}