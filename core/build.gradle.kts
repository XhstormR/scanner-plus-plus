plugins {
    `java-library`
    kotlin("jvm")
    kotlin("plugin.serialization")
    publish
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    compileOnly("net.portswigger.burp.extender:burp-extender-api:+")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-hocon:+")

    implementation("org.springframework:spring-expression:5.3.15")

    testImplementation("org.junit.jupiter:junit-jupiter:+")
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }

    withType<Jar> {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        from(configurations.runtimeClasspath.get().map { zipTree(it) })
        exclude("**/*.kotlin_module")
        exclude("**/*.kotlin_metadata")
        exclude("**/*.kotlin_builtins")
    }
}