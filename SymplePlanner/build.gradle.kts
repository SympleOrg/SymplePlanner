plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "top.symple.sympleplanner"
    compileSdk = 30

    defaultConfig {
        minSdk = 24
        //noinspection ExpiredTargetSdkVersion
        targetSdk = 28
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

repositories {
    mavenCentral()
}

dependencies {
    api("org.firstinspires.ftc:RobotCore:10.2.0")
    api("org.firstinspires.ftc:RobotServer:10.2.0")
    api("org.firstinspires.ftc:Hardware:10.2.0")
    api("org.firstinspires.ftc:FtcCommon:10.2.0")

    implementation("androidx.appcompat:appcompat:1.2.0")
}