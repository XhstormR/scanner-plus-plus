import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    idea
    val kotlinVersion = "1.7.0"
    kotlin("jvm") version kotlinVersion apply false
    kotlin("plugin.serialization") version kotlinVersion apply false
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
        }
    }
}

allprojects {

    group = "io.github.xhstormr.scanner-plus-plus"
    version = "1.0-SNAPSHOT"

    repositories {
        maven("https://mirrors.huaweicloud.com/repository/maven")
        maven("https://maven.aliyun.com/repository/public")
    }

    apply {
        plugin("org.jlleitschuh.gradle.ktlint")
    }

    tasks {
        withType<Test> {
            useJUnitPlatform()
            jvmArgs("--add-opens=java.base/java.util.regex=ALL-UNNAMED")
        }

        withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = "11"
                freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=all")
            }
        }

        withType<JavaCompile> {
            with(options) {
                encoding = Charsets.UTF_8.name()
                isFork = true
                isIncremental = true
                release.set(11)
            }
        }
    }
}

tasks {
    register<Delete>("clean") {
        delete(rootProject.buildDir)
    }

    withType<Wrapper> {
        gradleVersion = "7.4"
        distributionType = Wrapper.DistributionType.ALL
    }
}
