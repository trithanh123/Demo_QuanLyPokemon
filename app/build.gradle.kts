plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
}

android {
    namespace = "vn.edu.stu.PhamTriThanh_DH52201466"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "vn.edu.stu.demoquanlypokemon"
        minSdk = 28
        targetSdk = 36
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Thư viện giao diện cơ bản (Bắt buộc)
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Thư viện lõi (Sửa lỗi CoreComponentFactory)
    implementation("androidx.core:core-ktx:1.12.0")
    implementation(libs.activity)
    implementation(libs.play.services.maps)
    // Các thư viện test (Giữ nguyên mặc định)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("org.osmdroid:osmdroid-android:6.1.16")
}