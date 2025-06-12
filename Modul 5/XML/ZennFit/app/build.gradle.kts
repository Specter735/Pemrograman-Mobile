plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.kapt") // Penting untuk Room dan Glide annotation processing
    // Karena Anda menggunakan converter-gson, plugin kotlinx.serialization tidak diperlukan.
}

android {
    namespace = "com.example.zennfit"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.zennfit"
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
        // Direkomendasikan untuk menggunakan Java 1.8 untuk pengembangan Android modern
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8" // Pastikan sesuai dengan sourceCompatibility
    }

    buildFeatures {
        viewBinding = true
    }

}

dependencies {
    // Dependensi yang sudah ada atau diperbarui
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material) // Gunakan versi dari libs.versions.toml jika ada
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Fragment KTX (sudah ada)
    implementation ("androidx.fragment:fragment-ktx:1.6.2")

    // Lifecycle (ViewModel KTX sudah ada, tambahkan LiveData KTX)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1") // Tambahkan ini

    // Retrofit (untuk networking)
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0") // Konverter Gson (sesuai pilihan Anda)

    // OkHttp (HTTP client yang digunakan Retrofit)
    implementation ("com.squareup.okhttp3:okhttp:4.12.0") // Core OkHttp
    implementation ("com.squareup.okhttp3:logging-interceptor:4.12.0") // Penting untuk debugging network

    // Kotlin Coroutines (versi terbaru dan tambahkan core)
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3") // Tambahkan ini
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    // Baris kotlinx-coroutines-android:1.7.1 yang duplikat sudah saya hapus.

    // Glide (untuk image loading)
    implementation("com.github.bumptech.glide:glide:4.16.0")
    kapt("com.github.bumptech.glide:compiler:4.16.0")
    // Baris libs.glide dan annotationProcessor(libs.glide.compiler) yang duplikat/redundant sudah saya hapus.

    // Gson (sudah ada)
    implementation("com.google.code.gson:gson:2.10.1")

    // Room Database (persistence) - BARU untuk Modul 5
    implementation ("androidx.room:room-runtime:2.6.1")
    kapt ("androidx.room:room-compiler:2.6.1")
    implementation ("androidx.room:room-ktx:2.6.1") // Untuk ekstensi Kotlin dan Coroutines

    // ExoPlayer
    implementation ("com.google.android.exoplayer:exoplayer:2.19.1") // Versi stabil terbaru saat ini
    implementation ("com.google.android.exoplayer:exoplayer-ui:2.19.1") // Untuk PlayerView UI


    // Testing dependencies (sudah ada)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}