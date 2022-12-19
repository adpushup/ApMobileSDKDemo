# Native Ads

Customizable ads that match the look and feel of your app. You decide how and where they're placed, so the layout is more consistent with your app's design.

You can use our demo app as a reference project.

<aside>
📎 Demo App: https://github.com/adpushup/ApMobileSDKDemo/tree/master/NativeAdExample

</aside>

## Implementation Guide

To implement Native Ads in your app, follow these steps:

1. Create an Instance of **ApNative**.
    
    *Kotlin Example:*
    
    ```kotlin
    val apNative = ApNative("AP_PLACEMENT_ID")
    ```
    
    *JAVA Example:*
    
    ```java
    ApNative apNative = new ApNative("AP_PLACEMENT_ID");
    ```
    
2. Load the native ad by calling `apNative.loadAd()` 
    
    *Kotlin Example:*
    
    ```kotlin
    apNative.loadAd(context) {
        mNativeAd = it
        // It's best practice to always destroy the ad if the activity itself is destroyed.
        if(this@MainActivity.isDestroyed()) mNativeAd.destroy()
    }
    ```
    
    *JAVA Example:*
    
    ```java
    apNative.loadAd(context, it -> 
        mNativeAd = it;
        // It's best practice to always destroy the ad if the activity itself is destroyed.
        if(MainActivity.this.isDestroyed()) mNativeAd.destroy();
    });
    ```
    
3. Show Ad.
    
    > Displaying a native ad is the same as displaying an AdMob/AdManager Native Ad. AP Mobile SDK uses Native Ad Classes from Google Ad Manager SDK.
    > 
    
    **A.** Define a Native Layout with `com.google.android.gms.ads.nativead.NativeAdView` as Parent:
    
    NATIVE AD LAYOUT 
    
    ```xml
    <com.google.android.gms.ads.nativead.NativeAdView
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
        <LinearLayout
        android:orientation="vertical"
        ... >
            <LinearLayout
            android:orientation="horizontal"
            ... >
              <ImageView
               android:id="@+id/ad_app_icon"
               ... />
              <TextView
                android:id="@+id/ad_headline"
                ... />
             </LinearLayout>
    
             // Other assets such as image or media view, call to action, etc follow.
             ...
        </LinearLayout>
    </com.google.android.gms.ads.nativead.NativeAdView>
    ```
    
    **B.** Populate NativeAdView with NativeAd:
    
    *Kotlin Example:*
    
    ```kotlin
    apNative.loadAd(context) {
        // Assumes that your ad layout is in a file call native_ad_layout.xml in the res/layout folder
        val adView = layoutInflater.inflate(R.layout.native_ad_layout, null) as NativeAdView
        // This method sets the text, images and the native ad, etc into the ad view.
        populateNativeAdView(it, adView)
        // Assumes you have a placeholder FrameLayout in your View layout (with id ad_frame) where the ad is to be placed.
        ad_frame.removeAllViews()
        ad_frame.addView(adView)
    }
    ```
    
    *JAVA Example:*
    
    ```java
    apNative.loadAd(context, it -> 
        // Assumes that your ad layout is in a file call native_ad_layout.xml in the res/layout folder
        NativeAdView adView = (NativeAdView) getLayoutInflater().inflate(R.layout.native_ad_layout, null);
        // This method sets the text, images and the native ad, etc into the ad view.
        populateNativeAdView(it, adView);
        // Assumes you have a placeholder FrameLayout in your View layout (with id ad_frame) where the ad is to be placed.
        ad_frame.removeAllViews();
        ad_frame.addView(adView);
    });
    ```
    
    ## Supported Callbacks
    
    ```kotlin
    ApNativeListener {
    	// Called when the native ad is available.
    	override fun onNativeAd(nativeAd: NativeAd?) {}
    
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
