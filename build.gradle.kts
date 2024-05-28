// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.pluginAndroidApplication) apply false
    alias(libs.plugins.pluginJetbrainsKotlinAndroid) apply false
    alias(libs.plugins.pluginDaggerHilt) apply false
    alias(libs.plugins.pluginDevKsp) apply false
    alias(libs.plugins.pluginUndercouchDownload) apply false
    alias(libs.plugins.pluginNavigationSafeArgs) apply false
    alias(libs.plugins.pluginSerialization) apply false
    alias(libs.plugins.pluginFirebaseCrashlytics) apply false
    alias(libs.plugins.pluginGoogleServices) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.pluginSecretGradle) apply false
}