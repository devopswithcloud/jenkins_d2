// Implementing CICD for Spring3 project
// This includes, build, nexus, tomcat

pipeline {
    agent any 
    tools {
        maven 'MyMaven'
    }
    environment {
        TOMCAT_CREDS = credentials('tomcat_creds') // this is having my username and password
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