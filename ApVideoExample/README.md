# ApVideo

ApVideo is a special video player that don’t need any content. It plays content from AdPushup’s Video Lib. It can be placed in your app even when your app doesn’t have any video content. It plays video ads along with its own content that increases overall revenue.

To integrate ApVideo into your app, You can use **ApVideo** of **ApMobileSDK**.
It uses **ApPlayer** internally for best performance.

You can use our demo app as a reference project.

<aside>
📎 Demo App: https://github.com/adpushup/ApMobileSDKDemo/tree/master/ApVideoExample

</aside>

# ApVideo Implementation Guide:

## Configure your app:

Before using **ApVideo**, Add the dependencies for the **Exo Player** to your module's app-level [Gradle](https://gradle.org/) file, normally `app/build.gradle`:

```groovy
dependencies {
	// Ap Mobile SDK + Ap Video + Google Ads SDK (If you are using Video Ads Only, you may skip ApMobileSdk Integration. Contact AdPushup for more into.)
  implementation 'com.adpushup:apmobilesdk:1.8.5'
  implementation 'com.adpushup.apmobilesdk:video:1.8.5'
  implementation 'com.google.android.gms:play-services-ads:23.2.0'

  // Media3 Exo Player
  implementation 'androidx.media3:media3-exoplayer:1.4.0'
  implementation 'androidx.media3:media3-ui:1.4.0'
  implementation 'androidx.media3:media3-exoplayer-hls:1.4.0'
}
```

## Implementation Guide:

To implement **ApExoPlayer** in your app, follow these steps:

1. Add `PlayerView` in your Layout Resource File:
    
    ```xml
    <androidx.media3.ui.PlayerView
        android:id="@+id/player"
        android:layout_width="360dp"
        android:layout_height="240dp"/>
    ```
    
2. Create and Initialise **ApVideo:**
    
    > **`AP_PLACEMENT_ID` is like an AdUnitID and it will be provided to you by AdPushup.**
    > 
    
    *Kotlin Example:*
    
    ```kotlin
    private var apVideo: ApVideo? = null
    
    ...
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    
    		...
            
        val playerView = findViewById<PlayerView>(R.id.player)
           
        apVideo = ApVideo("AP_PLACEMENT_ID", playerView)
         
    		...   
    }
    
    ...
    
    ```
    
    *JAVA Example:*
    
    ```java
    private ApVideo apVideo;
    
    ...
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
    		...
    
        PlayerView playerView = findViewById(R.id.player);
    
        apVideo = new ApVideo("AP_PLACEMENT_ID", playerView);
    
    		...
    }
    
    ...
    ```
    
3. Handle Activity Lifecycle Events: 
    
    *Kotlin Example:*
    
    ```kotlin
    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT > 23) {
            apVideo?.start(context)
        }
    }
    
    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT <= 23) {
            apVideo?.start(context)
        }
    }
    
    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT <= 23) {
            apVideo?.stop()
        }
    }
    
    override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT > 23) {
            apVideo?.stop()
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        apVideo?.destroy()
    }
    ```
    
    *JAVA Example:*
    
    ```java
    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT > 23) {
            apVideo.start(context);
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT <= 23) {
            apVideo.start(context);
        }
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT <= 23) {
            apVideo.stop();
        }
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT > 23) {
            apVideo.stop();
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        apVideo.destroy();
    }
    ```
    
4. (**Optional But Recommended)** Set Activity’s `configChanges` to `“keyboardHidden|orientation|screenSize”` to prevent recreation of **ApExoPlayer**. 
Modify Your Activity Tag in your app’s `AndroidManifest.xml` file as shown below:
    
    ```xml
    <activity 
    	android:name=".ExampleActivity"
    	android:configChanges="keyboardHidden|orientation|screenSize" />
    ```