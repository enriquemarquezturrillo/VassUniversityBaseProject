package com.vasscompany.vassuniversitybaseproject.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Message
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.browser.customtabs.CustomTabsIntent
import com.vasscompany.vassuniversitybaseproject.data.constants.GeneralConstants.Companion.MARGIN_ERROR_WEBVIEW
import com.vasscompany.vassuniversitybaseproject.ui.extensions.TAG
import kotlin.math.floor


class WebUtils {

    interface ListenerWebView {
        fun onPageStarted(url: String)
        fun onPageFinished(url: String, hasLoaded: Boolean)
        fun doUpdateVisitedHistory(url: String)
        fun onReceivedError(error: WebResourceError?)
        fun reachEndScroll()
        fun overrideUrl(path: Uri): Boolean
    }

    companion object {


        /**
         * Method to initialize a webview with webclient, webChromeClient and JavaScript Support
         * @param webView
         */
        fun initializeWebView(webView: WebView, listenerWebView: ListenerWebView): WebView {
            initializeWebClient(webView, listenerWebView)
            initilizeWebChromeClient(webView)
            configSettingsWebView(webView)
            webView.setOnScrollChangeListener { view, _, _, _, _ ->
                val height = floor((webView.contentHeight * webView.context.resources.displayMetrics.density)).toInt()
                val webViewHeight: Int = webView.measuredHeight
                if ((height - (height * MARGIN_ERROR_WEBVIEW)) > 0) {
                    if (view.scrollY + webViewHeight >= (height - (height * MARGIN_ERROR_WEBVIEW))) {
                        Log.d(TAG, "l> END reached")
                        listenerWebView.reachEndScroll()
                    }
                }
            }
            return webView
        }

        @SuppressLint("SetJavaScriptEnabled")
        private fun configSettingsWebView(webView: WebView) {
            webView.settings.javaScriptEnabled = true
            webView.settings.domStorageEnabled = true
            webView.settings.builtInZoomControls = true
            webView.settings.setSupportZoom(true)
            webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
            webView.clearCache(true)
            webView.clearHistory()
        }

        /**
         * Method to initialize Webclient into a WebView
         * @param webView
         */
        private fun initializeWebClient(webView: WebView, listenerWebView: ListenerWebView) {
            webView.webViewClient = object : WebViewClient() {
                var hasLoaded = false
                override fun shouldOverrideUrlLoading(webView: WebView, request: WebResourceRequest): Boolean {
                    Log.d(TAG, "l> shouldOverrideUrlLoading${request.url.path}")
                    return listenerWebView.overrideUrl(request.url)
                }

                override fun onPageStarted(webView: WebView, url: String, favicon: Bitmap?) {
                    Log.d(TAG, "l> onPageStarted: $url")
                    super.onPageStarted(webView, url, favicon)
                    listenerWebView.onPageStarted(url)
                }

                override fun onPageFinished(webView: WebView, url: String) {
                    Log.d(TAG, "l> onPageFinished: $url")
                    super.onPageFinished(webView, url)
                    listenerWebView.onPageFinished(url, hasLoaded)
                    webView.evaluateJavascript(
                        "(function(){return document.body.scrollHeight > window.innerHeight;})();"
                    ) { result ->
                        Log.d(TAG, "l> tenemos scroll: ${result.toBoolean()}")
                        if (!result.toBoolean()) {
                            listenerWebView.reachEndScroll()
                        }
                    }
                }

                override fun doUpdateVisitedHistory(webView: WebView, url: String, isReload: Boolean) {
                    Log.d(TAG, "l> doUpdateVisitedHistory: $url")
                    super.doUpdateVisitedHistory(webView, url, isReload)
                    hasLoaded = true
                    listenerWebView.doUpdateVisitedHistory(url)
                }

                override fun onReceivedError(webView: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                    Log.d(TAG, "l> onReceivedError: ${error?.errorCode}")
                    hasLoaded = true
                    when (error?.errorCode) {
                        ERROR_UNKNOWN -> {
                            Log.d(TAG, "l> onReceivedError: ERROR_UNKNOWN")
                        }

                        ERROR_HOST_LOOKUP -> {
                            Log.d(TAG, "l> onReceivedError: ERROR_UNKNOWN")
                        }

                        ERROR_UNSUPPORTED_AUTH_SCHEME -> {
                            Log.d(TAG, "l> onReceivedError: ERROR_UNSUPPORTED_AUTH_SCHEME")
                        }

                        ERROR_AUTHENTICATION -> {
                            Log.d(TAG, "l> onReceivedError: ERROR_AUTHENTICATION")
                        }

                        ERROR_PROXY_AUTHENTICATION -> {
                            Log.d(TAG, "l> onReceivedError: ERROR_PROXY_AUTHENTICATION")
                        }

                        ERROR_CONNECT -> {
                            Log.d(TAG, "l> onReceivedError: ERROR_CONNECT")
                        }

                        ERROR_IO -> {
                            Log.d(TAG, "l> onReceivedError: ERROR_IO")
                        }

                        ERROR_TIMEOUT -> {
                            Log.d(TAG, "l> onReceivedError: ERROR_TIMEOUT")
                        }

                        ERROR_REDIRECT_LOOP -> {
                            Log.d(TAG, "l> onReceivedError: ERROR_REDIRECT_LOOP")
                        }

                        ERROR_UNSUPPORTED_SCHEME -> {
                            Log.d(TAG, "l> onReceivedError: ERROR_UNSUPPORTED_SCHEME")
                        }

                        ERROR_FAILED_SSL_HANDSHAKE -> {
                            Log.d(TAG, "l> onReceivedError: ERROR_FAILED_SSL_HANDSHAKE")
                        }

                        ERROR_BAD_URL -> {
                            Log.d(TAG, "l> onReceivedError: ERROR_BAD_URL")
                        }

                        ERROR_FILE -> {
                            Log.d(TAG, "l> onReceivedError: ERROR_FILE")
                        }

                        ERROR_FILE_NOT_FOUND -> {
                            Log.d(TAG, "l> onReceivedError: ERROR_FILE_NOT_FOUND")
                        }

                        ERROR_TOO_MANY_REQUESTS -> {
                            Log.d(TAG, "l> onReceivedError: ERROR_TOO_MANY_REQUESTS")
                        }

                        ERROR_UNSAFE_RESOURCE -> {
                            Log.d(TAG, "l> onReceivedError: ERROR_UNSAFE_RESOURCE")
                        }

                        else -> {
                            Log.d(TAG, "l> onReceivedError: UNKNOW")
                        }
                    }
                    super.onReceivedError(webView, request, error)
                    listenerWebView.onReceivedError(error)
                }
            }
        }

        /**
         * Method to initialize WebChromeClient into a WebView
         * @param webView
         */
        private fun initilizeWebChromeClient(webView: WebView) {
            webView.webChromeClient = object : WebChromeClient() {
                override fun onCreateWindow(
                    view: WebView,
                    isDialog: Boolean,
                    isUserGesture: Boolean,
                    resultMsg: Message
                ): Boolean {
                    return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)
                }

                override fun onCloseWindow(window: WebView) {
                    super.onCloseWindow(window)
                }
            }
        }

        fun openUrlInsideApp(context: Context, url: String) {
            CustomTabsIntent.Builder().build().launchUrl(context, Uri.parse(url))
        }

        fun openUrlInsideAppReplaceToAddApplicationModelVersion(context: Context, url: String) {
            CustomTabsIntent.Builder().build().launchUrl(context, Uri.parse(url))
        }

        fun openUrlPdfInsideApp(context: Context, url: String) {
            val customTabsIntent = CustomTabsIntent.Builder().build()
            val uri = Uri.parse("https://docs.google.com/gview?embedded=true&url=$url")
            customTabsIntent.launchUrl(context, uri)
        }

        fun injectJavaScript(code: String) = Unit

        fun openUrlOutsideApp(context: Context, url: Uri) {
            context.startActivity(
                Intent(Intent.ACTION_VIEW, url)
            )
        }
    }
}