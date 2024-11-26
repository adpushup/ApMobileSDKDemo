package com.adpushup.example.instreamad

import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adpushup.apmobilesdk.video.ApPlayer
import com.adpushup.apmobilesdk.video.ApSdkLite
import com.adpushup.example.instreamad.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var apPlayer: ApPlayer? = null
    private val videoUrl: Uri = Uri.parse("https://storage.googleapis.com/gvabox/media/samples/stock.mp4")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialising Ap Mobile SDK. - If In-stream Video Ads are used with other ad formats.
//        ApMobileSdk.init(this, getString(R.string.ap_app_id))
        // Initialising Ap SDK Lite - Only if NO OTHER AD FORMATS are not used.
        ApSdkLite.initialise(this, getString(R.string.ap_app_id))

        val playerView = binding.player

        // Creating Video Player (ApPlayer) with playerView.
        apPlayer = ApPlayer(getString(R.string.ad_placement_id), playerView)
        apPlayer?.init(this, videoUrl)

        // Adding Full Screen Button. Skip this step if you didn't want to add full screen mode button.
        apPlayer?.setFullScreenListener(this) {
            if(it){
                // Player is in Full Screen Mode. Change Screen Orientation if needed.
            } else {
                // Player is in Normal Mode. Change Screen Orientation if needed.
            }
        }

    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT > 23) {
            apPlayer?.start()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT <= 23) {
            apPlayer?.start()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT <= 23) {
            apPlayer?.stop()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT > 23) {
            apPlayer?.stop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        apPlayer?.destroy()
    }
}