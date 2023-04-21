package com.adpushup.example.appopenad;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.adpushup.apmobilesdk.ApMobileSdk;
import com.adpushup.apmobilesdk.ads.ApAppOpen;

public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks, LifecycleEventObserver {
    private ApAppOpen apAppOpen;
    private Activity currentActivity;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // Initialising Ap Mobile SDK Error Detection Module.
        ApMobileSdk.initACRA(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
        // Initialising Ap Mobile SDK.
        ApMobileSdk.init(this, getString(R.string.ap_app_id));
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        apAppOpen = new ApAppOpen();
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
        if(event == Lifecycle.Event.ON_START) {
            // Show the ad (if available) when the app moves to foreground.
            // NOTE: For Ad Optimisations, ApMobileSDK will will only show the ad at very specific time.
            if(currentActivity != null){
                apAppOpen.showAd(currentActivity, () -> {});
            }
        }
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        // Updating the currentActivity only when an ad is not showing.
        if (!apAppOpen.isShowingAd()) {
            currentActivity = activity;
        }
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {}

    @Override
    public void onActivityResumed(@NonNull Activity activity) {}

    @Override
    public void onActivityPaused(@NonNull Activity activity) {}

    @Override
    public void onActivityStopped(@NonNull Activity activity) {}

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {}

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {}

}
