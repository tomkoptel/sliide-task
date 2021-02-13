plugins {
    id("java-platform")
}

dependencies {
    constraints {
        api("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
        api("androidx.core:core-ktx:1.3.2")
        api("androidx.appcompat:appcompat:1.2.0")
        api("androidx.constraintlayout:constraintlayout:2.0.4")

        api("com.google.android.material:material:1.3.0")

        val okhttpVersion = "4.4.1"
        api("com.squareup.okhttp3:okhttp:$okhttpVersion")
        api("com.squareup.okhttp3:logging-interceptor:$okhttpVersion")

        val retrofitVersion = "2.9.0"
        api("com.squareup.retrofit2:retrofit:$retrofitVersion")
        api("com.squareup.retrofit2:converter-gson:$retrofitVersion")
        api("com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion")
        api("com.google.code.gson:gson:2.8.6")

        api("junit:junit:4.13.1")

        val mockkVersion = "1.9.3"
        api("io.mockk:mockk:$mockkVersion")
        api("io.mockk:mockk-dsl:$mockkVersion")

        api("org.amshove.kluent:kluent:1.61")

        val okreplayVersion = "1.6.0"
        api("com.airbnb.okreplay:okreplay:$okreplayVersion")
        api("com.airbnb.okreplay:junit:$okreplayVersion")

        api("androidx.test.ext:junit:1.1.2")
        api("androidx.test.espresso:espresso-core:3.3.0")

        api("io.reactivex.rxjava2:rxjava:2.2.21")
        api("io.reactivex.rxjava2:rxandroid:2.1.1")
        api("com.github.pwittchen:reactivenetwork-rx2:3.0.8")
    }
}
