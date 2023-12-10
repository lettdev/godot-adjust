package krad.godot.plugin.android.adjust

import android.util.Log
import android.widget.Toast
import android.app.Activity
import android.app.Application
import android.os.Bundle

import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustConfig
import com.adjust.sdk.LogLevel

import org.godotengine.godot.Godot
import org.godotengine.godot.plugin.GodotPlugin
import org.godotengine.godot.plugin.UsedByGodot

class GodotAdjust(godot: Godot): GodotPlugin(godot) {
    companion object {
        val TAG = GodotAdjust::class.java.simpleName
    }
    
    override fun getPluginName() = BuildConfig.GODOT_PLUGIN_NAME

    @UsedByGodot
    private fun init(appToken: String, production: Boolean) {
        runOnUiThread {
            Toast.makeText(activity, appToken, Toast.LENGTH_LONG).show()
            Log.v(TAG, appToken)
            
            val environment = if (production) AdjustConfig.ENVIRONMENT_PRODUCTION else AdjustConfig.ENVIRONMENT_SANDBOX

            // val config = AdjustConfig(activity, appToken, environment)

            // Adjust.onCreate(config)
            // Adjust.onResume()

            activity!!.application!!.registerActivityLifecycleCallbacks(AdjustLifecycleCallbacks())
        }
    }

    private class AdjustLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
        override fun onActivityResumed(activity: Activity) {
            Adjust.onResume()
        }

        override fun onActivityPaused(activity: Activity) {
            Adjust.onPause()
        }

        override fun onActivityStopped(activity: Activity) {}

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

        override fun onActivityDestroyed(activity: Activity) {}

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

        override fun onActivityStarted(activity: Activity) {}
    }

    @UsedByGodot
    private fun onButtonPressed() {
        Log.i(TAG, "OnButtonPressed from Kotlin")
    }
}