package com.adpushup.example.nativeadexample

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.SyncStateContract.Constants
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adpushup.apmobilesdk.ApMobileSdk
import com.adpushup.apmobilesdk.ads.ApNative
import com.adpushup.apmobilesdk.interfaces.ApNativeListener
import com.adpushup.example.nativeadexample.databinding.ActivityMainBinding
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var nativeAd : NativeAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialising Ap Mobile SDK.
        ApMobileSdk.init(this, getString(R.string.ap_app_id))
        
        binding.loadAd.setOnClickListener {
            val apNative = ApNative(getString(R.string.ad_placement_id))
            apNative.loadAd(this) { p0 ->
                if (this@MainActivity.isDestroyed) {
                    p0?.destroy()
                } else {
                    nativeAd = p0
                    Toast.makeText(this@MainActivity, "Ad Loaded", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.showAd.setOnClickListener {
            if(nativeAd != null){
                try{
                    // Inflating Native ad to native layout.
                    @SuppressLint("InflateParams")
                    val nativeAdView: NativeAdView = this.layoutInflater
                        .inflate(R.layout.native_ad, null) as NativeAdView
                    val title = nativeAdView.findViewById<TextView>(R.id.nTitle)
                    title.text = nativeAd?.headline
                    nativeAdView.headlineView = title

                    val icon: MediaView = nativeAdView.findViewById(R.id.media)
                    nativeAd?.mediaContent?.let { it1 -> icon.mediaContent = it1 }
                    nativeAdView.mediaView = icon

                    val publisher: TextView = nativeAdView.findViewById(R.id.nPublisher)
                    publisher.text = nativeAd?.advertiser
                    nativeAdView.advertiserView = publisher

                    nativeAd?.let { nativeAdView.setNativeAd(it) }

                    // Adding Ad Layout to Ad Frame
                    binding.adFrame.removeAllViews()
                    binding.adFrame.addView(nativeAdView)

                    Toast.makeText(this, "Ad Shown", Toast.LENGTH_SHORT).show()
                } catch (e: Exception){
                    e.printStackTrace()
                }
            } else {
                Toast.makeText(this, "Ad Not Loaded Yet", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Resume the SDK on Activity Resume. This will ensure the SDK preloads
        // the ads and is ready to show ads when required.
        ApMobileSdk.resume(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        nativeAd?.destroy()
    }
}