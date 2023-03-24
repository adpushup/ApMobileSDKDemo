package com.adpushup.example.apvideo

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adpushup.apmobilesdk.ApMobileSdk
import com.adpushup.apmobilesdk.video.ApVideo
import com.adpushup.example.apvideo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var apVideo: ApVideo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialising Ap Mobile SDK.
        ApMobileSdk.init(this, getString(R.string.ap_app_id))

        // Creating ApVideo with playerView.
        val playerView = binding.player
        apVideo = ApVideo(getString(R.string.ad_placement_id), playerView)
    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT > 23) {
            apVideo?.start(this)
        }
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT <= 23) {
            apVideo?.start(this)
        }
    }

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT <= 23) {
            apVideo?.stop()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT > 23) {
            apVideo?.stop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        apVideo?.destroy()
    }
}
