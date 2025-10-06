package com.app.snaplearnai.shared.util

import android.content.res.Resources
import androidx.annotation.StringRes
import com.app.snaplearnai.shared.MainApplication

object LocalizedResources {
    @Volatile
    private var res: Resources? = null

    @Synchronized
    @JvmStatic
    fun init(application: MainApplication) {
        if (res == null) {
            res = application.resources
        }
    }

    fun @receiver:StringRes Int.localized(): String {
        return res?.getString(this) ?: ""
    }
}