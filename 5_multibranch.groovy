pipeline {
    agent any 
    stages {
        stage ('Build') {
            steps {
                echo "Executing Multi branch pipeline"
            }
        }
    }
}

// follow the session video for various scenarios, we discussed on the multi branch pipeline