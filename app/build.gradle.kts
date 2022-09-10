plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = ConfigData.compileSdkVersion
    flavorDimensions.add("version")

    defaultConfig {
        applicationId = "com.lefarmico.moviesfinder"
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.compileSdkVersion

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    productFlavors {
        create("free") {
            dimension = "version"
            applicationIdSuffix = ".basic"
            versionNameSuffix = "-basic"
        }
        create("paid") {
            dimension = "version"
            applicationIdSuffix = ".paid"
            versionNameSuffix = "-paid"
        }
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
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

    buildFeatures {
        viewBinding = true
    }

    sourceSets {
        getByName("free") {
            java {
                srcDirs("src\\free\\java")
            }
        }
        getByName("paid") {
            java {
                srcDirs("src\\paid\\java")
            }
        }
    }
}

dependencies {

    // Android
    implementation(Dependency.constraintLayout)
    implementation(Dependency.cardView)
    implementation(Dependency.recyclerView)
    implementation(Dependency.fragment)
    implementation(Dependency.material2)

    // Tests
    testImplementation(Dependency.junit)
    testImplementation(Dependency.jetpackPagingTest)
    androidTestImplementation(Dependency.junitExt)
    androidTestImplementation(Dependency.espresso)

    // Picasso
    implementation(Dependency.picasso)

    // Hilt
    implementation(Dependency.daggerHilt)
    kapt(Dependency.hiltCompiler)
    kapt(Dependency.hiltAndroidCompiler)

    // Navigation Components
    implementation(Dependency.navigationFragment)
    implementation(Dependency.navigationUI)
    implementation(Dependency.navigationDynamicFeatures)

    // GSON
    implementation(Dependency.gson)

    // OkHttp
    implementation(Dependency.okHttp)
    implementation(Dependency.okHttpInterceptor)

    // Retrofit
    implementation(Dependency.retrofit)
    implementation(Dependency.retrofitGsonConverter)
    implementation(Dependency.retrofitRXJava)

    // Room
    implementation(Dependency.roomRuntime)
    kapt(Dependency.roomCompiler)
    implementation(Dependency.roomKtx)
    implementation(Dependency.roomRx)

    // Coroutines
    implementation(Dependency.coroutinesCore)
    implementation(Dependency.coroutinesAndroid)

    // Jetpack
    implementation(Dependency.jetpackPaging)

    // CircleImageView
    implementation("de.hdodenhof:circleimageview:3.1.0")
}
