plugins {
    id("com.android.application")
    id("kotlin-android")
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
    implementation(platform(project(":platform")))
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    // Use the Kotlin JDK 8 standard library.
    implementation(kotlin("kotlin-stdlib-jdk8"))

    implementation("androidx.core:core-ktx")
    implementation("androidx.appcompat:appcompat")
    implementation("androidx.constraintlayout:constraintlayout")

    implementation("com.google.android.material:material")

    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    implementation("com.squareup.retrofit2:retrofit")
    implementation("com.squareup.retrofit2:converter-gson")
    implementation("com.squareup.retrofit2:adapter-rxjava2")
    implementation("com.google.code.gson:gson")

    implementation("io.reactivex.rxjava2:rxjava")
    implementation("io.reactivex.rxjava2:rxandroid")
    implementation("com.github.pwittchen:reactivenetwork-rx2")

    testImplementation("junit:junit")
    testImplementation("org.amshove.kluent:kluent")
    testImplementation("io.mockk:mockk")
    testImplementation("io.mockk:mockk-dsl")
    testImplementation("com.airbnb.okreplay:okreplay")
    testImplementation("com.airbnb.okreplay:junit")

    androidTestImplementation("androidx.test.ext:junit")
    androidTestImplementation("androidx.test.espresso:espresso-core")
}
