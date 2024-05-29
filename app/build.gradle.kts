import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension
import java.io.FileInputStream
import java.lang.Thread.sleep
import java.net.URL
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Properties

plugins {
    kotlin("kapt")
    alias(libs.plugins.pluginAndroidApplication)
    alias(libs.plugins.pluginJetbrainsKotlinAndroid)
    alias(libs.plugins.pluginDaggerHilt)
    alias(libs.plugins.pluginDevKsp)
    alias(libs.plugins.pluginUndercouchDownload)
    alias(libs.plugins.pluginSerialization)
    alias(libs.plugins.pluginFirebaseCrashlytics)
    alias(libs.plugins.pluginGoogleServices)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.pluginNavigationSafeArgs)
    alias(libs.plugins.pluginSecretGradle)
}

val versionMajor = 0
val versionSprint = 13
val versionSprintRevision = 7

android {
    namespace = "com.vasscompany.vassuniversitybaseproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.vasscompany.vassuniversitybaseproject"
        minSdk = 24
        targetSdk = 34
        vectorDrawables.useSupportLibrary = true

        versionName = "$versionMajor.$versionSprint.$versionSprintRevision"
        val now = LocalDateTime.now(ZoneOffset.UTC)
        versionCode = now.format(DateTimeFormatter.ofPattern("yyMMddHH")).toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        resValue("string", "app_name", "VUBaseProject")

        buildConfigField("long", "VERSION_CODE", "${defaultConfig.versionCode}")
        buildConfigField("String", "VERSION_NAME", "\"${defaultConfig.versionName}\"")
    }

    val keystorePropertiesFile = rootProject.file("keystore.properties")
    val keystoreProperties = Properties()
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))

    signingConfigs {
        create("release") {
            storeFile = file(keystoreProperties["RELEASE_STORE_FILE"] ?: "")
            storePassword = (keystoreProperties["RELEASE_STORE_PASSWORD"] ?: "").toString()
            keyAlias = (keystoreProperties["RELEASE_KEY_ALIAS"] ?: "").toString()
            keyPassword = (keystoreProperties["RELEASE_KEY_PASSWORD"] ?: "").toString()
        }
    }

    flavorDimensions.add("default")
    productFlavors {
        create("des") {
            resValue("string", "app_name", "QA VUBaseProject")
            applicationIdSuffix = ".des"

            buildConfigField("String", "BASE_URL_VASS_UNIVERSITY_BASE_PROJECT", "\"https://pokeapi.co/api/v2/\"")
        }

        create("pro") {
            buildConfigField("String", "BASE_URL_VASS_UNIVERSITY_BASE_PROJECT", "\"https://pokeapi.co/api/v2/\"")
        }

        create("qapro") {
            initWith(getByName("pro"))

            resValue("string", "app_name", "PRO VUBaseProject")
            applicationIdSuffix = ".pro"
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro", "proguard-disable-log.pro")
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true

            configure<CrashlyticsExtension> {
                mappingFileUploadEnabled = false
            }
        }

        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro", "proguard-disable-log.pro")

            configure<CrashlyticsExtension> {
                mappingFileUploadEnabled = true
            }

            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packaging {
        resources.excludes.add("META-INF/LICENSE.md")
        resources.excludes.add("META-INF/LICENSE-notice.md")
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    secrets {
        // Optionally specify a different file name containing your secrets.
        // The plugin defaults to "local.properties"
        propertiesFileName = "secrets.properties"

        // A properties file containing default secret values. This file can be
        // checked in version control.
        defaultPropertiesFileName = "local.defaults.properties"

        // Configure which keys should be ignored by the plugin by providing regular expressions.
        // "sdk.dir" is ignored by default.
        ignoreList.add("keyToIgnore") // Ignore the key "keyToIgnore"
        ignoreList.add("sdk.*")       // Ignore all keys matching the regexp "sdk.*"
    }
}

dependencies {

    //Libs
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar", "*.jar"))))
    //Android
    implementation(libs.bundles.android)
    //Navigation
    implementation(libs.bundles.navigation)
    androidTestImplementation(libs.androidxNavigationTesting)
    //Hilt
    implementation(libs.daggerHilt)
    kapt(libs.daggerHiltCompiler)
    //Lifecycle
    implementation(libs.bundles.lifecycle)
    //Coroutines
    implementation(libs.bundles.coroutines)
    //Testing
    androidTestImplementation(libs.junitJupiter)
    testRuntimeOnly(libs.junitJupiterEngine)
    testImplementation(libs.bundles.jupiterTestImplementation)
    testImplementation(libs.bundles.mockito)
    androidTestImplementation(libs.bundles.espresso)
    //Retrofit
    implementation(libs.bundles.retrofit)
    //Okhttp
    implementation(libs.okhttp)
    implementation(libs.gson)
    //Volley
    implementation(libs.volley)
    //Glide
    implementation(libs.bundles.glide)
    ksp(libs.bundles.glideKsp)
    //Firebase
    implementation(libs.bundles.firebase)
    //Biometric
    implementation(libs.androidxBiometric)
    //Encrypted Shared Preferences
    implementation(libs.androidxCryptoSharedPreferences)
    //CustomTab
    implementation(libs.androidxCustomTab)
    //Flexbox
    implementation(libs.flexbox)
    //Json
    implementation(libs.json)
}

kapt {
    correctErrorTypes = true
}

task<DefaultTask>("downloadString") {
    doLast {
        val baseRequest =
            "https://movilidad.vass.es/jenkins/buildByToken/buildWithParameters?job=VASS_Localizer_GSheet&token=token&googlesheetid="
        val googleSheetId = "1qcT60wAW0CJMX6m7DVDe0Tyc99cHBC1uJWerzJY8SkM"
        val secondsSleep = 25L
        val connection = URL("$baseRequest$googleSheetId").openConnection() as java.net.HttpURLConnection

        try {
            connection.requestMethod = "GET"
            connection.connect()
            val responseCode = connection.responseCode
            println("l> Response code downloadString: $responseCode")
        } finally {
            connection.disconnect()
        }

        sleep(secondsSleep * 1000)

        download.run {
            src("http://movilidad.vass.es/VASSLocalizer/$googleSheetId/android/values-en/strings.xml")
            dest("src\\main\\res\\values\\strings.xml")
        }
        download.run {
            src("http://movilidad.vass.es/VASSLocalizer/$googleSheetId/android/values-es/strings.xml")
            dest("src\\main\\res\\values-es\\strings.xml")
        }
    }
}