// when condition, should have atleast one condition
// https://www.jenkins.io/doc/book/pipeline/syntax/#when

pipeline {
    agent any 
    environment {
        DEPLOY_TO = 'production'
    }
    stages {
        stage ('Deploy') {
            when {
                environment name: 'DEPLOY_TO', value: 'production'
            }
            steps {
                echo "Deploying"
            }
        }
    }
}

// Equals
pipeline {
    agent any 
    environment {
        DEPLOY_TO = 'production'
    }
    stages {
        stage ('Deploy') {
            when {
                //equals expected: 'production', actual: "${DEPLOY_TO}"
                //equals expected: 5, actual: "${BUILD_NUMBER}"
                equals expected: 5 , actual: currentBuild.number
                //environment name: 'DEPLOY_TO', value: 'production'
            }
            steps {
                echo "Deploying"
            }
        }
    }
}

// Not Condition
pipeline {
    agent any 
    environment {
        DEPLOY_TO = 'production'
    }
    stages {
        stage ('Deploy') {
            when {
                not {
                    equals expected: 'prod', actual: "${DEPLOY_TO}"
                }
                //equals expected: 5, actual: "${BUILD_NUMBER}"
               //equals expected: 5 , actual: currentBuild.number
                //environment name: 'DEPLOY_TO', value: 'production'
            }
            steps {
                echo "Deploying"
            }
        }
    }
}

// Branch based deployment
// for this it should be multi branch pipeline
pipeline {
    agent any 
    stages {
        stage ('Build') {
            steps {
                echo "Welcome to Build Stage"
            }
        }
        stage ('Depkoy to dev') {
            steps {
                echo "Deploying to dev environment"
            }
        }
        stage ('Deploy to Stage') {
            when {
                expression {
                    // stage should execute with either production branch or staging branch
                    BRANCH_NAME ==~ /(production|staging)/
                }
            }
            steps {
                echo "Deploying to Stage Environment "
            }
        }
    }
}


//allOf, and anyOf
// allOf
pipeline {
    agent any 
    environment {
        DEPLOY_TO = 'production' // this is static, we shall see about dynamic in parameter section
    }
    stages {
        stage ('Build') {
            steps {
                echo "Welcome to Build Stage"
            }
        }
        stage ('Depkoy to dev') {
            steps {
                echo "Deploying to dev environment"
            }
        }
        stage ('Deploy to Stage') {
            when {
                allOf {
                    // the below all conditions should be satisfied inorder for this stage tot execute
                    branch 'production'
                    environment name: 'DEPLOY_TO', value: 'production'
                }
            }
            steps {
                echo "Deploying to Stage Environment "
            }
        }
    }
}

//anyOf
pipeline {
    agent any 
    environment {
        DEPLOY_TO = 'production' // this is static, we shall see about dynamic in parameter section
    }
    stages {
        stage ('Build') {
            steps {
                echo "Welcome to Build Stage"
            }
        }
        stage ('Depkoy to dev') {
            steps {
                echo "Deploying to dev environment"
            }
        }
        stage ('Deploy to Stage') {
            when {
                anyOf {
                    // any of the below condition can be satisifed for this stage to be executed 
                    branch 'production'
                    environment name: 'DEPLOY_TO', value: 'productions'
                }
            }
            steps {
                echo "Deploying to Stage Environment "
            }
        }
    }
}


// I want to execute stage environment when the branch is release/****
pipeline {
    agent any 
    stages {
        stage ('Build') {
            when {
                branch 'release-*'
            }
            steps {
                echo "Deploying to Stage environment"
            }
        }
    }
}

// I want to execute stage environment when the branch is release/**** and tag in prod only
pipeline {
    agent any 
    stages {
        stage ('UAT') {
            when {
                branch 'release-*'
            }
            steps {
                echo "Deploying to Stage environment"
            }
        }
        stage ('Prod') {
            when {
                // this stage should execute with tag only
                //buildingTag()
                // v1.2.3
                // v.1.2.3
                // v1.0
                tag pattern: "v\\d{1,2}.\\d{1,2}.\\d{1,2}", comparator: "REGEXP"
            }
            steps {
                echo "Deploying to Production environment"
            }
        }
    }
}