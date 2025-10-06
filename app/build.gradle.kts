import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    kotlin("kapt")
    kotlin("plugin.serialization")
}

fun getLocalProperty(key: String, file: String = "local.properties"): String {
    val properties = Properties()
    val localPropertiesFile = project.rootProject.file(file)
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use { properties.load(it) }
    } else {
        throw GradleException("Could not find local.properties file.")
    }
    return properties.getProperty(key) ?: throw GradleException("Could not find property '$key' in local.properties.")
}

android {
    namespace = "com.app.snaplearnai"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.app.snaplearnai"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            type = "String",
            name = "GEMINI_API_KEY",
            value = getLocalProperty("geminiApiKey")
        )
    }
    buildFeatures {
        buildConfig = true
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
    buildFeatures {
        compose = true
    }
    hilt {
        enableAggregatingTask = false
    }
    packaging {
        resources {
            excludes.add("META-INF/INDEX.LIST")
            excludes.add("META-INF/DEPENDENCIES")
            excludes.add("META-INF/LICENSE.txt")
            excludes.add("META-INF/NOTICE.txt")
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.common)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation(libs.androidx.material.icons.extended)

    // Retro fit
    implementation(libs.retrofit)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.compose)
    kapt(libs.hilt.compiler)

    // Room
    implementation(libs.androidx.room.runtime)
    kapt(libs.room.compiler) // For Kotlin
    implementation(libs.androidx.room.ktx)

    // ML
    implementation(libs.text.recognition)

    // Camera X
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)

    // Permission
    implementation(libs.accompanist.permissions)

    // AI
    // Official Google GenAI SDK for Kotlin
    implementation(libs.generativeai)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.gson)

    // Splash
    implementation(libs.androidx.core.splashscreen)
}