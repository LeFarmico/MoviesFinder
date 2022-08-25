plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.compileSdkVersion

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-debug-rules.pro"
            )
            testProguardFile("proguard-test-rules.pro")
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {

    // Tests
    testImplementation(Dependency.junit)

    // Dagger 2
    implementation(Dependency.dagger)
    kapt(Dependency.daggerCompiler)

    // Retrofit
    implementation(Dependency.retrofit)
    implementation(Dependency.retrofitGsonConverter)
    implementation(Dependency.retrofitRXJava)

    // RxJava
    implementation(Dependency.rxjava3)

    // OkHttp
    implementation(Dependency.okHttp)
    implementation(Dependency.okHttpInterceptor)

    implementation("com.github.akarnokd:rxjava3-retrofit-adapter:3.0.0")
}
