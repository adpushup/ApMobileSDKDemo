package com.dev.apstoryadexample

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.adpushup.apmobilesdk.ads.ApStory
import com.adpushup.apmobilesdk.interfaces.ApStoryListener
import com.dev.apstoryadexample.databinding.ActivityReelBinding
import jp.shts.android.storiesprogressview.StoriesProgressView
import jp.shts.android.storiesprogressview.StoriesProgressView.StoriesListener
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ReelActivity powered by the open-source SHTS StoriesProgressView library:
 * https://github.com/shts/StoriesProgressView
 *
 * It also integrates AdPushUp ApStory native ads every two users,
 * giving developers an example of how to insert ads into reel flows.
 */
class ReelActivity : AppCompatActivity(), StoriesListener {
    private lateinit var binding: ActivityReelBinding
    private var storiesProgressView: StoriesProgressView? = null
    private var image: ImageView? = null
    private var tvUserName: TextView? = null
    private val viewModel: StoriesViewModel by lazy {
        ViewModelProvider(this)[StoriesViewModel::class.java]
    }
    private var userCompleteCounter = 0
    private lateinit var apStory: ApStory
    private var currentAdView: View? = null
    private var adJob: Job? = null

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReelBinding.inflate(layoutInflater)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.decorView.windowInsetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        setContentView(binding.root)

        // Method to initialize ApStory Ads
        initializeApStory()

        //Stories impl
        storiesProgressView = binding.stories
        image = binding.image
        tvUserName = binding.tvUserName
        viewModel.storyState.observe(this) { state ->
            tvUserName?.text = state.userName
            image?.setImageResource(state.storyImageRes)
            resetStoriesProgressView(state.storyCount, state.currentStoryIndex)
        }
        binding.reverse.apply {
            setOnClickListener { storiesProgressView?.reverse() }
            setOnTouchListener(onTouchListener)
        }
        binding.skip.apply {
            setOnClickListener { storiesProgressView?.skip() }
            setOnTouchListener(onTouchListener)
        }
        binding.btnPrevious.setOnClickListener { viewModel.prevUser() }
        binding.btnNext.setOnClickListener { onComplete() }
    }

    /**
     * Sets up the ApStory instance and kicks off ad loading.
     */
    private fun initializeApStory() {
        // Create ApStory with the placement ID from resources
        apStory = ApStory(getString(R.string.ad_placement_id))

        // Begin loading the ad into memory
        apStory.loadAd(this@ReelActivity, object : ApStoryListener {
            override fun onComplete(success: Boolean) {
                if (success) {
                    Log.d(TAG, "Ad loaded successfully")
                }
            }
            override fun onError(code: Int, message: String) {
                Log.e(TAG, "Error loading ad: $message")
            }
        })
    }

    /**
     * Adds the loaded ad view to the current activity and schedules its dismissal.
     */
    private fun showAd() {

        // Obtain the root FrameLayout of the activity to place the ad overlay
        val root = findViewById<FrameLayout>(android.R.id.content)

        // This AdView will get the ApStory ad in the 'Reel' format
        currentAdView = apStory.getAd(this)

        // We can now just add the adView with the ApStory Ad to any layout we want to display it in
        if (currentAdView != null && root != null) {
            root.addView(currentAdView)
            adJob = lifecycleScope.launch {
                delay(5000)
                skipAd()
            }
        } else {
            viewModel.nextUser()
        }
    }

    //Story Implementation Code Below
    private fun skipAd() {
        adJob?.cancel()
        val root = findViewById<FrameLayout>(android.R.id.content)
        currentAdView?.let { adView ->
            if (adView.parent != null && root != null) {
                root.removeView(adView)
            }
        }
        currentAdView = null

        // Destroy and reinitialize the ApStory instance for the next ad
        apStory.destroy()
        initializeApStory()

        viewModel.nextUser()
    }

    @SuppressLint("ClickableViewAccessibility")
    private val onTouchListener = View.OnTouchListener { _, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                pressTime = System.currentTimeMillis()
                storiesProgressView?.pause()
                return@OnTouchListener false
            }
            MotionEvent.ACTION_UP -> {
                val now = System.currentTimeMillis()
                storiesProgressView?.resume()
                return@OnTouchListener limit < now - pressTime
            }
        }
        false
    }

    private fun resetStoriesProgressView(storyCount: Int, currentIndex: Int) {
        val container = binding.storiesContainer
        container.removeAllViews()
        storiesProgressView = StoriesProgressView(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                3
            )
        }
        container.addView(storiesProgressView)
        storiesProgressView?.setStoriesCount(storyCount)
        storiesProgressView?.setStoryDuration(3000L)
        storiesProgressView?.setStoriesListener(this)
        storiesProgressView?.startStories(currentIndex)
    }

    override fun onComplete() {
        userCompleteCounter++
        when {
            !viewModel.hasNextUser() -> {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            userCompleteCounter % 2 == 0 -> showAd()
            else -> viewModel.nextUser()
        }
    }

    override fun onNext() {
        viewModel.onNextStory()
    }

    override fun onPrev() {
        viewModel.onPrevStory()
    }

    // Flush resources onDestroy
    override fun onDestroy() {
        storiesProgressView?.destroy()
        storiesProgressView = null
        adJob?.cancel()
        apStory.destroy()
        currentAdView = null
        super.onDestroy()
    }

    companion object {
        private var pressTime: Long = 0L
        private var limit: Long = 500L
        private var TAG: String  = "ReelActivity"
    }
}
