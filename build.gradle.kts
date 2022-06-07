import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.compose") version "1.1.1"
}

group = "com.bqliang.medhub"
version = "1.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation("ch.qos.logback:logback-classic:1.2.11")

    // EasyExcel
    implementation("com.alibaba:easyexcel:3.0.5")
    // Junit 5
    testImplementation(kotlin("test"))
    // Compose Desktop
    implementation(compose.desktop.currentOs)
    // MySQL驱动
    implementation("mysql:mysql-connector-java:8.0.28")
    // 数据库映射
    implementation("org.ktorm:ktorm-core:3.4.1")
    // 发送邮件
    implementation ("com.mailslurp:mailslurp-client-kotlin:15.7.0")
    // 连接池
    implementation("org.apache.commons:commons-dbcp2:2.9.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

compose.desktop {
    application {
        mainClass = "MainKt" // 程序入口
        //args += listOf("") // arguments for the application's JVM
        javaHome = System.getenv("JDK_17") // jpackage request JDK 15+

        nativeDistributions {
            // 包含的模块
            includeAllModules = true

            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "MedHub"
            packageVersion = "1.6.0"
            description = "Medicine sales manager App, fully based on Compose."
            copyright = "© 2022 bqliang. All rights reserved."
            vendor = "bqliang"

            val iconsRoot = project.file("./src/main/resources") //图标目录

            windows {
                iconFile.set(iconsRoot.resolve("launcher_icon.ico")) //程序图标
                menuGroup = "" // adds the application to the specified Start menu group
            }
        }
    }
}