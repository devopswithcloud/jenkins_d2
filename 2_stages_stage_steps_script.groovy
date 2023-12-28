// Script allows us to write custom code in Groovy
// This script block, should be available in steps
// If we are having any complex environment, we can use script unders steps block.
// Scrip uses groovy as the progamming language
pipeline {
    agent any 
    stages {
        stage ("hello") {
            steps {
                echo "hello! Mr Siva"
            }
        }
        stage ('ScriptedStage') {
            steps {
                script {
                    def course = "k8s" // static definition
                    if (course == "k8s") { // $(params.name)
                        println ("Thanks for enrolling to ${course}")
                    }
                    else
                        println("Do enroll")
                    // just sleep for 5 seconds
                    // https://www.jenkins.io/doc/pipeline/steps/workflow-basic-steps/#sleep-sleep
                    // This will pause the pipelines , till the time mentioned
                    sleep 5 // sh "sleep 5"
                    echo "********************** Script block ended **********************"
                }
            }
        }
    }
}

