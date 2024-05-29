package com.vasscompany.vassuniversitybaseproject.data.constants

class GeneralConstants {
    companion object {
        //Retrofit
        const val RETROFIT_TIMEOUT_IN_SECOND: Long = 30
        const val GENERAL_ERROR_401_UNAUTHORIZED = 401

        //DI
        const val NAME_OKHTTPCLIENT_JSON = "OkHttpClientJSON"
        const val NAME_OKHTTPCLIENT_HTML = "OkHttpClientHTML"
        const val NAME_RETROFIT_JSON = "OkHttpClientJSON"
        const val NAME_RETROFIT_HTML = "OkHttpClientHTML"

        //Webview
        const val MARGIN_ERROR_WEBVIEW = 0.002
        const val URL_PDF_BASE: String = "https://www.prueba.com"

        //Device Util
        const val OS_ANDROID = "Android"
        const val DIAGONAL_INCHES_TABLE_MIN: Float = 6.8f
        const val ANDROID_MOVIL = "ANDROID_MOVIL"
        const val ANDROID_TABLET = "ANDROID_TABLET"
    }
}