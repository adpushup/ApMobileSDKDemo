# ApStory Ads Demo

Ap Story Ads are a visually immersive ad format designed for integration between story-style or reel-like content. Ideal for apps with swipeable, full-screen media experiences, Ap Story Ads appear seamlessly between user-generated content and support rich media creatives. These ads can be skipped or interacted with, ensuring a non-intrusive yet engaging monetization experience.

You can use our demo app as a reference project.

📎 GitHub: [https://github.com/adpushup/ApMobileSDKDemo/tree/master/ApStoryAdExample](https://github.com/adpushup/ApMobileSDKDemo/tree/master/ApStoryAdExample)

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

## Extras

1. **Preloading Multiple Ads:**
    
    To improve ad availability and avoid placeholders, preload multiple ads in advance.
    
    ```kotlin
    fun preloadAds(context: Context, adPlacementIds: List<String>, callback: (List<ApStory>) -> Unit) {
        val adStories = mutableListOf<ApStory>()
        var processedCount = 0
    
        for (placementId in adPlacementIds) {
            val apStory = ApStory(placementId)
            apStory.loadAd(context) { success ->
                 processedCount++
                 if (success) {
                    adStories.add(apStory)
                 }
                 if (processedCount == adPlacementIds.size) {
                    callback(adStories)
                 }
            })
        }
    }
    ```
    
2. **Scale Type for Ad Media View:**
    
    You can define how the media content should scale within the ad’s media view.
    
    ```kotlin
    apStory.setMediaScaleType(ImageView.ScaleType.CENTER_CROP)
    ```
    
3. **Checking Ad Load Status:**
    
    To check if an ad has loaded, use the `isAdLoaded()` method.
    
    ```kotlin
    val isLoaded: Boolean = apStory.isAdLoaded()
    ```
    
4. **Checking Ad Shown Status:**
    
    Calling `getAd()` sets the Ad Shown status to **true**.
    If an ad has already been shown, `getAd()` will return null.
    
    ```kotlin
    val isUsed: Boolean = apStory.isAdShown()
    ```
    
5. **Checking Ad Destroy Status:**
    
    When `destroy()` is called, ad resources are cleaned up, and `getAd()` will return `null`.
    
    ```kotlin
    val isDestroyed: Boolean = apStory.isDestroyed()
    ```