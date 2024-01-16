// Implementing CICD for Spring3 project
// This includes, build, nexus, tomcat

pipeline {
    agent any 
    tools {
        maven 'MyMaven'
    }
    environment {
        TOMCAT_CREDS = credentials('tomcat_creds') // this is having my username and password
        //NEXUS_CREDS = credentials('nexus_creds')
        NEXUS_VERSION = 'nexus3'
        // http or https
        NEXUS_PROTOCOL = "http"
        // This is the Nexus Instance URL
        NEXUS_URL = "34.125.89.53:8081"
        //34.34.56.78:8081
        NEXUS_REPO = "spring-repo"
        //http://34.125.89.53:8081/repository/sivaaaa-repo/
    }
    stages {
        stage ('clone'){
            steps {
                // Want to clone a repo from github
                //sh 'git clone https://github.com/devopswithcloud/spring3-mvc-maven-xml-hello-world.git'
                git credentialsId: 'devops_github_creds', url: 'https://github.com/devopswithcloud/spring3-mvc-maven-xml-hello-world.git'
            }
        }
        stage ('Build') {
            steps {
                sh "mvn --version"
                sh "mvn clean package -Dmaven.test.failure.ignore=true"
                // sh "mvn deploy" pom.xml distibutionMangement, and mvn settings.xml ,nexus
            }
            post {
                success {
                    archiveArtifacts artifacts: 'target/*.war', followSymlinks: false
                }
            }
        }
        stage ('Nexus') {
            steps {
                script {
                // I want to read the pom.xml using readMavenPom step
                //https://www.jenkins.io/doc/pipeline/steps/pipeline-utility-steps/#readmavenpom-read-a-maven-project-file
                pom = readMavenPom file: "pom.xml";
                fileNames = findFiles(glob: "target/*.${pom.packaging}");
                // Just for testing Purpose pring down varipus arguments
                echo "${fileNames[0].name} ${fileNames[0].path}"
                // I would like to get the path 
                artifactPath = fileNames[0].path;
                // I will verify if this artifacts exists
                artifactExists = fileExists artifactPath; // True | False
                echo "Does Artifact Exists: ${artifactExists}"
                echo "************** Printing If Artifact Exists **************"
                if(artifactExists) {
                    echo "************** Artifact is Available **************"
                    echo "File is: ${artifactPath}, Package is: ${pom.packaging}, Version is: ${pom.version}, GroupID: ${pom.groupId}"
                    nexusArtifactUploader(
                        nexusVersion: "$env.NEXUS_VERSION", //${NEXUS_VERSION}
                        protocol: "$env.NEXUS_PROTOCOL",
                        nexusUrl: "$env.NEXUS_URL",
                        groupId: "${pom.groupId}",
                        //version: "${BUILD_NUMBER}",
                        version: "${pom.version}",
                        credentialsId: 'nexus_creds',
                        repository: "$env.NEXUS_REPO",
                        artifacts: [
                            [
                            artifactId: pom.artifactId,
                            classifier: '',
                            file: artifactPath,
                            pom: pom.packaging
                            ]
                        ]
                    )
                }
                else {
                    error "************** Artifact ${artifactPath} is not avalable"
                }

                }
            }
        }
        stage ('Deploy to Tomcat') {
            steps {
                //sh "curl -v -u sivaacademy:password -T /var/lib/jenkins/workspace/allpipeline/target/spring3-mvc-maven-xml-hello-world-1.0-SNAPSHOT.war http://34.125.152.222:8080/manager/text/deploy?path=/spring-cloud"
                sh "curl -v -u ${TOMCAT_CREDS_USR}:${TOMCAT_CREDS_PSW} -T /var/lib/jenkins/workspace/allpipeline/target/spring3-mvc-maven-xml-hello-world-1.0-SNAPSHOT.war http://34.125.152.222:8080/manager/text/deploy?path=/spring-cloud"
            }
        }
    }
}

//curl -v -u admin:admin123 --upload-file target/*.war http://nexusURL:nexusPORT/repository/myRepository/com/my/group/myArtifact/1.0.0-RC1/siva-repo.jar