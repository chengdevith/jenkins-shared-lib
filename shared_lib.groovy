@library("shared_lib@main")

pipeline {
    agent any

    stages {
        stage('TelegramBot') {
            steps {
                script {
                    sendTelegramMessage()
                }
            }
        }
    }
}
