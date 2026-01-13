def call(String projectName, String projectKey, String projectVersion){
     withSonarQubeEnv('sonarqube') {
        script {
            def scannerHome = tool 'sonarqube'
                sh """
                ${scannerHome}/bin/sonar-scanner \
                -Dsonar.projectKey=${projectKey} \
                -Dsonar.projectName=${projectName} \
                -Dsonar.projectVersion=${projectVersion} \
                -Dsonar.exclusions="pipes/**"
                """
        }
    }
}