# Rewarded Ads

Ads that reward users for watching short videos and interacting with playable ads and surveys. 

You should ask the user to view a rewarded ad for a reward. If you want to show rewarded ads between the normal flow of the screens, Please use Rewarded Interstitial Ads instead.

You can use our demo app as a reference project.

<aside>
📎 Demo App: https://github.com/adpushup/ApMobileSDKDemo/tree/master/RewardedAdExample

</aside>

## Implementation Guide

To implement Rewarded Ads in your app, follow these steps:

1. Check If Rewarded Ad is ready.
    
    *Kotlin Example:*
    
    ```kotlin
    if(ApMobileSdk.isRewardedAdReady("AP_PLACEMENT_ID")){
    		// Ad is available. Ask user to view an ad for some reward.
    		...
    }
    ```
    
    *JAVA Example:*
    
    ```java
    if(ApMobileSdk.isRewardedAdReady("AP_PLACEMENT_ID")){
    		// Ad is available. Ask user to view an ad for some reward.
    		...
    }
    ```
    
2. Ask User if they are interested in viewing a Rewarded Ad.
    
    *Kotlin Example:*
    
    ```kotlin
    MaterialAlertDialogBuilder(this@MainActivity)
    		.setTitle("Get 10 Coins")
    		.setMessage("Watch a video to earn 10 coins.")
    		.setPositiveButton("YES") { _, i ->
    				// User want to watch an ad. Show the Ad.
    				...
    		}
    		.setNegativeButton("NO") { _, i ->
    				// User doesn't want to watch an ad.
    		}.show()
    ```
    
    *JAVA Example:*
    
    ```java
    new MaterialAlertDialogBuilder(MainActivity.this)
    		.setTitle("Get 10 Coins")
    		.setMessage("Watch a video to earn 10 coins.")
    		.setPositiveButton("YES", (dialog, which) -> {
    				// User want to watch an ad. Show the Ad.
    				...
    		}).setNegativeButton("NO", (dialog, which) -> {
    				// User doesn't want to watch an ad. Skip Showing Ad.
    		}).show();
    ```
    
3. Show Rewarded Ad.
    
    Just call `ApMobileSdk.showRewardedAd()` to show the ad.
    
    *Kotlin Example:*
    
    ```kotlin
    ApMobileSdk.showRewardedAd(this@MainActivity, "AP_PLACEMENT_ID", object : ApRewardedListener{
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
    ApMobileSdk.showRewardedAd(MainActivity.this, "AP_PLACEMENT_ID", new ApRewardedListener() {
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
ApRewardedListener {
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