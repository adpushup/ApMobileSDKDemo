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
            ApMobileSdk.showInterstitialAd(this, getString(R.string.ad_placement_id)) {
                Toast.makeText(this@MainActivity, "Ad Completed.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}