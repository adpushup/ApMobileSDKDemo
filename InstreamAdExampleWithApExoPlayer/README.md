# In-Stream Ads

In-Stream ads are the multimedia ads that you can insert between video content of your app.

To integrate In-stream ads into your app, You can use **ApPlayer** of **ApMobileSDK**.
It is a simplified version of Media3 Exo Player that has built in support for IMA Ads.

<aside>
💡 Note: ApMobileSDK also support custom video players. It can generate VAST Tags in realtime that you can use in your custom player. Check out the Last section of this doc for more info.

</aside>

You can use our demo app as a reference project.

<aside>
📎 Demo App: https://github.com/adpushup/ApMobileSDKDemo/tree/master/InstreamAdExampleWithApExoPlayer

</aside>

# ApPlayer Implementation Guide:

## Configure your app:

Before using **ApPlayer**, Add the dependencies for the **Media3 Exo Player** to your module's app-level [Gradle](https://gradle.org/) file, normally `app/build.gradle`:

```groovy
dependencies {
	// Ap Mobile SDK + Ap Video + Google Ads SDK (If you are using Video Ads Only, you may skip ApMobileSdk Integration. Contact AdPushup for more into.)
  implementation 'com.adpushup:apmobilesdk:1.9.0'
  implementation 'com.adpushup.apmobilesdk:video:1.9.0'
  implementation 'com.google.android.gms:play-services-ads:23.4.0'

  // Media3 Exo Player
  implementation 'androidx.media3:media3-exoplayer:1.4.1'
  implementation 'androidx.media3:media3-ui:1.4.1'
  implementation 'androidx.media3:media3-exoplayer-hls:1.4.1'
}
```

Note: If you only use Instream Video Ads in your app, you can skip adding `apmobilesdk` and `play-services-ads` dependencies. Refer to the Example App or contact the Adpushup Team for more info.


## Implementation Guide:

To implement **ApPlayer** in your app, follow these steps:

1. Add `StyledPlayerView` in your Layout Resource File:
    
    ```xml
    <androidx.media3.ui.PlayerView
        android:id="@+id/player"
        android:layout_width="360dp"
        android:layout_height="240dp"/>
    ```
    
2. Create and Initialise **ApPlayer.** 
    
    > **`AP_PLACEMENT_ID` is like an AdUnitID and it will be provided to you by AdPushup.**
    > 
    
    *Kotlin Example:*
    
    ```kotlin
    private var apPlayer: ApPlayer? = null
    
    ...
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    
    		...
            
        val playerView = findViewById<PlayerView>(R.id.player)
        val uri = Uri.parse("https://YOUR_VIDEO_URL")
           
        apPlayer = ApPlayer("AP_PLACEMENT_ID", playerView)
        apPlayer?.init(this, uri)
         
    		...   
    }
    
    ...
    
    ```
    
    *JAVA Example:*
    
    ```java
    private ApPlayer apPlayer;
    
    ...
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
    		...
    
        PlayerView playerView = findViewById(R.id.player);
        Uri uri = Uri.parse("https://YOUR_VIDEO_URL.mp4");
    
    		// Create new ApPlayer.
        apPlayer = new ApPlayer("AP_PLACEMENT_ID", playerView);
        apPlayer.init(this, uri);
    
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
            apPlayer?.start()
        }
    }
    
    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT <= 23) {
            apPlayer?.start()
        }
    }
    
    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT <= 23) {
            apPlayer?.stop()
        }
    }
    
    override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT > 23) {
            apPlayer?.stop()
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        apPlayer?.destroy()
    }
    ```
    
    *JAVA Example:*
    
    ```java
    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT > 23) {
            apPlayer.start();
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT <= 23) {
            apPlayer.start();
        }
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT <= 23) {
            apPlayer.stop();
        }
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT > 23) {
            apPlayer.stop();
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        apPlayer.destroy();
    }
    ```
    
4. (**Optional But Recommended)** Set Activity’s `configChanges` to `“keyboardHidden|orientation|screenSize”` to prevent recreation of **ApPlayer**. 
Modify Your Activity Tag in your app’s `AndroidManifest.xml` file as shown below:
    
    ```xml
    <activity 
    	android:name=".ExampleActivity"
    	android:configChanges="keyboardHidden|orientation|screenSize" />
    ```
    

## Additional Methods:

### Basic Player Controls:

- **Play Video:**
    
    Play Video In the Player.
    
    *Kotlin Example:*
    
    ```kotlin
    apPlayer?.playVideo()
    ```
    
    *JAVA Example:*
    
    ```java
    apPlayer.playVideo();
    ```
    
- **Pause Video:**
    
    Pause Video In the Player.
    
    *Kotlin Example:*
    
    ```kotlin
    apPlayer?.pauseVideo()
    ```
    
    *JAVA Example:*
    
    ```java
    apPlayer.pauseVideo();
    ```
    
- **Is Video Playing:**
    
    Check if the video is currently playing in the player.
    
    *Kotlin Example:*
    
    ```kotlin
    val isVideoPlaying : Boolean? = apPlayer?.isPlaying()
    ```
    
    *JAVA Example:*
    
    ```java
    boolean isVideoPlaying = apPlayer.isPlaying();
    ```
    
- **Add Full Screen Button:**
    
    Adds a full screen button to the player.
    
    *Kotlin Example:*
    
    ```kotlin
    apPlayer?.setFullScreenListener(this) {
        // Do Something when full screen mode changes.
    }
    ```
    
    *JAVA Example:*
    
    ```java
    apPlayer.setFullScreenListener(this, isFullScreen -> {
    		// Do Something when full screen mode changes.
    });
    ```
    

### Playing Mode Controls:

- **AutoPlay**
    
    Enable Auto Play Video As soon as Player gets ready. (Disabled by Default)
    
    *Kotlin Example:*
    
    ```kotlin
    apPlayer?.setAutoPlay(true)
    ```
    
    *JAVA Example:*
    
    ```java
    apPlayer.setAutoPlay(true);
    ```
    
- **Shuffle Mode**
    
    Enable Auto Play Video As soon as Player gets ready. (Disabled by Default)
    
    *Kotlin Example:*
    
    ```kotlin
    apPlayer?.setShuffleModeEnabled(true) 
    ```
    
    *JAVA Example:*
    
    ```java
    apPlayer.setShuffleModeEnabled(true);
    ```
    
- **Repeat Mode**
    
    You can set Repeat Mode of player to the following values:
    
    `RepeatModeAll` - loops all videos of the playlist.
    
    `RepeatModeOne` - loops current video of the playlist.
    
    `RepeatModeOff` - doesn't repeat videos. (Default Setting)
    
    by calling their respective functions:
    
    *Kotlin Example:*
    
    ```kotlin
    // Repeat Mode All - loops all videos of the playlist.
    apPlayer?.setRepeatModeAll()
    // Repeat Mode One - loops current video of the playlist.
    apPlayer?.setRepeatModeOne()
    // Repeat Mode Off - doesn't repeat videos. (Default Setting)
    apPlayer?.setRepeatModeOff()
    ```
    
    *JAVA Example:*
    
    ```java
    // Repeat Mode All - loops all videos of the playlist.
    apPlayer.setRepeatModeAll();
    // Repeat Mode One - loops current video of the playlist.
    apPlayer.setRepeatModeOne();
    // Repeat Mode Off - doesn't repeat videos. (Default Setting)
    apPlayer.setRepeatModeOff();
    ```
    

### Playlist Controls:

- **Add Media**
    
    Adds Media to the playlist.
    
    *Kotlin Example:*
    
    ```kotlin
    apPlayer?.addMediaToPlaylist(Uri.parse("https://YOUR_VIDEO_URL.mp4"))
    ```
    
    *JAVA Example:*
    
    ```java
    apPlayer.addMediaToPlaylist(Uri.parse("https://YOUR_VIDEO_URL.mp4"));
    ```
    
- **Add Media At an Index**
    
    Adds Media to the playlist at a specific position by calling: `player.addMediaToPlaylist(index, “mediauri.mp4”)`
    
    Example to add a video at an index of 16:
    
    *Kotlin Example:*
    
    ```kotlin
    apPlayer?.addMediaToPlaylist(16, Uri.parse("https://YOUR_VIDEO_URL.mp4"))
    ```
    
    *JAVA Example:*
    
    ```java
    apPlayer.addMediaToPlaylist(16, Uri.parse("https://YOUR_VIDEO_URL.mp4"));
    ```
    
- **Move Media In Playlist**
    
    Move Media in the playlist by calling `player.moveMediaInPlaylist(initialPosition, finalPosition)`
    
    Example to move media from 8th Position to 4th Position:
    
    *Kotlin Example:*
    
    ```kotlin
    apPlayer?.moveMediaInPlaylist(8, 4)
    ```
    
    *JAVA Example:*
    
    ```java
    apPlayer.moveMediaInPlaylist(8, 4);
    ```
    
- **Remove Media From Playlist**
    
    Remove media from any index of playlist by calling: `removeMediaInPlaylist(index)` 
    
    Example to remove media from 5th position:
    
    *Kotlin Example:*
    
    ```kotlin
    apPlayer?.removeMediaInPlaylist(5)
    ```
    
    *JAVA Example:*
    
    ```java
    apPlayer.removeMediaInPlaylist(5);
    ```
    
- **Clear Playlist**
    
    Remove all media items from playlist.
    
    *Kotlin Example:*
    
    ```kotlin
    apPlayer?.clearPlaylist()
    ```
    
    *JAVA Example:*
    
    ```java
    apPlayer.clearPlaylist();
    ```
    
- **Get Playlist Length**
    
    Get total number of items in the playlist.
    
    *Kotlin Example:*
    
    ```kotlin
    val length : Int? = apPlayer?.getPlaylistLength()
    ```
    
    *JAVA Example:*
    
    ```java
    int length = apPlayer.getPlaylistLength();
    ```
    
- **Get Current Media**
    
    Get current playing media Uri.
    
    *Kotlin Example:*
    
    ```kotlin
    val mediaUri = apPlayer?.getCurrentMedia()
    ```
    
    *JAVA Example:*
    
    ```java
    Uri mediaUri = apPlayer.getCurrentMedia();
    ```
    
- **Get Media At an Index**
    
    Get Media Uri of Media at particular Index.
    
    Example of getting Media Uri of a media item at an 5th index:
    
    *Kotlin Example:*
    
    ```kotlin
    val mediaUri = apPlayer?.getMediaAt(5)
    ```
    
    *JAVA Example:*
    
    ```java
    Uri mediaUri = apPlayer.getMediaAt(5);
    ```
    
- **Has Next Media**
    
    Checks if playlist has next media item to play.
    
    *Kotlin Example:*
    
    ```kotlin
    val hasNextMediaItem : Boolean? = apPlayer?.hasNextMedia()
    ```
    
    *JAVA Example:*
    
    ```java
    boolean hasNextMediaItem = apPlayer.hasNextMedia();
    ```
    
- **Get Next Media Index**
    
    Get the next media index from the playlist.
    
    *Kotlin Example:*
    
    ```kotlin
    val nextMediaIndex : Int? = apPlayer?.getNextMediaIndex()
    ```
    
    *JAVA Example:*
    
    ```java
    int nextMediaIndex = apPlayer.getNextMediaIndex();
    ```
    

### Advance Controls:

- **Switch Player View**
    
    When you want to change player view from one screen to another without interrupting the video, You may use `apPlayer.switchPlayer()` method.
    
    *Kotlin Example:*
    
    ```kotlin
    // secondStyledPlayerView is your the new Player View.
    apPlayer?.switchPlayer(secondStyledPlayerView)
    ```
    
    *JAVA Example:*
    
    ```java
    // secondStyledPlayerView is your the new Player View.
    apPlayer.switchPlayer(secondStyledPlayerView);
    ```
    
- **Switch back to Original Player**
    
    When you switched your player using `switchPlayer` method and want to switch back to original player, You can use `apPlayer.placeOriginalPlayer()` method.
    
    *Kotlin Example:*
    
    ```kotlin
    apPlayer?.placeOriginalPlayer()
    ```
    
    *JAVA Example:*
    
    ```java
    apPlayer.placeOriginalPlayer();
    ```
    

---

# Generate VAST Tag for custom Players.

When you are using an custom video player and integrating **ApPlayer** isn’t possible, you can use Ap Mobile SDK’s `generateVastTag()` method. This generate VAST Tag for your player in real time. 

Example: 

*Kotlin Example:*

```kotlin
val vastTag: Uri? = ApMobileSdk.generateVastTag(context, "AP_PLACEMENT_ID")
```

*JAVA Example:*

```java
Uri vastTag = ApMobileSdk.generateVastTag(context, "AP_PLACEMENT_ID");
```