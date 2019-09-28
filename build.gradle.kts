val arrowVersion by extra { "0.10.0" }

plugins {
    kotlin("jvm") version "1.3.30"
}

repositories {
    jcenter()
    mavenCentral()
    maven(url = "https://dl.bintray.com/arrow-kt/arrow-kt/")
}

dependencies {
    implementation(kotlin("stdlib"))

    compile("io.arrow-kt:arrow-core:$arrowVersion")
    compile("io.arrow-kt:arrow-syntax:$arrowVersion")
    compile("io.arrow-kt:arrow-optics:$arrowVersion")

    testCompile("io.kotlintest:kotlintest-runner-junit5:3.3.2")
    testCompile("junit:junit:4.12")
}
