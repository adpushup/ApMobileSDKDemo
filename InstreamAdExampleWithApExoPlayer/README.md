# In-Stream Ads

In-Stream ads are the multimedia ads that you can insert between video content of your app.

To integrate In-stream ads into your app, You can use **ApExoPlayer** of **ApMobileSDK**.
It is a simplified version of Exo Player that has built in support for IMA Ads.

<aside>
💡 Note: ApMobileSDK also support custom video players. It can generate VAST Tags in realtime that you can use in your custom player. Check out the Last section of this doc for more info.

</aside>

You can use our demo app as a reference project.

<aside>
📎 Demo App: https://github.com/adpushup/ApMobileSDKDemo/tree/master/InstreamAdExampleWithApExoPlayer

</aside>

# ApExoPlayer Implementation Guide:

## Configure your app:

Before using **ApExoPlayer**, Add the dependencies for the **Exo Player** to your module's app-level [Gradle](https://gradle.org/) file, normally `app/build.gradle`:

```groovy
dependencies {
	// Ap Mobile SDK + Google Ads SDK
  implementation 'com.adpushup:apmobilesdk:1.1.0'
  implementation 'com.google.android.gms:play-services-ads:21.5.0'

  // Exo Player
  implementation 'com.google.android.exoplayer:exoplayer-hls:2.18.4'
  implementation 'com.google.android.exoplayer:exoplayer-ui:2.18.4'
  implementation 'com.google.android.exoplayer:exoplayer-core:2.18.4'
}
```

## Implementation Guide:

To implement **ApExoPlayer** in your app, follow these steps:

1. Add `StyledPlayerView` in your Layout Resource File:
    
    ```xml
    <com.google.android.exoplayer2.ui.StyledPlayerView
        android:id="@+id/player"
        android:layout_width="360dp"
        android:layout_height="240dp"/>
    ```
    
2. Create and Initialise **ApExoPlayer.** 
    
    > **`AP_PLACEMENT_ID` is like an AdUnitID and it will be provided to you by AdPushup.**
    > 
    
    *Kotlin Example:*
    
    ```kotlin
    private var apExoPlayer: ApExoPlayer? = null
    
    ...
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    
    		...
            
        val playerView = findViewById<StyledPlayerView>(R.id.player)
        val uri = Uri.parse("https://YOUR_VIDEO_URL")
           
        apExoPlayer = ApExoPlayer("AP_PLACEMENT_ID", playerView)
        apExoPlayer!!.init(this, uri)
         
    		...   
    }
    
    ...
    
    ```
    
    *JAVA Example:*
    
    ```java
    private ApExoPlayer apExoPlayer;
    
    ...
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
    		...
    
        StyledPlayerView playerView = findViewById(R.id.player);
        Uri uri = Uri.parse("https://YOUR_VIDEO_URL.mp4");
    
    		// Create new ApExoPlayer.
        apExoPlayer = new ApExoPlayer("AP_PLACEMENT_ID", playerView);
        apExoPlayer.init(this, uri);
    
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
            apExoPlayer?.start()
        }
    }
    
    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT <= 23) {
            apExoPlayer?.start()
        }
    }
    
    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT <= 23) {
            apExoPlayer?.stop()
        }
    }
    
    override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT > 23) {
            apExoPlayer?.stop()
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        apExoPlayer?.destroy()
    }
    ```
    
    *JAVA Example:*
    
    ```java
    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT > 23) {
            apExoPlayer.start();
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT <= 23) {
            apExoPlayer.start();
        }
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT <= 23) {
            apExoPlayer.stop();
        }
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT > 23) {
            apExoPlayer.stop();
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        apExoPlayer.destroy();
    }
    ```
    
4. (**Optional But Recommended)** Set Activity’s `configChanges` to `“keyboardHidden|orientation|screenSize”` to prevent recreation of **ApExoPlayer**. 
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
    apExoPlayer?.playVideo()
    ```
    
    *JAVA Example:*
    
    ```java
    apExoPlayer.playVideo();
    ```
    
- **Pause Video:**
    
    Pause Video In the Player.
    
    *Kotlin Example:*
    
    ```kotlin
    apExoPlayer?.pauseVideo()
    ```
    
    *JAVA Example:*
    
    ```java
    apExoPlayer.pauseVideo();
    ```
    
- **Is Video Playing:**
    
    Check if the video is currently playing in the player.
    
    *Kotlin Example:*
    
    ```kotlin
    val isVideoPlaying : Boolean? = apExoPlayer?.isPlaying()
    ```
    
    *JAVA Example:*
    
    ```java
    boolean isVideoPlaying = apExoPlayer.isPlaying();
    ```
    
- **Add Full Screen Button:**
    
    Adds a full screen button to the player.
    
    *Kotlin Example:*
    
    ```kotlin
    apExoPlayer?.setFullScreenListener(this) {
        // Do Something when full screen mode changes.
    }
    ```
    
    *JAVA Example:*
    
    ```java
    apExoPlayer.setFullScreenListener(this, isFullScreen -> {
    		// Do Something when full screen mode changes.
    });
    ```
    

### Playing Mode Controls:

- **AutoPlay**
    
    Enable Auto Play Video As soon as Player gets ready. (Disabled by Default)
    
    *Kotlin Example:*
    
    ```kotlin
    apExoPlayer?.setAutoPlay(true)
    ```
    
    *JAVA Example:*
    
    ```java
    apExoPlayer.setAutoPlay(true);
    ```
    
- **Shuffle Mode**
    
    Enable Auto Play Video As soon as Player gets ready. (Disabled by Default)
    
    *Kotlin Example:*
    
    ```kotlin
    apExoPlayer?.setShuffleModeEnabled(true) 
    ```
    
    *JAVA Example:*
    
    ```java
    apExoPlayer.setShuffleModeEnabled(true);
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
    apExoPlayer?.setRepeatModeAll()
    // Repeat Mode One - loops current video of the playlist.
    apExoPlayer?.setRepeatModeOne()
    // Repeat Mode Off - doesn't repeat videos. (Default Setting)
    apExoPlayer?.setRepeatModeOff()
    ```
    
    *JAVA Example:*
    
    ```java
    // Repeat Mode All - loops all videos of the playlist.
    apExoPlayer.setRepeatModeAll();
    // Repeat Mode One - loops current video of the playlist.
    apExoPlayer.setRepeatModeOne();
    // Repeat Mode Off - doesn't repeat videos. (Default Setting)
    apExoPlayer.setRepeatModeOff();
    ```
    

### Playlist Controls:

- **Add Media**
    
    Adds Media to the playlist.
    
    *Kotlin Example:*
    
    ```kotlin
    apExoPlayer?.addMediaToPlaylist(Uri.parse("https://YOUR_VIDEO_URL.mp4"))
    ```
    
    *JAVA Example:*
    
    ```java
    apExoPlayer.addMediaToPlaylist(Uri.parse("https://YOUR_VIDEO_URL.mp4"));
    ```
    
- **Add Media At an Index**
    
    Adds Media to the playlist at a specific position by calling: `player.addMediaToPlaylist(index, “mediauri.mp4”)`
    
    Example to add a video at an index of 16:
    
    *Kotlin Example:*
    
    ```kotlin
    apExoPlayer?.addMediaToPlaylist(16, Uri.parse("https://YOUR_VIDEO_URL.mp4"))
    ```
    
    *JAVA Example:*
    
    ```java
    apExoPlayer.addMediaToPlaylist(16, Uri.parse("https://YOUR_VIDEO_URL.mp4"));
    ```
    
- **Move Media In Playlist**
    
    Move Media in the playlist by calling `player.moveMediaInPlaylist(initialPosition, finalPosition)`
    
    Example to move media from 8th Position to 4th Position:
    
    *Kotlin Example:*
    
    ```kotlin
    apExoPlayer?.moveMediaInPlaylist(8, 4)
    ```
    
    *JAVA Example:*
    
    ```java
    apExoPlayer.moveMediaInPlaylist(8, 4);
    ```
    
- **Remove Media From Playlist**
    
    Remove media from any index of playlist by calling: `removeMediaInPlaylist(index)` 
    
    Example to remove media from 5th position:
    
    *Kotlin Example:*
    
    ```kotlin
    apExoPlayer?.removeMediaInPlaylist(5)
    ```
    
    *JAVA Example:*
    
    ```java
    apExoPlayer.removeMediaInPlaylist(5);
    ```
    
- **Clear Playlist**
    
    Remove all media items from playlist.
    
    *Kotlin Example:*
    
    ```kotlin
    apExoPlayer?.clearPlaylist()
    ```
    
    *JAVA Example:*
    
    ```java
    apExoPlayer.clearPlaylist();
    ```
    
- **Get Playlist Length**
    
    Get total number of items in the playlist.
    
    *Kotlin Example:*
    
    ```kotlin
    val length : Int? = apExoPlayer?.getPlaylistLength()
    ```
    
    *JAVA Example:*
    
    ```java
    int length = apExoPlayer.getPlaylistLength();
    ```
    
- **Get Current Media**
    
    Get current playing media Uri.
    
    *Kotlin Example:*
    
    ```kotlin
    val mediaUri = apExoPlayer?.getCurrentMedia()
    ```
    
    *JAVA Example:*
    
    ```java
    Uri mediaUri = apExoPlayer.getCurrentMedia();
    ```
    
- **Get Media At an Index**
    
    Get Media Uri of Media at particular Index.
    
    Example of getting Media Uri of a media item at an 5th index:
    
    *Kotlin Example:*
    
    ```kotlin
    val mediaUri = apExoPlayer?.getMediaAt(5)
    ```
    
    *JAVA Example:*
    
    ```java
    Uri mediaUri = apExoPlayer.getMediaAt(5);
    ```
    
- **Has Next Media**
    
    Checks if playlist has next media item to play.
    
    *Kotlin Example:*
    
    ```kotlin
    val hasNextMediaItem : Boolean? = apExoPlayer?.hasNextMedia()
    ```
    
    *JAVA Example:*
    
    ```java
    boolean hasNextMediaItem = apExoPlayer.hasNextMedia();
    ```
    
- **Get Next Media Index**
    
    Get the next media index from the playlist.
    
    *Kotlin Example:*
    
    ```kotlin
    val nextMediaIndex : Int? = apExoPlayer?.getNextMediaIndex()
    ```
    
    *JAVA Example:*
    
    ```java
    int nextMediaIndex = apExoPlayer.getNextMediaIndex();
    ```
    

### Advance Controls:

- **Switch Player View**
    
    When you want to change player view from one screen to another without interrupting the video, You may use `apExoPlayer.switchPlayer()` method.
    
    *Kotlin Example:*
    
    ```kotlin
    // secondStyledPlayerView is your the new Player View.
    apExoPlayer?.switchPlayer(secondStyledPlayerView)
    ```
    
    *JAVA Example:*
    
    ```java
    // secondStyledPlayerView is your the new Player View.
    apExoPlayer.switchPlayer(secondStyledPlayerView);
    ```
    
- **Switch back to Original Player**
    
    When you switched your player using `switchPlayer` method and want to switch back to original player, You can use `apExoPlayer.placeOriginalPlayer()` method.
    
    *Kotlin Example:*
    
    ```kotlin
    apExoPlayer?.placeOriginalPlayer()
    ```
    
    *JAVA Example:*
    
    ```java
    apExoPlayer.placeOriginalPlayer();
    ```
    

---

# Generate VAST Tag for custom Players.

When you are using an custom video player and integrating **ApExoPlayer** isn’t possible, you can use Ap Mobile SDK’s `generateVastTag()` method. This generate VAST Tag for your player in real time. 

Example: 

*Kotlin Example:*

```kotlin
val vastTag: Uri? = ApMobileSdk.generateVastTag(context, "AP_PLACEMENT_ID")
```

*JAVA Example:*

```java
Uri vastTag = ApMobileSdk.generateVastTag(context, "AP_PLACEMENT_ID");
```