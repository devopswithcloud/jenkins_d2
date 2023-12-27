// This is the Comment

/* 
Multi line comments 
*/


//agent
// any: We will execute the pipeline or stage with any available agent/slave.
// label: idealy this is a string, which informs ourr jenkins to run on a particular slave.
// none: when we apply none , no global agent will be picked. The individual stage should specify the respective agent , based on their requirements.


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

// 2 ********************
pipeline {
    agent {
        label 'mvn-slave'
    }
    stages {
        stage('labelstage') {
            steps {
                // this should give me the private ip of my mvn slave
                sh 'hostname -i'
            }
        }
    }
}

// node agent 

pipeline {
    // the below agent is at the pipeline level,and applies for all stages
    agent none
    stages {
        stage ("Build") {
            // the below agent is for specific stage, 
            agent {
                node {
                    label 'mvn-slave'
                    customWorkspace "/home/siva/customsiva"
                }
            }
            steps {
                echo "Hello !, executing node in agent"
                sh "hostname -i"
                sh "cat imp.txt"
            }
        }
    }
}

// 

pipeline {
    // the below agent is at the pipeline level,and applies for all stages
    agent none
    stages {
        stage ("Build") {
            // the below agent is for specific stage, 
            agent {
                node {
                    label 'mvn-slave'
                    customWorkspace "/home/siva/customsiva"
                }
            }
            steps {
                echo "Hello !, executing node in agent"
                sh "hostname -i"
                sh "cat imp.txt"
                git branch: 'main', url: 'https://github.com/spring-projects/spring-petclinic.git'
                //sh 'git clone https://github.com/spring-projects/spring-petclinic.git'
            }
        }
    }
}