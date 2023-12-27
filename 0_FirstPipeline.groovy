pipeline { // top level field
    agent any
    stages {
        stage ('FirstStage') { // Name: can be userfrriendly name, but needs to be specific fof the task performing
           steps {
            echo "Welcome to First Pipeline!!!!"
           } 
        }

    } 
}