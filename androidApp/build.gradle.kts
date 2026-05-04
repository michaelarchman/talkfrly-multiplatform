plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeCompiler)
//    id("com.google.gms.google-services")
}

android {
    namespace = "com.talkfrly.multiplatform.androidApp"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 29
        versionCode = 9
        versionName = "0.0.2"
//        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation(projects.composeApp)
    implementation(libs.koin.android)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
    implementation(libs.androidx.activity.compose)
    debugImplementation(platform(libs.androidx.compose.bom))
}