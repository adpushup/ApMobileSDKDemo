package com.dev.apstoryadexample

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
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
import com.dev.apstoryadexample.databinding.ActivityStoryBinding
import jp.shts.android.storiesprogressview.StoriesProgressView
import jp.shts.android.storiesprogressview.StoriesProgressView.StoriesListener

/**
 * StoryActivity demonstrates a full-screen, swipeable "stories" UI,
 * powered by the open-source SHTS StoriesProgressView library:
 * https://github.com/shts/StoriesProgressView
 *
 * It also integrates AdPushUp ApStory native ads every two users,
 * giving developers an example of how to insert ads into story flows.
 */
class StoryActivity : AppCompatActivity(), StoriesListener {
    // View binding for layout views
    private lateinit var binding: ActivityStoryBinding

    // Progress view from SHTS library to display story progress bars
    private var storiesProgressView: StoriesProgressView? = null

    // ImageView to show each story image
    private var image: ImageView? = null

    // TextView to display the current user's name
    private var tvUserName: TextView? = null

    // ViewModel to manage story/user state
    private val viewModel: StoriesViewModel by lazy {
        ViewModelProvider(this)[StoriesViewModel::class.java]
    }

    // Controller that manages ApStory ads within the story sequence
    private var adController: StoriesAdController? = null

    // Counter for completed users; show ad every 2 users
    private var userCompleteCounter = 0

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)

        // Hide status bar for immersive, full-screen experience
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.decorView.windowInsetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        setContentView(binding.root)

        // Determine which ad format to use (story vs. reel)
        val storyType = intent.getSerializableExtra("STORY_TYPE") as ApStoryType

        // Assign views from binding
        storiesProgressView = binding.stories
        image = binding.image
        tvUserName = binding.tvUserName

        // Initialize the ad controller; show next user when an ad completes
        adController = StoriesAdController(this, lifecycleScope, storyType).apply {
            onAdCompleted = { viewModel.nextUser() }
        }

        // Observe changes in story data: user name, image, and progress resets
        viewModel.storyState.observe(this) { state ->
            tvUserName?.text = state.userName
            image?.setImageResource(state.storyImageRes)
            resetStoriesProgressView(state.storyCount, state.currentStoryIndex)
        }

        // Reverse tap: go to previous story; pause/resume on touch
        binding.reverse.apply {
            setOnClickListener { storiesProgressView?.reverse() }
            setOnTouchListener(onTouchListener)
        }

        // Skip tap: go to next story; pause/resume on touch
        binding.skip.apply {
            setOnClickListener { storiesProgressView?.skip() }
            setOnTouchListener(onTouchListener)
        }

        // Buttons to manually switch between users
        binding.btnPrevious.setOnClickListener { viewModel.prevUser() }
        binding.btnNext.setOnClickListener { onComplete() }
    }

    @SuppressLint("ClickableViewAccessibility")
    private val onTouchListener = View.OnTouchListener { _, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // Record press time and pause the progress
                pressTime = System.currentTimeMillis()
                storiesProgressView?.pause()
                return@OnTouchListener false
            }
            MotionEvent.ACTION_UP -> {
                // Resume and decide if it's a click or long press
                val now = System.currentTimeMillis()
                storiesProgressView?.resume()
                return@OnTouchListener limit < now - pressTime
            }
        }
        false
    }

    /**
     * Rebuilds the StoriesProgressView for the current user,
     * setting the count, duration, listener, and starting at an index.
     */
    private fun resetStoriesProgressView(storyCount: Int, currentIndex: Int) {
        val container = binding.storiesContainer
        container.removeAllViews()

        // Create a fresh StoriesProgressView from the SHTS library
        storiesProgressView = StoriesProgressView(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                3
            )
        }
        container.addView(storiesProgressView)

        // Configure progress view
        storiesProgressView?.setStoriesCount(storyCount)
        storiesProgressView?.setStoryDuration(3000L)
        storiesProgressView?.setStoriesListener(this)
        storiesProgressView?.startStories(currentIndex)
    }

    /**
     * Called when a user's story sequence finishes.
     * After every 2 users, displays an ApStory ad; otherwise moves to next user.
     */
    override fun onComplete() {
        userCompleteCounter++
        when {
            !viewModel.hasNextUser() -> {
                // All users done: return to main screen
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            userCompleteCounter % 2 == 0 -> adController?.showAd()
            else -> viewModel.nextUser()
        }
    }

    override fun onNext() {
        // Advance within current user's stories
        viewModel.onNextStory()
    }

    override fun onPrev() {
        // Go back within current user's stories
        viewModel.onPrevStory()
    }

    override fun onDestroy() {
        // Clean up progress view and ad controller
        storiesProgressView?.destroy()
        storiesProgressView = null
        adController?.destroy()
        super.onDestroy()
    }

    companion object {
        // Timestamp for touch handling
        private var pressTime: Long = 0L

        // Threshold (ms) for distinguishing long presses
        private var limit: Long = 500L
    }
}
