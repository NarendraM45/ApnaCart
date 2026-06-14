import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.kotlin.compose)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    sourceSets {
        androidMain.dependencies {
            implementation(project(":shared"))
            
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
            implementation(libs.sqldelight.android)
            
            // Android specific
            implementation(libs.datastore.preferences)
            implementation(libs.security.crypto)
            implementation(libs.biometric)
            implementation(libs.splashscreen)
            implementation(libs.paging.compose)
            implementation(libs.lottie)
            
            // Media3 ExoPlayer for Splash Animation
            implementation(libs.media3.exoplayer)
            implementation(libs.media3.ui)
            implementation(libs.media3.common)
        }
    }
}

android {
    namespace = "com.apnacart.android"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.apnacart.android"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
    
    buildFeatures {
        compose = true
    }
    
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    
    sourceSets {
        getByName("main") {
            manifest.srcFile("src/main/AndroidManifest.xml")
            res.srcDirs("src/main/res")
            java.srcDirs("src/main/kotlin")
        }
    }
    
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
