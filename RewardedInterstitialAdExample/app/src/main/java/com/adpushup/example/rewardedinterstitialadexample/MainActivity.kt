package com.adpushup.example.rewardedinterstitialadexample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adpushup.apmobilesdk.ApMobileSdk
import com.adpushup.apmobilesdk.interfaces.ApRewardedInterstitialListener
import com.adpushup.example.rewardedinterstitialadexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialising Ap Mobile SDK.
        ApMobileSdk.init(this, getString(R.string.ap_app_id))

        binding.showAd.setOnClickListener {
            // Show Ad.
            // NOTE: For Ad Optimisations, ApMobileSDK will will only show the ad at very specific time.
            // So, It is safe to call this function at multiple events.
            ApMobileSdk.showRewardedInterstitialAd(this, getString(R.string.ad_placement_id), object :
                ApRewardedInterstitialListener {
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
    }
}