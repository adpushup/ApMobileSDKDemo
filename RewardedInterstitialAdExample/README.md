# Rewarded Interstitial Ads

[Rewarded interstitial](https://support.google.com/admanager/answer/7386053) is a type of incentivized ad format that allows you to offer rewards for ads that appear automatically during natural app transitions. Unlike rewarded ads, users aren't required to opt-in to view a rewarded interstitial.

You can use our demo app as a reference project.

<aside>
📎 Demo App: https://github.com/adpushup/ApMobileSDKDemo/tree/master/RewardedInterstitialAdExample

</aside>

## Implementation Guide

To implement Rewarded Ads in your app, follow these steps:

1. Check If Rewarded Interstitial Ad is ready.
  
    *Kotlin Example:*
    
    ```kotlin
    if(ApMobileSdk.isRewardedInterstitialAdReady("AP_PLACEMENT_ID")){
    	// Ad is available. You can show a timer before showing the Rewarded Interstitial Ad.
    	...
    }
    ```
    
    *JAVA Example:*
    
    ```java
    if(ApMobileSdk.isRewardedInterstitialAdReady("AP_PLACEMENT_ID")){
    	// Ad is available. You can show a timer before showing the Rewarded Interstitial Ad.
    	...
    }
    ```

2. Show the ad to user if user want to see the ad within timer limits.

	Just Call `ApMobileSdk.showRewardedInterstitialAd()` 

	*Kotlin Example:*

	```kotlin
	ApMobileSdk.showRewardedInterstitialAd(this@MainActivity, "AP_PLACEMENT_ID", object : ApRewardedListener{
		override fun onUserEarnedReward(type: String?, amount: Int) {
			// Reward User
		}

		override fun onComplete() {
			// Do your work after the rewarded ad is closed.
		}
	})
	```

	*JAVA Example:*

	```java
	ApMobileSdk.showRewardedInterstitialAd(MainActivity.this, "AP_PLACEMENT_ID", new ApRewardedListener() {
		@Override
		public void onUserEarnedReward(String type, int amount) {
			// Reward User
		}

		@Override
		public void onComplete() {
			// Do your work after the rewarded ad is closed.
		}
	});
	```

## Supported Callbacks

```kotlin
ApRewardedInterstitialListener {
	// Called when user earned reward.
	override fun onUserEarnedReward(type: String?, reward: Int) {}

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

## Additional Tips:
Ap Mobile SDK supports controlling full-screen ad behaviour via Ad Caping like attempt, time and quantity remotely in real-time. If you have any specific requirements, reach out to the AdPushup Team.