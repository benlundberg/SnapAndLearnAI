package com.app.snaplearnai.shared

import android.app.Application
import com.app.snaplearnai.shared.util.LocalizedResources
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        LocalizedResources.init(this)
    }
}