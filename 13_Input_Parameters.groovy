// let write a bit complex one with both input and parameters.
// stage > input and parameters 
pipeline {
    agent any 
    stages {
        stage ('Deploy to Dev') {
            steps {
                echo "Deploying to dev env Succesfully"
            }
        }
        stage ('Deploy to Prod') {
            options {
                timeout(time: 600, unit: 'SECONDS')
            }
            input {
                message "Should We Continue ???"
                ok "Approved"
                submitter "krish" // maha, krish , by default admins can approve, no need to provide the name here 
                submitterParameter "whoApproved"
                parameters {
                    string(name: 'CHANGE_TICKET', defaultValue: 'CH12345', description: 'Please Enter Change Ticket number')
                    booleanParam(name: 'SRE Approved ????', defaultValue: true, description: 'Is approval taken from SRE??')
                    choice(name: 'Release', choices: 'Regular\nHotfix', description: 'What type of release is this ??')
                    text(name: 'Notes', defaultValue: "Enter release notes if any.....", description: 'Release Notes')
                    password(name: 'myPassword', defaultValue: 'myPasswordValue', description: 'Enter the password')
                    credentials(name: 'myCredentials', description: 'My Credentials stored', required: true)
                }
            }
            steps {
                echo "The Change Ticket is ${CHANGE_TICKET}"
                echo "Deploying to Production"
                echo "This is a ${Release} Release"
                echo "Approved by ${whoApproved}"
            }
        }
    }
}

// pipeline >> params.NAME
// stage >.>.input >>>> parameters >>> NAME