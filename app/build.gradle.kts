plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.arfipod.justanothersimpletile"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.arfipod.justanothersimpletile"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.play.services.wearable)
    implementation(libs.tiles)
    implementation(libs.tiles.material)
}