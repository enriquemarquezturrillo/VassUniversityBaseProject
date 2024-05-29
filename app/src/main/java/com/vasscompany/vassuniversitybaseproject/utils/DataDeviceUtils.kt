package com.vasscompany.vassuniversitybaseproject.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.TelephonyManager
import android.util.DisplayMetrics
import android.util.Log
import com.google.firebase.crashlytics.internal.common.CommonUtils
import com.vasscompany.vassuniversitybaseproject.BuildConfig
import com.vasscompany.vassuniversitybaseproject.data.constants.GeneralConstants
import com.vasscompany.vassuniversitybaseproject.ui.extensions.TAG
import java.io.File
import java.net.InetAddress
import java.net.NetworkInterface
import java.text.NumberFormat
import java.util.Collections
import java.util.Locale
import kotlin.math.sqrt


class DataDeviceUtils {
    companion object {
        fun getVersionApp(): String {
            return BuildConfig.VERSION_NAME.replace("-des", "").replace("-mock", "")
        }

        fun getModelDevice(): String {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            return if (model.lowercase().startsWith(manufacturer.lowercase())) {
                capitalize(model)
            } else {
                capitalize(manufacturer) + " " + model
            }
        }

        private fun capitalize(text: String?): String {
            if (text == null || text.isEmpty()) {
                return ""
            }
            val first = text[0]
            return if (Character.isUpperCase(first)) {
                text
            } else {
                Character.toUpperCase(first).toString() + text.substring(1)
            }
        }

        fun getVersionSO(): String {
            return Build.VERSION.RELEASE
        }

        fun getSO(): String {
            return "Android ${getVersionSO()} ${getArchitecture()}"
        }

        private fun getArchitecture(): String {
            return System.getProperty("os.arch", "") ?: ""
        }

        fun isDeviceRoot(context: Context): Boolean {
            val firebaseRoot = CommonUtils.isRooted()
            val checkRootFilesAndPackages = checkRootFilesAndPackages(context)
            val suPaths = checkSUPaths()
            Log.d(TAG, "l> isDeviceRoot firebaseRoot: $firebaseRoot")
            Log.d(TAG, "l> isDeviceRoot checkRootFilesAndPackages: $checkRootFilesAndPackages")
            Log.d(TAG, "l> isDeviceRoot suPaths: $suPaths")
            return firebaseRoot || checkRootFilesAndPackages || suPaths
        }

        fun getIPAddress(useIPv4: Boolean): String? {
            try {
                val interfaces: List<NetworkInterface> = Collections.list(NetworkInterface.getNetworkInterfaces())
                for (intf in interfaces) {
                    val addrs: List<InetAddress> = Collections.list(intf.inetAddresses)
                    for (addr in addrs) {
                        if (!addr.isLoopbackAddress) {
                            val sAddr = addr.hostAddress
                            //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                            val indexOf = sAddr?.indexOf(':')
                            var isIPv4 = false
                            isIPv4 = if (indexOf != null) {
                                sAddr.indexOf(':') < 0
                            } else {
                                false
                            }
                            if (useIPv4) {
                                if (isIPv4) return sAddr
                            } else {
                                if (!isIPv4) {
                                    val delim = sAddr?.indexOf('%') // drop ip6 zone suffix
                                    return if (delim != null) {
                                        if (delim < 0) sAddr.uppercase() else sAddr.substring(0, delim).uppercase()
                                    } else {
                                        ""
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (ignored: Exception) {
            } // for now eat exceptions
            return ""
        }

        private val knownRootAppsPackages = arrayOf(
            "com.noshufou.android.su",
            "com.noshufou.android.su.elite",
            "eu.chainfire.supersu",
            "com.koushikdutta.superuser",
            "com.thirdparty.superuser",
            "com.yellowes.su",
            "com.topjohnwu.magisk",
            "com.kingroot.kinguser",
            "com.kingo.root",
            "com.smedialink.oneclickroot",
            "com.zhiqupk.root.global",
            "com.alephzain.framaroot"
        )

        private fun checkRootFilesAndPackages(context: Context): Boolean {
            var result = false
            for (knownRootAppsPackage in knownRootAppsPackages) {
                if (isPackageInstalled(knownRootAppsPackage, context)) {
                    result = true
                    break
                }
            }
            return result
        }

        private fun isPackageInstalled(packagename: String, context: Context): Boolean {
            val pm: PackageManager = context.packageManager
            return try {
                pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES)
                true
            } catch (nameNotFoundException: PackageManager.NameNotFoundException) {
                false
            }
        }

        private val knownSUPaths = arrayOf(
            "/system/bin/su",
            "/system/xbin/su",
            "/sbin/su",
            "/system/su",
            "/system/bin/.ext/.su",
            "/system/usr/we-need-root/su-backup",
            "/system/xbin/mu"
        )

        private fun checkSUPaths(): Boolean {
            var result = false
            for (knownSUPath in knownSUPaths) {
                val file = File(knownSUPath)
                if (file.exists()) {
                    result = true
                }
            }
            return result
        }

        fun getUserCountry(context: Context): String {
            try {
                val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                val simCountry = tm.simCountryIso
                if (simCountry != null && simCountry.length == 2) { // SIM country code is available
                    return simCountry.lowercase()
                } else if (tm.phoneType != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
                    val networkCountry = tm.networkCountryIso
                    if (networkCountry != null && networkCountry.length == 2) { // network country code is available
                        return networkCountry.lowercase()
                    }
                }
            } catch (exception: java.lang.Exception) {
                Log.e(TAG, "l> Exception getUserCountry: ${exception.message}")
            }
            return ""
        }

        private fun isTablet(activity: Activity): Boolean {
            val metrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(metrics)
            val yInches = metrics.heightPixels / metrics.ydpi
            val xInches = metrics.widthPixels / metrics.xdpi
            val diagonalInches = sqrt((xInches * xInches + yInches * yInches).toDouble())
            return diagonalInches >= GeneralConstants.DIAGONAL_INCHES_TABLE_MIN
        }

        fun getIsTabletOrPhoneLoginService(activity: Activity): String {
            return if (isTablet(activity)) {
                GeneralConstants.ANDROID_TABLET
            } else {
                GeneralConstants.ANDROID_MOVIL
            }
        }

        fun formatNumber(text: String): String {
            return try {
                var textToFormat: String = text
                if (textToFormat.contains(",")) {
                    textToFormat = textToFormat.replace(",".toRegex(), "")
                }
                val number: Long = textToFormat.toLong()
                val formattedText = NumberFormat.getNumberInstance(Locale.US).format(number)
                formattedText
            } catch (nfe: java.lang.NumberFormatException) {
                nfe.printStackTrace()
                text
            }
        }
    }
}