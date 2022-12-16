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
            ApMobileSdk.showRewardedInterstitialAd(this, getString(R.string.ad_placement_id), object :
                ApRewardedInterstitialListener {
                override fun onUserEarnedReward(type: String?, amount: Int) {
                    Toast.makeText(this@MainActivity, "User Earned Reward: Amount: $amount of Type: $type", Toast.LENGTH_SHORT).show()
                }
                override fun onComplete() {
                    Toast.makeText(this@MainActivity, "Ad Completed.", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}