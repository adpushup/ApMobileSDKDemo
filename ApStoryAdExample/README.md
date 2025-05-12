# ApStory Ads Demo

This demo app shows how to integrate **ApStory** native ads into your Android application in a “stories” or “reels/shorts” format. It uses the [SHTS StoriesProgressView](https://github.com/shts/StoriesProgressView) library to drive a story-style UI and demonstrates how to insert ApStory ads every two users (or “stories”).

---

## Demo App

📎 GitHub: [https://github.com/adpushup/ApMobileSDKDemo/tree/master/ApStoryAdExample](https://github.com/adpushup/ApMobileSDKDemo/tree/master/ApStoryAdExample)

---

## Getting Started

1. **Clone the repo**
    
    ```bash
    git clone https://github.com/your-org/ApStoryAdsDemo.git
    cd ApStoryAdsDemo
    
    ```
    
2. **Open in Android Studio**
3. **Set your AD_PLACEMENT_ID** in `strings.xml`:
    
    ```xml
    <string name="ad_placement_id">YOUR_AP_STORY_PLACEMENT_ID</string>
    
    ```
    
4. **Run on a device or emulator**.

---

## Implementation Guide

To implement Ap Story Ad in your app, follow these steps:

## 1. Loading Ads

To load an ad, use the `loadAd` method:

```kotlin
val apStory = ApStory("AP_PLACEMENT_ID")
apStory.loadAd(context, object : ApStoryListener {
    override fun onComplete(success: Boolean) {
        if (success) {
            // Ad successfully loaded
        }
    }
    override fun onError(code: Int, message: String) {
        // Handle error
    }
})

```

## 2. Retrieving and Displaying Ads

### With Click-to-Dismiss and Swipe Gesture Control

> Best for Story-style integration
> 
- **Enable/Disable** Mapping of Ad’s Click-To-Action (CTA) to Swipe Up gesture.
- **Enable/Disable** Click To Dismiss Action For Ad.
- **Disables** Click-To-Action (CTA) on the Ad’s Media View itself.

```kotlin
val adView = apStory.getAd(
context, 
isSwipeUpGestureEnabled = true, 
isClickDismissEnabled = true
) {
    // Callback when ad is dismissed. You can move to next story.
}
adView?.let { parentLayout.addView(it) }
```

### Without Click-to-Dismiss or Swipe Gesture Control

> Best for Reels/Shorts-style integration
> 
- **Disables** Mapping of Ad’s CTA to Swipe Up gesture.
- **Disables** Click To Dismiss Action For Ad.
- **Enables** CTA on the Ad’s Media View itself.

```kotlin
val adView = apStory.getAd(context)
adView?.let { parentLayout.addView(it) }
```

## 3. Cleaning Up Resources

Destroy the ad when it is no longer needed:

```kotlin
apStory.destroy()
```

---

## License

This demo is provided as-is under the MIT license. See [LICENSE](https://chatgpt.com/c/LICENSE) for details.