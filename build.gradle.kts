// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
//    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.dagger.hilt) apply false
    id("com.google.devtools.ksp") version "1.9.24-1.0.20" apply false
    alias(libs.plugins.android.library) apply false
}
//// Top-level build file where you can add configuration options common to all sub-projects/modules.
//buildscript {
//
//    repositories {
//        google()
//        mavenCentral()
//    }
//    dependencies {
//        classpath(libs.gradle)
//        classpath(libs.kotlin.gradle.plugin)
//        // NOTE: Do not place your application dependencies here; they belong
//        // in the individual module build.gradle.kts files
//    }
//}