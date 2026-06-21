pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'Java11'
    }

    environment {
        ALLURE_RESULTS = 'target/allure-results'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Clean Workspace') {
            steps {
                sh 'mvn clean'
            }
        }

        stage('Run Automated Tests') {
            steps {
                // Execute tests via Maven. Failures here should not fail the pipeline immediately
                // so that reports can still be generated.
                catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                    sh 'mvn test -Dbrowser=chrome -Denv=qa'
                }
            }
        }
    }

    post {
        always {
            // Generate and attach Allure Report
            allure includeProperties: false, jdk: '', results: [[path: "${ALLURE_RESULTS}"]]
            
            // Archive TestNG raw reports
            archiveArtifacts artifacts: 'target/surefire-reports/**/*', allowEmptyArchive: true
        }
        success {
            echo 'All tests passed successfully!'
        }
        unstable {
            echo 'Test execution completed, but some tests failed.'
        }
        failure {
            echo 'Pipeline failed during execution.'
        }
    }
}
