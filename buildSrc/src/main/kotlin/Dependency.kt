object Dependency {

    // Dagger 2
    const val dagger = "com.google.dagger:dagger:${Version.dagger}"
    const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${Version.dagger}"
    const val daggerAndroidProcessor = "com.google.dagger:dagger-android-processor:${Version.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Version.dagger}"

    // Picasso
    const val picasso = "com.squareup.picasso:picasso:${Version.picasso}"

    // Navigation Components
    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Version.navFragment}"
    const val navigationUI = "androidx.navigation:navigation-ui-ktx:${Version.navFragment}"
    const val navigationDynamicFeatures = "androidx.navigation:navigation-dynamic-features-fragment:${Version.navFragment}"

    // GSON
    const val gson = "com.google.code.gson:gson:${Version.gson}"

    // OkHttp
    const val okHttp = "com.squareup.okhttp3:okhttp:${Version.okHttp}"
    const val okHttpInterceptor = "com.squareup.okhttp3:logging-interceptor:${Version.okHttp}"

    // Retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Version.retrofit}"
    const val retrofitRXJava = "com.squareup.retrofit2:adapter-rxjava2:${Version.retrofit}"

    // Room
    const val roomRuntime = "androidx.room:room-runtime:${Version.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Version.room}"
    const val roomKtx = "androidx.room:room-ktx:${Version.room}"
    const val roomRx = "androidx.room:room-rxjava3:${Version.room}"

    // RXJava
    const val rxjava3Android = "io.reactivex.rxjava3:rxandroid:${Version.rxJava}"
    const val rxjava3 = "io.reactivex.rxjava3:rxjava:${Version.rxJava}"
    const val rxjavaKotlin = "io.reactivex.rxjava3:rxkotlin:${Version.rxJava}"
    const val rxjavaRetrofitAdapter = "com.github.akarnokd:rxjava3-retrofit-adapter:${Version.rxJava}"

    // Android
    const val androidCore = "androidx.core:core-ktx:1.8.0"
    const val appCompat = "androidx.appcompat:appcompat:1.5.0"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"
    const val cardView = "androidx.cardview:cardview:1.0.0"
    const val recyclerView = "androidx.recyclerview:recyclerview:1.2.1"
    const val fragment = "androidx.fragment:fragment-ktx:1.5.2"
    const val material2 = "com.google.android.material:material:1.6.1"

    // Test
    const val junit = "junit:junit:4.13.2"
    const val junitExt = "androidx.test.ext:junit:1.1.3"
    const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
}