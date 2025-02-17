plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")

}

android {
    namespace = "com.example.kotlincoroutine"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.kotlincoroutine"
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //ktx
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7") //collectAsStateWithLifeCycle
    implementation("androidx.compose.runtime:runtime-livedata:1.7.7") //observeAsState


    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")

    //hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    //retrofit + Gson
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.google.code.gson:gson:2.10")

    //casting
    implementation ("androidx.mediarouter:mediarouter:1.7.0")
    implementation ("com.google.android.gms:play-services-cast-framework:22.0.0")


    //media 3
    implementation ("androidx.media3:media3-ui:1.5.1")
    implementation ("androidx.media3:media3-session:1.5.1")
    implementation("androidx.media3:media3-datasource-okhttp:1.5.1")
    implementation ("androidx.media3:media3-common:1.5.1")
    implementation ("androidx.media3:media3-cast:1.5.1")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    //async
    implementation("io.coil-kt.coil3:coil-compose:3.1.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.1.0")

    //navigation
    implementation("androidx.navigation:navigation-compose:2.8.7")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
}

kapt {
    correctErrorTypes = true
}