import java.util.Date

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.allsoft.qrscanapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.allsoft.qrscanapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        multiDexEnabled = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField ("String", "BASE_URL", "\"https://epravesh.cgepass.com\"")
        }

        debug {
            buildConfigField ("String", "BASE_URL", "\"https://epravesh.cgepass.com\"")
            buildConfigField ("String", "BUILD_TYPE", "\"debug\"")
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

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/DEPENDENCIES"
            excludes += "META-INF/LICENSE.txt"
            excludes += "META-INF/NOTICE"
            excludes += "META-INF/LICENSE"
        }
    }

    namespace = "com.allsoft.qrscanapp"
}

dependencies {

    implementation ("androidx.core:core-ktx:1.12.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    implementation ("com.google.android.material:material:1.11.0")
    implementation ("androidx.multidex:multidex:2.0.1")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("androidx.fragment:fragment-ktx:1.6.2")
    implementation ("androidx.legacy:legacy-support-v13:1.0.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.recyclerview:recyclerview:1.3.2")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

    implementation("io.github.g00fy2.quickie:quickie-bundled:1.9.0")

    // Koin Dependencies
    implementation ("org.koin:koin-android:2.1.5")
    implementation ("org.koin:koin-android-scope:2.1.5")
    implementation ("org.koin:koin-android-viewmodel:2.1.5")

    //REST APIs
    // Networking
    implementation ("com.squareup.retrofit2:converter-moshi:2.6.2")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.7.1")
    implementation ("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    implementation ("com.squareup.retrofit2:adapter-rxjava:2.6.2")
    implementation ("com.google.code.gson:gson:2.10.1")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation ("com.squareup.okhttp3:okhttp:4.12.0")
    implementation ("com.google.android.play:app-update-ktx:2.1.0")

    //OTP view
    implementation ("com.github.appsfeature:otp-view:1.0")

    //Lottie Animation
    implementation ("com.airbnb.android:lottie:6.4.0")
}