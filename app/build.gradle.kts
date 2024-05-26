import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.dagger.hilt.android)
    id("kotlin-kapt")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.akumakeito.vyyer"
    compileSdk = 34


    defaultConfig {
        applicationId = "com.akumakeito.vyyer"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        manifestPlaceholders["auth0Domain"] = "vyyer.us"
        manifestPlaceholders["auth0Scheme"] = "https"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    val keystoreFile = project.rootProject.file("secret.properties")
    val properties = Properties()
    properties.load(keystoreFile.inputStream())

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL_AUTH", "\"https://vyyer.us.auth0.com\"")
            buildConfigField("String", "BASE_URL_API", "\"https://unifiedapi.dev.vyyer.id\"")
            buildConfigField("String", "CLIENT_ID", "\"${properties.getProperty("CLIENT_ID")}\"")
            buildConfigField("String", "CLIENT_SECRET", "\"${properties.getProperty("CLIENT_SECRET")}\"")

        }
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
        buildConfig = true
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

    implementation(libs.androidx.swiperefreshlayout)


    // navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.logging.interceptor)

    // lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    kapt(libs.androidx.lifecycle.compiler)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    //coroutines
    implementation(libs.kotlinx.coroutines.android)

    //authO
    implementation(libs.jwtdecode)
    implementation(libs.auth0)

    //prefs
    implementation(libs.androidx.security.crypto)

    //paging
    implementation(libs.androidx.paging.runtime)

    //glide
    implementation(libs.glide)

    //room
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    kapt(libs.androidx.room.compiler)

    implementation(libs.androidx.paging.runtime.ktx)


}