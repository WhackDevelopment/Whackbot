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
    implementation(libs.annotations)
    annotationProcessor(libs.annotations)
    implementation(libs.gson)
    implementation(libs.guava)
    implementation(libs.jda)
    implementation(libs.webhooks)
    implementation(libs.jedis)
    implementation(libs.mongo)
    implementation(libs.jline)
}
