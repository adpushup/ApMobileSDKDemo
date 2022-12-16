# Interstitial Ads

Interstitial ads are full-screen ads that cover the interface of their host app. They're typically displayed at natural transition points in the flow of an app, such as between activities or during the pause between levels in a game. When an app shows an interstitial ad, the user has the choice to either tap on the ad and continue to its destination or close it and return to the app.

You can use our demo app as a reference project.

<aside>
📎 Demo App: https://github.com/adpushup/ApMobileSDKDemo/tree/master/InterstitialAdExample

</aside>

## Implementation Guide

To implement Interstitial Ads in your app, follow these steps:

Call `ApMobileSdk.showInterstitialAd()`

*Kotlin Example:*

```kotlin
ApMobileSdk.showInterstitialAd(this@MainActivity, "AP_PLACEMENT_ID") {
		// Do your work after the interstitial ad is closed.
}
```

*JAVA Example:*

```java
ApMobileSdk.showInterstitialAd(MainActivity.this, "AP_PLACEMENT_ID", () -> {
		// Do your work after the interstitial ad is closed.
});
```

## Supported Callbacks

```kotlin
ApInterstitialListener {
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