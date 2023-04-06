fun commitHash(): String = try {
    val runtime = Runtime.getRuntime()
    val process = runtime.exec("git rev-parse --short HEAD")
    val out = process.inputStream
    out.bufferedReader().readText().trim()
} catch (ignored: Exception) {
    "unknown"
}

val commit: String? = commitHash()

dependencies {
    compileOnly(libs.annotations)
    annotationProcessor(libs.annotations)
    compileOnly(libs.gson)
    compileOnly(libs.guava)
    compileOnly(libs.jda)
    compileOnly(libs.webhooks)
    compileOnly(libs.jedis)
    compileOnly(libs.mongo)
    compileOnly(libs.jline)
}
