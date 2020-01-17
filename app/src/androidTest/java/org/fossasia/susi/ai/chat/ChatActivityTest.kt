package org.fossasia.susi.ai.chat

import android.Manifest
import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import android.support.test.runner.AndroidJUnit4
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import java.io.IOException
import junit.framework.Assert.assertTrue
import org.fossasia.susi.ai.R
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import timber.log.Timber

/**
 * Created by collinx on 22-10-2017.
 */

@RunWith(AndroidJUnit4::class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ChatActivityTest {

    @Rule
    @JvmField
    val permissionRule: TestRule = GrantPermissionRule.grant(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @Rule
    @JvmField
    val mActivityRule = ActivityTestRule(ChatActivity::class.java)

    /**
     * Unlock screen.
     *
     * @throws IOException the io exception
     * @throws InterruptedException the interrupted exception
     */
    @Before
    @Throws(IOException::class, InterruptedException::class)
    fun unlockScreen() {
        Timber.d("running unlockScreen..")

        val activity = mActivityRule.activity
        val wakeUpDevice = Runnable {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
        activity.runOnUiThread(wakeUpDevice)
    }

    /**
     * Test activity_chat items visibility on launch of app
     */
    @Test
    fun test01_UIViewsPresenceOnLoad() {
        Timber.d("running test01_UIViewsPresenceOnLoad..")

        // checks if recycler view is present
        onView(withId(R.id.rv_chat_feed)).check(matches(isDisplayed()))

        // checks if layout container for chat box is present
        onView(withId(R.id.sendMessageLayout)).check(matches(isDisplayed()))

        // checks if message box is present
        onView(withId(R.id.askSusiMessage)).check(matches(isDisplayed()))

        // checks if microphone button is present
        onView(withId(R.id.btnSpeak)).check(matches(isDisplayed()))

        // checks if search button is present
        onView(withId(R.id.searchChat)).check(matches(isDisplayed()))

        // checks if voice search button is present
        onView(withId(R.id.voiceSearchChat)).check(matches(isDisplayed()))

        // checks if base coordinator_layout is present
        onView(withId(R.id.coordinator_layout)).check(matches(isDisplayed()))

        // checks if settings button is present
        onView(withId(R.id.fabsetting)).check(matches(isDisplayed()))

        // performs click on search icon and checks if search input is present
        onView(withId(R.id.searchChat)).perform(click())
        onView(withId(R.id.chatSearchInput)).check(matches(isDisplayed()))

        // checks if keyboard is shown on search input click
        assertTrue(isKeyboardShown())

        // performs click on settings button and checks if Skills activity is open
        onView(withId(R.id.fabsetting)).perform(click())
        onView(withId(R.id.fragment_container)).check(matches(isDisplayed()))
    }

    fun isKeyboardShown(): Boolean {
        val inputMethodManager = InstrumentationRegistry.getInstrumentation().targetContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.isAcceptingText
    }
}
