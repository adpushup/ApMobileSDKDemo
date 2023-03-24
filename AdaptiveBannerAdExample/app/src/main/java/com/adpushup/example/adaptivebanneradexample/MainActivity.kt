package com.adpushup.example.adaptivebanneradexample

import android.content.res.Resources.getSystem
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adpushup.apmobilesdk.ApMobileSdk
import com.adpushup.apmobilesdk.ads.ApBanner
import com.adpushup.apmobilesdk.interfaces.ApBannerListener
import com.adpushup.example.adaptivebanneradexample.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdSize

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialising Ap Mobile SDK.
        ApMobileSdk.init(this, getString(R.string.ap_app_id))

        // Calculating Ad Size For Adaptive Banner Ad.
        val screenWidth =  getSystem().displayMetrics.widthPixels
        val adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this,  screenWidth)

        // Loading Adaptive Banner Ad.
        val apBanner = ApBanner(getString(R.string.ad_placement_id))
        binding.adFrame.addView(apBanner.getAdView(this))
        apBanner.loadAd(this, adSize, object : ApBannerListener {})

    }

}