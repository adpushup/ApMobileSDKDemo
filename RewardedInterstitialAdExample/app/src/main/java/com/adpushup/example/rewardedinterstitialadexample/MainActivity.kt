package com.adpushup.example.rewardedinterstitialadexample

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowMetrics
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adpushup.apmobilesdk.ApMobileSdk
import com.adpushup.apmobilesdk.interfaces.ApRewardedInterstitialListener
import com.adpushup.example.rewardedinterstitialadexample.databinding.ActivityMainBinding
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialising Ap Mobile SDK.
        ApMobileSdk.init(this, getString(R.string.ap_app_id))

        binding.showAd.setOnClickListener {
            // NOTE: For Ad Optimisations, ApMobileSDK will will only show the ad at very specific time.
            // So, You need to check if the ad is ready.
            // It is safe to call ApMobileSdk.isRewardedInterstitialAdReady() multiple times.
            if(ApMobileSdk.isRewardedInterstitialAdReady(this@MainActivity, getString(R.string.ad_placement_id))){
                // We need to show a timer dialog before showing the rewarded interstitial ads.
                val dialog = AdDialogFragment.newInstance(200, "coins")
                dialog.setAdDialogInteractionListener(
                    object : AdDialogFragment.AdDialogInteractionListener {
                        override fun onShowAd() {
                            // User wants to watch the ad. Show Ad.
                            ApMobileSdk.showRewardedInterstitialAd(this@MainActivity, getString(R.string.ad_placement_id), object : ApRewardedInterstitialListener{
                                override fun onUserEarnedReward(type: String?, amount: Int) {
                                    // Reward user in case when user watched full ad.
                                    Toast.makeText(this@MainActivity, "User Earned Reward: Amount: $amount of Type: $type", Toast.LENGTH_SHORT).show()
                                }
                                override fun onComplete() {
                                    // You don't have to worry about ad events. After calling ApMobileSdk.showRewardedInterstitialAd(),
                                    // resume your work here.
                                    Toast.makeText(this@MainActivity, "Ad Completed.", Toast.LENGTH_SHORT).show()
                                }
                            })
                        }
                        override fun onCancelAd() {
                            Log.d("MainActivity", "The rewarded interstitial ad was skipped before it starts.")
                        }
                    }
                )
                dialog.show(supportFragmentManager, "AdDialogFragment")
            } else {
                // Ad Not Ready Yet
                Toast.makeText(this@MainActivity, "Ad Not Ready Yet", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Resume the SDK on Activity Resume. This will ensure the SDK preloads
        // the ads and is ready to show ads when required.
        ApMobileSdk.resume(this)
    }
}