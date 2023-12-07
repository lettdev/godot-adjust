package com.krad.godot.adjust

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull

import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustConfig
import com.adjust.sdk.LogLevel

import org.godotengine.godot.Godot
import org.godotengine.godot.plugin.GodotPlugin
import org.godotengine.godot.plugin.UsedByGodot

class GodotAdjust(godot: Godot): GodotPlugin(godot) {

    override fun getPluginName() = BuildConfig.GODOT_PLUGIN_NAME

    private val TAG = "godot-adjust"
    private val activity: Activity = godot.activity

    fun init(appToken: String, production: Boolean) {
        Log.i(TAG, "Started initializing GodotAdjust Singleton with App Token: $appToken")
        activity.runOnUiThread {
            val environment = if (production) AdjustConfig.ENVIRONMENT_PRODUCTION else AdjustConfig.ENVIRONMENT_SANDBOX

            val config = AdjustConfig(activity, appToken, environment)
            config.setLogLevel(LogLevel.VERBOSE)

            Adjust.onCreate(config)
            Adjust.onResume()

            activity.application.registerActivityLifecycleCallbacks(AdjustLifecycleCallbacks())

            Log.i(TAG, "Finished initializing GodotAdjust Singleton")
        }
    }

    private inner class AdjustLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
        override fun onActivityResumed(activity: Activity) {
            Adjust.onResume()
        }

        override fun onActivityPaused(activity: Activity) {
            Adjust.onPause()
        }

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
        override fun onActivityStarted(activity: Activity) {}
        override fun onActivityStopped(activity: Activity) {}
        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
        override fun onActivityDestroyed(activity: Activity) {}
    }

    @NonNull
    override fun getPluginName(): String {
        return "GodotAdjust"
    }

    @NonNull
    override fun getPluginMethods(): List<String> {
        return listOf("init")
    }
}
