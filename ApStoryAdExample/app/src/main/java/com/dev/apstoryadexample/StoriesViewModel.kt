package com.dev.apstoryadexample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel for managing the state of user stories.
 *
 * This ViewModel maintains a list of users, tracks the currently displayed user and story,
 * and provides methods to navigate between stories and users.
 */
class StoriesViewModel : ViewModel() {

    /**
     * List of users, each containing their respective stories.
     */
    private val userList = listOf(
        User(1, "User 1", intArrayOf(R.drawable.logo1, R.drawable.logo2, R.drawable.logo1)),
        User(2, "User 2", intArrayOf(R.drawable.logo2, R.drawable.logo1, R.drawable.logo2)),
        User(3, "User 3", intArrayOf(R.drawable.logo1, R.drawable.logo2, R.drawable.logo1)),
        User(4, "User 4", intArrayOf(R.drawable.logo2, R.drawable.logo1, R.drawable.logo2)),
        User(5, "User 5", intArrayOf(R.drawable.logo1, R.drawable.logo2, R.drawable.logo1)),
        User(6, "User 6", intArrayOf(R.drawable.logo2, R.drawable.logo1, R.drawable.logo2)),
        User(7, "User 7", intArrayOf(R.drawable.logo1, R.drawable.logo2, R.drawable.logo1)),
        User(8, "User 8", intArrayOf(R.drawable.logo2, R.drawable.logo1, R.drawable.logo2)),
        User(9, "User 9", intArrayOf(R.drawable.logo1, R.drawable.logo2, R.drawable.logo1))
    )

    /** Index of the currently displayed user. */
    private var currentUserIndex = 0

    /** Index of the currently displayed story in the current user's story list. */
    private var currentStoryIndex = 0

    /** Array of stories for the current user. */
    private var currentStories: IntArray = userList[currentUserIndex].stories

    /** LiveData holding the current story display state. */
    private val _storyState = MutableLiveData<StoryDisplayState>()
    val storyState: LiveData<StoryDisplayState> = _storyState

    /**
     * Initializes the ViewModel and sets the initial state.
     */
    init {
        updateState()
    }

    /**
     * Updates the current story state and notifies observers.
     */
    private fun updateState() {
        _storyState.value = StoryDisplayState(
            userName = userList[currentUserIndex].name,
            storyImageRes = currentStories[currentStoryIndex],
            storyCount = currentStories.size,
            currentStoryIndex = currentStoryIndex
        )
    }

    /**
     * Loads the stories for the specified user index.
     *
     * @param userIndex The index of the user whose stories should be loaded.
     */
    private fun loadUserStories(userIndex: Int) {
        if (userIndex < 0 || userIndex >= userList.size) return
        currentUserIndex = userIndex
        currentStoryIndex = 0
        currentStories = userList[currentUserIndex].stories
        updateState()
    }

    /**
     * Moves to the next story within the current user's story list.
     *
     * If the current story is the last one, it moves to the next user's stories if available.
     */
    fun onNextStory() {
        if (currentStoryIndex < currentStories.size - 1) {
            currentStoryIndex++
            updateState()
        } else {
            // Move to the next user if available
            if (currentUserIndex < userList.size - 1) {
                loadUserStories(currentUserIndex + 1)
            }
            // Optionally handle completion when no more users exist
        }
    }

    /**
     * Moves to the previous story within the current user's story list.
     *
     * If the current story is the first one, it moves to the previous user's last story if available.
     */
    fun onPrevStory() {
        if (currentStoryIndex > 0) {
            currentStoryIndex--
            updateState()
        } else {
            if (currentUserIndex > 0) {
                loadUserStories(currentUserIndex - 1)
                // Optionally set currentStoryIndex to the last story of the previous user
                currentStoryIndex = currentStories.size - 1
                updateState()
            }
        }
    }

    /**
     * Moves to the next user's stories.
     */
    fun nextUser() {
        if (currentUserIndex < userList.size - 1) {
            loadUserStories(currentUserIndex + 1)
        }
    }

    /**
     * Moves to the previous user's stories.
     */
    fun prevUser() {
        if (currentUserIndex > 0) {
            loadUserStories(currentUserIndex - 1)
        }
    }

    /**
     * Checks whether there is a next user available.
     *
     * @return `true` if another user exists, otherwise `false`.
     */
    fun hasNextUser(): Boolean {
        return currentUserIndex < userList.size - 1
    }
}