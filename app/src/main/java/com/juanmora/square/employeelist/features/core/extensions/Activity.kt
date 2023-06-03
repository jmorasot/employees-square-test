package com.juanmora.square.employeelist.features.core.extensions

import android.app.Activity
import android.content.Intent

inline fun <reified T : Activity> Activity.launchActivity(block: ((extras: Intent) -> Unit) = {}) {
    startActivity(Intent(this, T::class.java).apply(block))
}

inline fun <reified T : Activity> Activity.launchActivityAndFinish(block: ((extras: Intent) -> Unit) = {}) {
    startActivity(Intent(this, T::class.java).apply(block))
    finish()
}
