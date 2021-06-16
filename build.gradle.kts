
plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.10"
    id("org.jetbrains.kotlin.kapt") version "1.4.10"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.4.10"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("io.micronaut.application") version "1.2.0"
}

version = "1.0.0-SNAPSHOT"
group = "test"

val kotlinVersion=project.properties.get("kotlinVersion")
repositories {
    mavenCentral()
    jcenter()
}

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.peraton.ymca.referral.partners.*")
    }
}

dependencies {
    implementation("io.micronaut:micronaut-validation")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut:micronaut-runtime")
    implementation("javax.annotation:javax.annotation-api")
    implementation("io.micronaut:micronaut-http-client")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")

        // Data
    annotationProcessor("io.micronaut.data:micronaut-data-processor") // <1>
    implementation("io.micronaut.data:micronaut-data-jdbc") // <2>
    runtimeOnly("com.h2database:h2") // <3>
    runtimeOnly("io.micronaut.sql:micronaut-jdbc-hikari") // <4>

}


application {
    mainClass.set("com.peraton.ymca.referral.partners.Application")
}

java {
    sourceCompatibility = JavaVersion.toVersion("11")
}
tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "1!"
        }
    }


}



//plugins {
//    id("org.jetbrains.kotlin.jvm") version "1.4.10"
//    id("org.jetbrains.kotlin.kapt") version "1.4.10"
//    id("org.jetbrains.kotlin.plugin.allopen") version "1.4.10"
//    id("com.github.johnrengelman.shadow") version "6.1.0"
//    id("io.micronaut.application") version "1.2.0"
//
//}
//
//version = "1.0.0-SNAPSHOT"
//group = "com.peraton.ymca.referral.partners"
//
//val kotlinVersion=project.properties.get("kotlinVersion")
//repositories {
//    mavenCentral()
//    jcenter()
//}
//
//micronaut {
//    runtime("netty")
//    testRuntime("junit5")
//    processing {
//        incremental(true)
//        annotations("com.peraton.ymca.referral.partners.*")
//    }
//}
//
//dependencies {
//    kapt("io.micronaut.security:micronaut-security-annotations")
//    implementation("io.micronaut:micronaut-validation")
//    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
//    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
//    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
//    implementation("io.micronaut:micronaut-runtime")
//    implementation("javax.annotation:javax.annotation-api")
//    implementation("io.micronaut:micronaut-http-client")
////    implementation("io.micronaut.security:micronaut-security-oauth2")
//    runtimeOnly("ch.qos.logback:logback-classic")
//    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
//    // Data
//    annotationProcessor("io.micronaut.data:micronaut-data-processor") // <1>
//    implementation("io.micronaut.data:micronaut-data-jdbc") // <2>
//    runtimeOnly("com.h2database:h2") // <3>
//    runtimeOnly("io.micronaut.sql:micronaut-jdbc-hikari") // <4>
//
//}
//
//
//application {
//    mainClass.set("com.peraton.ymca.referral.partners.Application")
//}
//
//java {
//    sourceCompatibility = JavaVersion.toVersion("11")
//}
//
//tasks {
//    compileKotlin {
//        kotlinOptions {
//            jvmTarget = "11"
//        }
//    }
//    compileTestKotlin {
//        kotlinOptions {
//            jvmTarget = "11"
//        }
//    }
//
//
//}
//
//val compileKotlin: KotlinCompile by tasks
//compileKotlin.kotlinOptions {
//    jvmTarget = "1.8"
//}
//val compileTestKotlin: KotlinCompile by tasks
//compileTestKotlin.kotlinOptions {
//    jvmTarget = "1.8"
//}