import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)

    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidTarget()
    jvm("desktop")


    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            xcf.add(this)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.androidx.lifecycle.runtimeCompose)
                implementation(libs.navigation.compose)
                implementation(libs.coil.compose)
                implementation(libs.material.icons.extended)

                implementation(libs.bundles.ktor)
                implementation(libs.com.russhwolf.multiplatform.settings.v130.x2)
                implementation(libs.multiplatform.settings.coroutines)
                implementation(libs.multiplatform.settings.no.arg)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(compose.preview)
                implementation(libs.androidx.activity.compose)

                implementation(libs.ktor.client.okhttp)
                implementation(libs.com.russhwolf.multiplatform.settings.v130.x2)
                implementation(libs.multiplatform.settings.coroutines)
                implementation(libs.multiplatform.settings.no.arg)
            }
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.com.russhwolf.multiplatform.settings.v130.x2)
            implementation(libs.multiplatform.settings.coroutines)
            implementation(libs.multiplatform.settings.no.arg)
        }

        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(libs.kotlinx.coroutinesSwing)

                implementation(libs.ktor.client.okhttp)
                implementation(libs.com.russhwolf.multiplatform.settings.v130.x2)
                implementation(libs.multiplatform.settings.coroutines)
                implementation(libs.multiplatform.settings.no.arg)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}



android {

    lint {
        disable += "NullSafeMutableLiveData"
        checkReleaseBuilds = false // Optional fallback
    }

    namespace = "org.example.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.example.project"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "org.example.project.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.example.project"
            packageVersion = "1.0.0"
        }
    }
}
