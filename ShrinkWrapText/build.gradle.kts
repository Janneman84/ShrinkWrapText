plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    id("maven-publish")
}

android {
    namespace = "com.janneman84.shrinkwraptest"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    publishing {
        singleVariant("release") {
            withSourcesJar() // Optional, publish source code
        }
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material3)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "Janneman84"
                artifactId = "Compose"
                version = "0.1.0"
            }
        }
    }
}
