def call(){
    withCredentials([
        string(credentialsId: 'TELEGRAM_BOT_TOKEN', variable: 'BOT_TOKEN'),
        string(credentialsId: 'TELEGRAM_CHAT_ID', variable: 'CHAT_ID')
        ]) {
            sh """
                curl -s -X POST https://api.telegram.org/bot${BOT_TOKEN}/sendMessage \
                -d chat_id=${CHAT_ID} \
                -d parse_mode=Markdown \
                -d text="✅ *Job SUCCESS*
    
                *Project:* JobFinder Frontend
                *Job:* ${JOB_NAME}
                *Build:* #${BUILD_NUMBER}
                *SonarQube:* Quality Gate PASSED 🎯
                "
                """
    }
}