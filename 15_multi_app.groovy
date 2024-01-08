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
                git branch: 'main', url: 'https://github.com/spring-projects/spring-petclinic.git'
            }
        }
        stage ('Build') {
            tools {
                maven 'MyMaven'
                jdk 'JDK17'
            }
            steps {
                sh "mvn --version"
                sh "mvn clean package -Dmaven.test.failure.ignore=true"
            }
            post {
                success {
                    archiveArtifacts artifacts: 'target/*.war', followSymlinks: false
                }
            }
        }
    }
}