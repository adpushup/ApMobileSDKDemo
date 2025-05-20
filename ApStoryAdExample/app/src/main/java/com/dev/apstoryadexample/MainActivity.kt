package com.dev.apstoryadexample

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.adpushup.apmobilesdk.ApMobileSdk
import com.adpushup.apmobilesdk.ads.ApStory
import com.adpushup.apmobilesdk.interfaces.ApStoryListener
import com.dev.apstoryadexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialising Ap Mobile SDK.
        ApMobileSdk.init(this, getString(R.string.ap_app_id))
        binding.btnStory.setOnClickListener {
            startActivity(Intent(this@MainActivity, StoryActivity::class.java))
        }
        binding.btnReel.setOnClickListener {
            startActivity(Intent(this@MainActivity, ReelActivity::class.java))
        }
    }
}