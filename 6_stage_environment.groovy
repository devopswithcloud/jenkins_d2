// This environment block can be used at pipeline level and stage level

pipeline {
    agent any
    // this env variables can be used across all the stages
    environment {
        // Key value pairs
        name = "Siva"
        course = "k8s"
    }
    stages {
        stage ('Build') {
            // these environment varibles are specific to this stage only
            environment {
                cloud = "GCP"
            }
            steps {
                echo "Welcome ${name}"
                echo "You enrolled to ${course} Course"
                echo "You are certified in ${cloud}"
            }
        }
    }
}
//
// This environment block can be used at pipeline level and stage level

pipeline {
    agent any
    // this env variables can be used across all the stages
    environment {
        // Key value pairs
        name = "Siva"
        course = "k8s"
    }
    stages {
        stage ('Build') {
            // these environment varibles are specific to this stage only
            environment {
                cloud = "GCP"
            }
            steps {
                echo "Welcome ${name}"
                echo "You enrolled to ${course} Course"
                echo "You are certified in ${cloud}"
            }
        }
        stage ('SecondStage') {
            steps {
                echo "Welcome ${name}" 
                echo "You enrolled to ${course} Course" 
                echo "You are certified in ${cloud}" 
            }
        }
    }
}

// Lets test presedence

pipeline {
    agent any
    // this env variables can be used across all the stages
    environment {
        // Key value pairs
        name = "Siva"
        course = "k8s"
    }
    stages {
        stage ('Build') {
            // these environment varibles are specific to this stage only
            environment {
                cloud = "GCP"
                name = "Maha"
            }
            steps {
                echo "Welcome ${name}"
                echo "You enrolled to ${course} Course"
                echo "You are certified in ${cloud}"
            }
        }
    }
}

// printenv
pipeline {
    agent any
    // this env variables can be used across all the stages
    environment {
        // Key value pairs
        name = "Siva"
        course = "k8s"
    }
    stages {
        stage ('Build') {
            // these environment varibles are specific to this stage only
            environment {
                cloud = "GCP"
                name = "Maha"
            }
            steps {
                echo "Welcome ${name}"
                echo "You enrolled to ${course} Course"
                echo "You are certified in ${cloud}"
                sh "printenv"
            }
        }
    }
}