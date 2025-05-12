package com.dev.apstoryadexample

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleCoroutineScope
import com.adpushup.apmobilesdk.ads.ApStory
import com.adpushup.apmobilesdk.interfaces.ApStoryListener
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * StoriesAdController manages the loading, display, and lifecycle of ApStory ads
 * in a real-world scenario. It demonstrates two ad formats:
 * 1. AP_STORY (short story ads) with swipe-up and click-to-dismiss gestures
 * 2. AP_REEL (reel-style ads) with default behavior
 *
 * @param context The Activity context used to attach ad views to the UI
 * @param lifecycleScope Coroutine scope tied to the Activity lifecycle
 * @param adType Specifies whether to show a SHORTS-style story or a REEL-style ad
 */
class StoriesAdController(
    private val context: Context,
    private val lifecycleScope: LifecycleCoroutineScope,
    private val adType: ApStoryType
) {
    // ApStory SDK instance for loading and rendering native ads
    private lateinit var apStory: ApStory

    // Reference to the currently added ad view in the activity root
    private var currentAdView: View? = null

    // Job that manages the automatic ad dismissal timer
    private var adJob: Job? = null

    // Callback to notify when the ad is finished or skipped
    var onAdCompleted: (() -> Unit)? = null

    // Tag for console logging
    private val TAG = "StoriesAdController"

    init {
        // Initialize the ApStory ad manager as soon as this controller is created
        initializeAdManager()
    }

    /**
     * Sets up the ApStory instance and kicks off ad loading.
     */
    private fun initializeAdManager() {
        // Create ApStory with the placement ID from resources
        apStory = ApStory(context.getString(R.string.ad_placement_id))

        // Begin loading the ad into memory
        apStory.loadAd(context, object : ApStoryListener {
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
     * The implementation differs based on the adType:
     * - AP_STORY: Enables swipe-up gesture and click-to-dismiss
     * - AP_REEL: Default ad view for reel format
     */
    fun showAd() {
        // Obtain the root FrameLayout of the activity to place the ad overlay
        val root = (context as? AppCompatActivity)
            ?.findViewById<FrameLayout>(android.R.id.content)

        // Choose which ad view to retrieve based on type
        currentAdView = when (adType) {
            ApStoryType.AP_STORY ->
                // Short story ads support gestures for engagement
                apStory.getAd(
                    context,
                    isSwipeUpGestureEnabled = true,
                    isClickDismissEnabled = true
                ) {
                    Log.d(TAG, "Ad dismissed via gesture/click")
                    skipAd()
                }
            ApStoryType.AP_REEL ->
                // Reel-style ads with default interaction
                apStory.getAd(context)
        }

        if (currentAdView != null && root != null) {
            // Add the ad view to the UI
            root.addView(currentAdView)

            // Automatically skip ad after 5 seconds
            adJob = lifecycleScope.launch {
                delay(5000)
                skipAd()
            }
        } else {
            // If ad view failed to load or attach, immediately proceed
            onAdCompleted?.invoke()
        }
    }

    /**
     * Removes the ad view, cancels the timer, and reloads the next ad.
     * Also triggers the completion callback to continue the story flow.
     */
    private fun skipAd() {
        // Stop auto-dismiss timer
        adJob?.cancel()

        // Remove the view from its parent if still attached
        val root = (context as? AppCompatActivity)
            ?.findViewById<FrameLayout>(android.R.id.content)
        currentAdView?.let { adView ->
            if (adView.parent != null && root != null) {
                root.removeView(adView)
            }
        }
        currentAdView = null

        // Destroy and reinitialize the ApStory instance for the next ad
        apStory.destroy()
        initializeAdManager()

        // Notify that the ad cycle is complete
        onAdCompleted?.invoke()
    }

    /**
     * Clean up resources when the Activity or controller is destroyed.
     */
    fun destroy() {
        // Cancel any pending dismissals
        adJob?.cancel()

        // Tear down ApStory internals
        apStory.destroy()
        currentAdView = null
    }
}
