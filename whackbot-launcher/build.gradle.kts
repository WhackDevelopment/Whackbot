import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

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
    implementation(project(":whackbot-common", "default"))
}

tasks.named<ShadowJar>("shadowJar") {
    this.archiveClassifier.set(null as String?)
    this.archiveFileName.set("${rootProject.name}-${project.version}.${this.archiveExtension.getOrElse("jar")}")
    this.destinationDirectory.set(file("$projectDir/../out"))

    doFirst {
        manifest {
            attributes["Main-Class"] = "com.whackbot.launcher.Bootstrap"
            attributes["Premain-Class"] = "com.whackbot.Agent"
            attributes["Can-Retransform-Classes"] = true
            attributes["WhackBot-Website"] = "https://whackbot.com"
            attributes["WhackBot-Api"] = "https://api.whackbot.com"
        }
    }
}
