package com.adpushup.example.nativeadexample

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adpushup.apmobilesdk.ApMobileSdk
import com.adpushup.apmobilesdk.ads.ApNative
import com.adpushup.example.nativeadexample.databinding.ActivityMainBinding
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAdView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialising Ap Mobile SDK.
        ApMobileSdk.init(this, getString(R.string.ap_app_id))

        binding.showAd.setOnClickListener {
            val apNative = ApNative("testNativeAdUnit")
            apNative.loadAd(this) {
                if (this.isDestroyed) {
                    it.destroy()
                } else {
                    try{
                        // Inflating Native ad to native layout.
                        @SuppressLint("InflateParams")
                        val nativeAdView: NativeAdView = this.layoutInflater
                            .inflate(R.layout.native_ad, null) as NativeAdView
                        val title = nativeAdView.findViewById<TextView>(R.id.nTitle)
                        title.text = it.headline
                        nativeAdView.headlineView = title

                        val icon: MediaView = nativeAdView.findViewById(R.id.media)
                        it.mediaContent?.let { it1 -> icon.mediaContent = it1 }
                        nativeAdView.mediaView = icon

                        val publisher: TextView = nativeAdView.findViewById(R.id.nPublisher)
                        publisher.text = it.advertiser
                        nativeAdView.advertiserView = publisher

                        nativeAdView.setNativeAd(it)

                        // Adding Ad Layout to Ad Frame
                        binding.adFrame.removeAllViews()
                        binding.adFrame.addView(nativeAdView)

                        Toast.makeText(this, "Ad Shown", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}