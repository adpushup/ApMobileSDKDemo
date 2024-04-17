package com.adpushup.example.appopenad

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.adpushup.apmobilesdk.ApMobileSdk
import com.adpushup.apmobilesdk.ads.ApAppOpen

class MyApp: Application(), Application.ActivityLifecycleCallbacks, LifecycleEventObserver {
    private lateinit var apAppOpen: ApAppOpen
    private var currentActivity: Activity? = null

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
        // Initialising Ap Mobile SDK.
        ApMobileSdk.init(this, getString(R.string.ap_app_id))
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        apAppOpen = ApAppOpen()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if(event == Lifecycle.Event.ON_START) {
            // Show the ad (if available) when the app moves to foreground.
            // NOTE: For Ad Optimisations, ApMobileSDK will will only show the ad at very specific time.
            currentActivity?.let {
                apAppOpen.showAd(it, getString(R.string.ad_placement_id)) {}
            }
        } else if(event == Lifecycle.Event.ON_RESUME) {
            ApMobileSdk.resume(this)
        }
    }

    override fun onActivityStarted(activity: Activity) {
        // Updating the currentActivity only when an ad is not showing.
        if (!apAppOpen.isShowingAd) {
            currentActivity = activity
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}
}