plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp") version "1.9.0-1.0.13"
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
    id ("com.google.dagger.hilt.android") version "2.44" apply false
}

android {
    namespace = "com.example.diyapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.diyapp"
        minSdk = 26
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Nav Component in libs.versions.toml
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    //Room in libs.versions.toml
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    //Retrofit in libs.versions.toml
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    //Coroutines in libs.versions.toml
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    //ViewModel in libs.versions.toml
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    //LiveData in libs.versions.toml
    implementation(libs.androidx.lifecycle.livedata.ktx)

    //Fragment in libs.versions.toml
    implementation(libs.androidx.fragment.ktx)

    //Activity in libs.versions.toml
    implementation(libs.androidx.activity.ktx)

    // Dependencias de Hilt
    implementation ("com.google.dagger:hilt-android:2.44")
    ksp("com.google.dagger:hilt-android-compiler:2.44")

    implementation (libs.logging.interceptor)
}