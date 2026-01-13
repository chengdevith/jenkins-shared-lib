def call(Map config = [:]) {
    def image = config.image ?: 'app:latest'
    def env = config.env ?: 'dev'

    def dockerfilePath = detectProject(env)

    echo "Detected Dockerfile: ${dockerfilePath}"

    writeFile file: 'Dockerfile', text: libraryResource(dockerfilePath)

    sh """
        docker build -t ${image} .
    """
}

/* ---------------- helpers ---------------- */

def detectProject(String env) {
    if (fileExists('package.json') && fileExists('next.config.js')) {
        return "nextjs/${env}.Dockerfile"
    }

    if (fileExists('pom.xml') || fileExists('build.gradle')) {
        return "spring/${env}.Dockerfile"
    }

    error "❌ Unsupported project type"
}
