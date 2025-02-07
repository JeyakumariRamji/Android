plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android.gradle)
    kotlin("kapt")
}

android {
    namespace = "com.example.cosmicexplorerapp_jeyakumari_ramji"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.cosmicexplorerapp_jeyakumari_ramji"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.android)
    implementation(libs.coroutines)
    implementation(libs.coil.compose)

    // Add Glide dependencies here
    implementation(libs.glide) // Glide library

    // Hilt testing dependencies
    androidTestImplementation("com.google.dagger:hilt-android-testing:${libs.versions.hiltAndroidGradlePlugin.get()}")
    kaptAndroidTest("com.google.dagger:hilt-compiler:${libs.versions.hiltAndroidGradlePlugin.get()}")

    // Add the following dependencies for Compose icons, navigation, and viewmodel support:
    implementation(libs.androidx.material.icons.extended) // Material Icons for Compose
    implementation(libs.androidx.material) // Material Components like IconButton
    implementation(libs.ui.tooling.preview) // UI tools (optional but helpful)
    implementation(libs.androidx.lifecycle.viewmodel.compose) // ViewModel integration with Compose

}