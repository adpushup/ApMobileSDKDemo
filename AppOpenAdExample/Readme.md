# App Open Ads

App open ads are a special ad format intended for publishers wishing to monetize their app load screens. App open ads can be closed at any time, and are designed to be shown when your users bring your app to the foreground.

You can use our demo app as a reference project.

<aside>
📎 Demo App: https://github.com/adpushup/ApMobileSDKDemo/tree/master/AppOpenAdExample

</aside>

## Implementation Guide

You can implement Ap App Open Ads in two ways:

1. Quick Implementation
2. Step-By-Step Implementation

### 1. Quick Implementation

1. Create a **MyApp** Class in your app code and copy the following code in it:
    
    *Kotlin Example:*
    
    ```kotlin
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
    
        override fun attachBaseContext(base: Context) {
            super.attachBaseContext(base)
            // Initialising Ap Mobile SDK Error Detection Module.
            ApMobileSdk.initACRA(this)
        }
    
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
                currentActivity?.let {
                    apAppOpen.showAd(it) {}
                }
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
    ```
    
    *JAVA Example:*
    
    ```java
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
    
    public class MyApp extends Application implements Application.ActivityLifecycleCallbacks, LifecycleEventObserver {
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
    ```
    
2. Next, add the following code to your `AndroidManifest.xml` : 
    
    ```xml
    <manifest>
        <application
                android:name=".MyApp" >
            ...
        </application>
    </manifest>
    ```
    
    Be sure to reference your actual package name.
    

---

### 2. Step By Step Procedure:

To implement App Open Ads in your app, follow these steps:

**Extend the Application class**

1. Create a new class that extends the `Application` class, and add the following code to initialize the Ap Mobile SDK:
    
    *Kotlin Example:*
    
    ```kotlin
    /** Application class that initializes, loads and shows ads when activities change states. */
    
    class MyApp: Application() {
        override fun attachBaseContext(base: Context) {
            super.attachBaseContext(base)
            // Initialising Ap Mobile SDK Error Detection Module.
            ApMobileSdk.initACRA(this)
        }
    
        override fun onCreate() {
            super.onCreate()
            // Initialising Ap Mobile SDK.
            ApMobileSdk.init(this, getString(R.string.ap_app_id))
        }
    }
    ```
    
    *JAVA Example:*
    
    ```java
    /** Application class that initializes, loads and shows ads when activities change states. */
    public class MyApp extends Application {
        @Override
        protected void attachBaseContext(Context base) {
            super.attachBaseContext(base);
            // Initialising Ap Mobile SDK Error Detection Module.
            ApMobileSdk.initACRA(this);
        }
    
        @Override
        public void onCreate() {
            super.onCreate();
            // Initialising Ap Mobile SDK.
            ApMobileSdk.init(this, getString(R.string.ap_app_id));
        }
    }
    ```
    
    This will initialize the SDK and provide the skeleton where you'll later register for app foregrounding events.
    
2. Next, add the following code to your `AndroidManifest.xml` : 
    
    ```xml
    <manifest>
        <application
                android:name=".MyApp" >
            ...
        </application>
    </manifest>
    ```
    
    Be sure to reference your actual package name.
    

**Implement ApAppOpen Ads to your Application class:**

1. Implement `Application.ActivityLifecycleCallbacks, LifecycleEventObserver` to your Application Class.
    
    *Kotlin Example:*
    
    ```kotlin
    ...
    
    class MyApp: Application(), Application.ActivityLifecycleCallbacks, LifecycleEventObserver {
            ...
    }
    ```
    
    *JAVA Example:*
    
    ```java
    ...
    
    public class MyApp extends Application implements Application.ActivityLifecycleCallbacks, LifecycleEventObserver {
            ...
    }
    ```
    
2. Create `ApAppOpen` and `Activity` variables in the class
    
    *Kotlin Example:*
    
    ```kotlin
    ...
    
    class MyApp: Application(), Application.ActivityLifecycleCallbacks, LifecycleEventObserver {
        private lateinit var apAppOpen: ApAppOpen
        private var currentActivity: Activity? = null
        ...
    }
    ```
    
    *JAVA Example:*
    
    ```java
    ...
    
    public class MyApp extends Application implements Application.ActivityLifecycleCallbacks, LifecycleEventObserver {
        private ApAppOpen apAppOpen;
        private Activity currentActivity;
        ...
    }
    ```
    
3. Implement the Activity Lifecycle Callbacks. Also, update the current activity in the `onActivityStarted()` Callback.
    
    *Kotlin Example:*
    
    ```kotlin
    ...
    
    class MyApp: Application(), Application.ActivityLifecycleCallbacks, LifecycleEventObserver {
        private var currentActivity: Activity? = null
        ...
    
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
    ```
    
    *JAVA Example:*
    
    ```java
    ...
    
    public class MyApp extends Application implements Application.ActivityLifecycleCallbacks, LifecycleEventObserver {
        private Activity currentActivity;
        ...
    
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
    ```
    
4. Implement `LifecycleEventObserver` callback `onStateChanged()` and call `apAppOpen.showAd()` method to show ad.
    
    *Kotlin Example:*
    
    ```kotlin
    ...
    
    class MyApp: Application(), Application.ActivityLifecycleCallbacks, LifecycleEventObserver {
        private lateinit var apAppOpen: ApAppOpen
        private var currentActivity: Activity? = null
        ...
    		
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if(event == Lifecycle.Event.ON_START) {
                // Show the ad (if available) when the app moves to foreground.
                currentActivity?.let {
                    apAppOpen.showAd(it) {}
                }
            }
        }
    		
        ...
    }
    ```
    
    *JAVA Example:*
    
    ```java
    ...
    
    public class MyApp extends Application implements Application.ActivityLifecycleCallbacks, LifecycleEventObserver {
        private ApAppOpen apAppOpen;
        private Activity currentActivity;
        ...
    		
        @Override
        public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
            if(event == Lifecycle.Event.ON_START) {
                // Show the ad (if available) when the app moves to foreground.
                if(currentActivity != null){
                    apAppOpen.showAd(currentActivity, () -> {});
                }
            }
        }
    		
        ...
    }
    ```
    

## Supported Callbacks

```kotlin
ApAppOpenListener {
		// Called when the ad is clicked.
		override fun onAdClicked() {}

		// Called on the ad impression.
		override fun onAdImpression() {}

		// Called when the ad is shown.
		override fun onAdShowedFullScreenContent() {}

		// Called when the ad is dismissed.
		override fun onAdDismissedFullScreenContent() {}

		// Called when the ad is not ready.
		override fun onAdNotLoadedYet() {}

		// Best place to resume your work.
		override fun onComplete() {}

		// Called when the ad is unable to display due to an error.
		override fun onError(code: Int, message: String?) {}

		// Called when the ad is displayed but with some warnings.
		override fun onWarning(code: Int, message: String?) {}
}
```