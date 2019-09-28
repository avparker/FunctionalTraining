plugins {
    kotlin("jvm") version "1.2.71"
}

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))

    testCompile("io.kotlintest:kotlintest-runner-junit5:3.3.2")
}
