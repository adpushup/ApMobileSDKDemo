# Banner Ads

Banner ads occupy a spot within an app's layout, either at the top or bottom of the device screen. They stay on screen while users are interacting with the app, and can refresh automatically after a certain period of time. If you're new to mobile advertising, they're a great place to start.

You can display only one banner ad on the screen.

You can use our demo app as a reference project.

<aside>
📎 Demo App: https://github.com/adpushup/ApMobileSDKDemo/tree/master/AdaptiveBannerAdExample

</aside>

## Implementation Guide

To implement Adaptive Banner Ads in your app, follow these steps:

1. Ad a Placeholder **FrameLayout** in your Layout Resource File:
    
    ```xml
    <FrameLayout
        android:id="@+id/adFrame"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>
    ```
    
2. Create an ApBanner and add adView to your frame layout.
    
    > **`AP_PLACEMENT_ID` is like an AdUnitID and it will be provided to you by AdPushup.**
    > 
    
    *Kotlin Example:*
    
    ```kotlin
    val adFrame : FrameLayout = findViewById(R.id.adFrame)
    val apBanner = ApBanner("AP_PLACEMENT_ID")
    adFrame.addView(apBanner.getAdView(context))
    ```
    
    *JAVA Example:*
    
    ```java
    FrameLayout adFrame = findViewById(R.id.adFrame);
    ApBanner apBanner = new ApBanner("AP_PLACEMENT_ID");
    adFrame.addView(apBanner.getAdView(context));
    ```
    
3. Load an ad.
    
    *Kotlin Example:*
    
    ```kotlin
    apBanner.loadAdaptiveBanner(context, adSize, object : ApBannerListener{})
    ```
    
    *JAVA Example:*
    
    ```java
    apBanner.loadAdaptiveBanner(context, adSize, new ApBannerListener() {});
    ```

    Here, the `adSize` is the size of the banner ad that you calculated at runtime.
    
4. Destroy the ad in the `onDestroy()` function of your activity.
    
    *Kotlin Example:*
    
    ```kotlin
    override fun onDestroy() {
        super.onDestroy()
        apBanner.destroy()
    }
    ```
    
    *JAVA Example:*
    
    ```java
    @Override
    protected void onDestroy() {
        super.onDestroy();
        apBanner.destroy();
    }
    ```
    

## Supported Callbacks

```kotlin
ApBannerListener {
		// Called when the ad is closed.
		override fun onAdClosed() {}

		// Called when the ad is opened.
		override fun onAdOpened() {}

		// Called when the ad is loaded.
		override fun onAdLoaded() {}

		// Called when the ad is clicked.
		override fun onAdClicked() {}

		// Called on ad impression.
		override fun onAdImpression() {}

		// Called when the ad is unable to display due to an error.
		override fun onError(code: Int, message: String?) {}

		// Called when the ad is displayed but with some warnings.
		override fun onWarning(code: Int, message: String?) {}
}
```