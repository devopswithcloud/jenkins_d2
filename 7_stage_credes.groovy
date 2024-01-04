pipeline {
    agent any 
    environment {
        // credentials('id') , this id should be the same from jenkins credeentials 
        GITHUB_CREDS = credentials('devops_github_creds')
        name = "Siva"
    }
    stages {
        stage ("Build") {
            steps {
                echo "Github Credentials are ${GITHUB_CREDS}"
                echo "User id is : ${GITHUB_CREDS_USR}"
                echo "Password is: ${GITHUB_CREDS_PSW}"
            }
        }
    }
}


