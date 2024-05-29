package com.vasscompany.vassuniversitybaseproject.ui.extensions

import android.content.Context
import android.os.Build
import android.os.Handler
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.NavDirections

//https://developer.android.com/reference/android/util/Log.html
//is thrown if the tag.length() > 23 for Nougat (7.0) and prior releases (API <= 25), there is no tag limit of concern after this API level.
val Any.TAG: String
    get() {
        val tagSimpleName = javaClass.simpleName
        val tagName = javaClass.name
        return when {
            tagSimpleName.isNotBlank() -> {
                if (tagSimpleName.length > 23) {
                    tagSimpleName.takeLast(23)
                } else {
                    tagSimpleName
                }
            }

            tagName.isNotBlank() -> {
                if (tagName.length > 23) {
                    tagName.takeLast(23)
                } else {
                    tagName
                }
            }

            else -> {
                "TAG unknow"
            }
        }
    }

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visible(show: Boolean) {
    if (show) {
        visible()
    } else {
        gone()
    }
}

fun Context.toastShort(message: String) {
    toastMessageDuration(message, Toast.LENGTH_SHORT)
}

fun Context.toastLong(message: String) {
    toastMessageDuration(message, Toast.LENGTH_LONG)
}

fun Context.toastMessageDuration(message: String, durationToast: Int) {
    val mainHandler = Handler(this.mainLooper)
    val runnable = Runnable {
        Toast.makeText(this, message, durationToast).show()
    }
    mainHandler.post(runnable)
}

fun Context.dpToPixel(dp: Int): Float {
    return dp.toFloat() * this.resources.displayMetrics.density
}

fun Context.spToPixel(sp: Int): Float {
    return sp.toFloat() * this.resources.displayMetrics.density
}

fun Context.pixelToDp(pixel: Float): Int {
    return (pixel / this.resources.displayMetrics.density).toInt()
}

fun String.replaceLinkToAddApplicationModelVersion(): String {
    val model = Build.MODEL.replace("(", "").replace(")", "")
    val versionOS = Build.VERSION.RELEASE.replace("(", "").replace(")", "")
    return this
        .replace("{dispositivo}", model)
        .replace("{version_os}", versionOS)
}

fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
}

fun String?.underlined(): SpannableString {
    return if (this != null) {
        val spannableString = SpannableString(this)
        spannableString.setSpan(UnderlineSpan(), 0, this.length, 0)
        spannableString
    } else SpannableString("")
}