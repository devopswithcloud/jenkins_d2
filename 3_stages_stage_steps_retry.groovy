// retry
pipeline {
    agent any
    stages {
        stage ('Build') {
            steps {
                retry (3) {
                    echo "Welocme to Pipeline"
                    error "Testing the Retry block"
                }
                echo "Printing after 3 retrys"
            }
        }
    }
}
// timeout
pipeline {
    agent any 
    stages {
        stage ('Build') {
            steps {
                //https://www.jenkins.io/doc/pipeline/steps/workflow-basic-steps/#timeout-enforce-time-limit
                timeout (time: 2, unit: 'SECONDS') { //Values: NANOSECONDS, MICROSECONDS, MILLISECONDS, SECONDS, MINUTES, HOURS, DAYS 
                    // timeout block code 
                    echo "Sleeping for 60 seconds"
                    sleep 60 
                    // manager approval 
                }
            }
        }
    }
}

// Try an example with both retry an timeout