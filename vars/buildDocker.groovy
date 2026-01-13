def call(Map config = [:]) {

    def image = config.image ?: 'app:latest'
    def env = config.env ?: 'dev'
    def isNext = ${params.isNext}
    def isSpring = ${params.isSpring}

    def dockerfilePath = detectProject(env, isNext, isSpring)

    echo "Detected Dockerfile: ${dockerfilePath}"

    writeFile(
        file: 'Dockerfile',
        text: libraryResource(dockerfilePath)
    )

    sh "docker build -t ${image} ."
}

/* ---------------- helpers ---------------- */

def detectProject(String env, boolean isNext, boolean isSpring) {

    if (isNext) {
        return "nextjs/${env}.dockerfile"
    }

    if (isSpring) {
        return "spring/${env}.dockerfile"
    }

    error "❌ Unsupported project type (NEXT or SPRING must be true)"
}
