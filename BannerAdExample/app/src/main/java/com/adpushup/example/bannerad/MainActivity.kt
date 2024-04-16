package com.adpushup.example.bannerad

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adpushup.apmobilesdk.ApMobileSdk
import com.adpushup.apmobilesdk.ads.ApBanner
import com.adpushup.apmobilesdk.interfaces.ApBannerListener
import com.adpushup.example.bannerad.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var apBanner: ApBanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialising Ap Mobile SDK.
        ApMobileSdk.init(this, getString(R.string.ap_app_id))

        // Loading Banner Ad.
        apBanner = ApBanner(getString(R.string.ad_placement_id))
        binding.adFrame.addView(apBanner.getAdView(this))
        apBanner.loadAd(this, object : ApBannerListener {})
    }

    override fun onResume() {
        super.onResume()
        // Resume the SDK on Activity Resume. This will ensure the SDK preloads
        // the ads and is ready to show ads when required.
        ApMobileSdk.resume(this)
    }

    override fun onDestroy() {
        // Destroy the Banner Ad
        apBanner.destroy()
        super.onDestroy()
    }
}