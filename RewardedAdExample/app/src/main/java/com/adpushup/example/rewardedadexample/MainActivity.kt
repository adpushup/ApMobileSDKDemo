package com.adpushup.example.rewardedadexample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adpushup.apmobilesdk.ApMobileSdk
import com.adpushup.apmobilesdk.interfaces.ApRewardedListener
import com.adpushup.example.rewardedadexample.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialising Ap Mobile SDK.
        ApMobileSdk.init(this, getString(R.string.ap_app_id))

        binding.showAd.setOnClickListener {
            if(ApMobileSdk.isRewardedAdReady(getString(R.string.ad_placement_id))){
                // Ask User if he want to watch an Rewarded Ad.
                MaterialAlertDialogBuilder(this@MainActivity)
                    .setTitle("Ad Available")
                    .setMessage("Hey! Do you want to watch an rewarded ad?")
                    .setPositiveButton(
                        "YES"
                    ) { dialogInterface, i ->
                        // User want to watch an ad. Show Ad.
                        ApMobileSdk.showRewardedAd(this, getString(R.string.ad_placement_id), object : ApRewardedListener {
                            override fun onUserEarnedReward(type: String?, amount: Int) {
                                Toast.makeText(this@MainActivity, "User Earned Reward: Amount: $amount of Type: $type", Toast.LENGTH_SHORT).show()
                            }
                            override fun onComplete() {
                                Toast.makeText(this@MainActivity, "Ad Completed.", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                    .setNegativeButton(
                        "NO"
                    ) { dialogInterface, i ->
                        // User doesn't want to watch an ad.
                        Toast.makeText(this@MainActivity, "User Not Interested.", Toast.LENGTH_SHORT).show()
                    }
                    .show()
            } else {
                // Ad Not Ready Yet
                Toast.makeText(this@MainActivity, "Ad Not Ready Yet", Toast.LENGTH_SHORT).show()
            }
        }
    }
}