package com.adpushup.example.interstitialadexample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adpushup.apmobilesdk.ApMobileSdk
import com.adpushup.example.interstitialadexample.databinding.ActivityMainBinding

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
            ApMobileSdk.showInterstitialAd(this, getString(R.string.ad_placement_id)) {
                // You don't have to worry about ad events. After calling ApMobileSdk.showInterstitialAd(),
                // resume your work here.
                Toast.makeText(this@MainActivity, "Ad Completed.", Toast.LENGTH_SHORT).show()
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