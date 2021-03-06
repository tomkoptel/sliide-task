plugins {
    id("com.android.application")
    id("kotlin-android")

    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.2")

    defaultConfig {
        applicationId("com.olderwold.sliide")
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")

        val gorestApiAccessToken: String? by project
        buildConfigField("String", "GOREST_API_TOKEN", "\"$gorestApiAccessToken\"")
    }
    sourceSets {
        getByName("main").java.srcDirs(
            "src/main/kotlin",
            "src/main/kotlinX"
        )
        getByName("androidTest").java.srcDirs(
            "src/androidTest/kotlin"
        )
        getByName("test").java.srcDirs(
            "src/test/kotlin",
            "src/test/kotlinX"
        )
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        // Flag to enable support for the new language APIs
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
    }
    lintOptions {
        lintConfig = file("lint.xml")
        textReport = true
        textOutput("stdout")
    }
}

dependencies {
    // Align project versions
    platform(project(":platform")).let {
        kapt(it)
        implementation(it)
        coreLibraryDesugaring(it)
        testImplementation(it)
        androidTestImplementation(it)
    }
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    // Use the Kotlin JDK 8 standard library.
    implementation(kotlin("stdlib-jdk8"))

    // Add support for Java 8 Time API
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs")

    // region Dagger Hilt
    implementation("androidx.hilt:hilt-lifecycle-viewmodel")
    kapt("androidx.hilt:hilt-compiler")
    implementation("com.google.dagger:hilt-android")
    kapt("com.google.dagger:hilt-compiler")
    testImplementation("com.google.dagger:hilt-android-testing")
    // endregion

    // region Jetpack & UI specific
    implementation("androidx.core:core-ktx")
    implementation("androidx.activity:activity-ktx")
    implementation("androidx.fragment:fragment-ktx")
    implementation("androidx.appcompat:appcompat")
    implementation("androidx.constraintlayout:constraintlayout")

    implementation("com.google.android.material:material")
    // endregion

    // region Network
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    implementation("com.squareup.retrofit2:retrofit")
    implementation("com.squareup.retrofit2:converter-gson")
    implementation("com.squareup.retrofit2:adapter-rxjava2")
    implementation("com.google.code.gson:gson")
    implementation("com.squareup.okhttp3:mockwebserver")

    implementation("io.reactivex.rxjava2:rxjava")
    implementation("io.reactivex.rxjava2:rxandroid")
    implementation("io.reactivex.rxjava2:rxkotlin")
    implementation("com.github.pwittchen:reactivenetwork-rx2")
    // endregion

    // region Unit Tests
    testImplementation("junit:junit")
    testImplementation("org.amshove.kluent:kluent")
    testImplementation("io.mockk:mockk")
    testImplementation("io.mockk:mockk-dsl")
    testImplementation("com.airbnb.okreplay:okreplay")
    testImplementation("com.airbnb.okreplay:junit")
    testImplementation("androidx.arch.core:core-testing")
    // endregion

    // region Android Instrumentation Tests
    androidTestImplementation("androidx.test.ext:junit")
    androidTestImplementation("androidx.test.espresso:espresso-core")
    // endregion
}
