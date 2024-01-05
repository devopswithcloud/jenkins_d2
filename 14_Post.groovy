pipeline {
    agent any 
    stages {
        stage ('Build') {
            steps {
                error("Failing the stage intentionally")
                //echo "Building the application"
            }
        }
    }
    post {
        // this will only execute if the pipleline/stage has a "Success" status
        // with blue/green in UI
        success {
            // You can keep what ever u want here 
            // typicaly we use mailers here
            echo "Post activity ..... Success is called"
        }
        // this will only execute if the pipeline/stage has a "failed" status
        failure {
            echo "Post activity ..... failed is called"
        }
        // Always 
        always {
            // Mail notification is typicall used here
            echo "Always Block Triggered"
        }
    }
}