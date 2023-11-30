package com.example.test

import android.os.SystemClock
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.test.ui.MainActivity
import com.example.test.ui.adapters.AlbumAdapter
import org.hamcrest.Matchers
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
class CreateAlbumCommentTest {
    @get:Rule
    val mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun addAlbumCommentCorrectlyTest() {
        //Main Activity - Click button collector
        onView(withId(R.id.buttonCollector)).perform(scrollTo(), click())
        //Collector Home Activity - Click button get albums
        onView(withId(R.id.buttonGetAlbums)).perform(scrollTo(), click())
        //Collector Get firts album
        SystemClock.sleep(2000)
        onView(withId(R.id.recycler_view_albums))
            .perform(
                RecyclerViewActions.scrollToPosition<AlbumAdapter.AlbumViewHolder>(1),
                RecyclerViewActions.actionOnItemAtPosition<AlbumAdapter.AlbumViewHolder>(1, click())
            )
        SystemClock.sleep(2000)
        onView(withId(R.id.btnAddComment)).perform(scrollTo(), click())
        onView(withId(R.id.name)).perform(scrollTo(), typeText("Collector 1"))
        onView(withId(R.id.email)).perform(scrollTo(), typeText("Collector1@test.com"))
        onView(withId(R.id.telephone)).perform(scrollTo(), typeText("3057100878"))
        onView(withId(R.id.rating)).perform(click())
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val ratingsArray: Array<String> = context.resources.getStringArray(R.array.rating)
        Espresso.onData(
            Matchers.allOf(
                Matchers.`is`(
                    Matchers.instanceOf<Any>(
                        String::class.java
                    )
                ), Matchers.`is`(ratingsArray[1])
            )
        ).perform(click())
        onView(withId(R.id.description)).perform(scrollTo(), typeText("Test"))
        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())
        //Check if dialog alert
        onView(ViewMatchers.withText(R.string.addComment)).inRoot(RootMatchers.isDialog()).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
    }

    @Test
    fun addAlbumCommentEmptyNameTest() {
        //Main Activity - Click button collector
        onView(withId(R.id.buttonCollector)).perform(scrollTo(), click())
        //Collector Home Activity - Click button get albums
        onView(withId(R.id.buttonGetAlbums)).perform(scrollTo(), click())
        //Collector Get firts album
        SystemClock.sleep(2000)
        onView(withId(R.id.recycler_view_albums))
            .perform(
                RecyclerViewActions.scrollToPosition<AlbumAdapter.AlbumViewHolder>(1),
                RecyclerViewActions.actionOnItemAtPosition<AlbumAdapter.AlbumViewHolder>(1, click())
            )
        SystemClock.sleep(2000)
        onView(withId(R.id.btnAddComment)).perform(scrollTo(), click())
        onView(withId(R.id.name)).perform(scrollTo(), click(), typeText(""))
        onView(withId(R.id.email)).perform(scrollTo(), typeText("Collector1@test.com"))
        onView(withId(R.id.telephone)).perform(scrollTo(), typeText("3057100878"))
        onView(withId(R.id.rating)).perform(click())
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val ratingsArray: Array<String> = context.resources.getStringArray(R.array.rating)
        Espresso.onData(
            Matchers.allOf(
                Matchers.`is`(
                    Matchers.instanceOf<Any>(
                        String::class.java
                    )
                ), Matchers.`is`(ratingsArray[1])
            )
        ).perform(click())
        onView(withId(R.id.description)).perform(scrollTo(), typeText("Test"))
        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())
        onView(withId(R.id.name)).perform(scrollTo())
        onView(ViewMatchers.withText(R.string.error1)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
    }

    @Test
    fun addAlbumCommentEmptyEmailTest() {
        //Main Activity - Click button collector
        onView(withId(R.id.buttonCollector)).perform(scrollTo(), click())
        //Collector Home Activity - Click button get albums
        onView(withId(R.id.buttonGetAlbums)).perform(scrollTo(), click())
        //Collector Get firts album
        SystemClock.sleep(2000)
        onView(withId(R.id.recycler_view_albums))
            .perform(
                RecyclerViewActions.scrollToPosition<AlbumAdapter.AlbumViewHolder>(1),
                RecyclerViewActions.actionOnItemAtPosition<AlbumAdapter.AlbumViewHolder>(1, click())
            )
        SystemClock.sleep(2000)
        onView(withId(R.id.btnAddComment)).perform(scrollTo(), click())
        onView(withId(R.id.name)).perform(scrollTo(), typeText("Collector 1"))
        onView(withId(R.id.email)).perform(scrollTo(), typeText(""))
        onView(withId(R.id.telephone)).perform(scrollTo(), typeText("3057100878"))
        onView(withId(R.id.rating)).perform(click())
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val ratingsArray: Array<String> = context.resources.getStringArray(R.array.rating)
        Espresso.onData(
            Matchers.allOf(
                Matchers.`is`(
                    Matchers.instanceOf<Any>(
                        String::class.java
                    )
                ), Matchers.`is`(ratingsArray[1])
            )
        ).perform(click())
        onView(withId(R.id.description)).perform(scrollTo(), typeText("Test"))
        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())
        //Check if alert
        onView(ViewMatchers.withText(R.string.error1)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
    }

    @Test
    fun addAlbumCommentWrongEmailTest() {
        //Main Activity - Click button collector
        onView(withId(R.id.buttonCollector)).perform(scrollTo(), click())
        //Collector Home Activity - Click button get albums
        onView(withId(R.id.buttonGetAlbums)).perform(scrollTo(), click())
        //Collector Get firts album
        SystemClock.sleep(2000)
        onView(withId(R.id.recycler_view_albums))
            .perform(
                RecyclerViewActions.scrollToPosition<AlbumAdapter.AlbumViewHolder>(1),
                RecyclerViewActions.actionOnItemAtPosition<AlbumAdapter.AlbumViewHolder>(1, click())
            )
        SystemClock.sleep(2000)
        onView(withId(R.id.btnAddComment)).perform(scrollTo(), click())
        onView(withId(R.id.name)).perform(scrollTo(), typeText("Collector 1"))
        onView(withId(R.id.email)).perform(scrollTo(), typeText("test"))
        onView(withId(R.id.telephone)).perform(scrollTo(), typeText("3057100878"))
        onView(withId(R.id.rating)).perform(click())
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val ratingsArray: Array<String> = context.resources.getStringArray(R.array.rating)
        Espresso.onData(
            Matchers.allOf(
                Matchers.`is`(
                    Matchers.instanceOf<Any>(
                        String::class.java
                    )
                ), Matchers.`is`(ratingsArray[1])
            )
        ).perform(click())
        onView(withId(R.id.description)).perform(scrollTo(), typeText("Test"))
        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())
        //Check if alert
        onView(ViewMatchers.withText(R.string.error6)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
    }

    @Test
    fun addAlbumCommentEmptyTelephoneTest() {
        //Main Activity - Click button collector
        onView(withId(R.id.buttonCollector)).perform(scrollTo(), click())
        //Collector Home Activity - Click button get albums
        onView(withId(R.id.buttonGetAlbums)).perform(scrollTo(), click())
        //Collector Get firts album
        SystemClock.sleep(2000)
        onView(withId(R.id.recycler_view_albums))
            .perform(
                RecyclerViewActions.scrollToPosition<AlbumAdapter.AlbumViewHolder>(1),
                RecyclerViewActions.actionOnItemAtPosition<AlbumAdapter.AlbumViewHolder>(1, click())
            )
        SystemClock.sleep(2000)
        onView(withId(R.id.btnAddComment)).perform(scrollTo(), click())
        onView(withId(R.id.name)).perform(scrollTo(), typeText("Collector 1"))
        onView(withId(R.id.email)).perform(scrollTo(), typeText("Collector1@test.com"))
        onView(withId(R.id.telephone)).perform(scrollTo(), click(), typeText(""))
        onView(withId(R.id.rating)).perform(click())
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val ratingsArray: Array<String> = context.resources.getStringArray(R.array.rating)
        Espresso.onData(
            Matchers.allOf(
                Matchers.`is`(
                    Matchers.instanceOf<Any>(
                        String::class.java
                    )
                ), Matchers.`is`(ratingsArray[1])
            )
        ).perform(click())
        onView(withId(R.id.description)).perform(scrollTo(), typeText("Test"))
        onView(ViewMatchers.withText(R.string.error1)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
    }

    @Test
    fun addAlbumCommentEmptyDescriptionTest() {
        //Main Activity - Click button collector
        onView(withId(R.id.buttonCollector)).perform(scrollTo(), click())
        //Collector Home Activity - Click button get albums
        onView(withId(R.id.buttonGetAlbums)).perform(scrollTo(), click())
        //Collector Get firts album
        SystemClock.sleep(2000)
        onView(withId(R.id.recycler_view_albums))
            .perform(
                RecyclerViewActions.scrollToPosition<AlbumAdapter.AlbumViewHolder>(1),
                RecyclerViewActions.actionOnItemAtPosition<AlbumAdapter.AlbumViewHolder>(1, click())
            )
        SystemClock.sleep(2000)
        onView(withId(R.id.btnAddComment)).perform(scrollTo(), click())
        onView(withId(R.id.name)).perform(scrollTo(), typeText("Collector 1"))
        onView(withId(R.id.email)).perform(scrollTo(), typeText("Collector1@test.com"))
        onView(withId(R.id.telephone)).perform(scrollTo(), typeText("3057100878"))
        onView(withId(R.id.rating)).perform(click())
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val ratingsArray: Array<String> = context.resources.getStringArray(R.array.rating)
        Espresso.onData(
            Matchers.allOf(
                Matchers.`is`(
                    Matchers.instanceOf<Any>(
                        String::class.java
                    )
                ), Matchers.`is`(ratingsArray[1])
            )
        ).perform(click())
        onView(withId(R.id.description)).perform(scrollTo(), typeText(""))
        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())
        //Check if alert
        onView(ViewMatchers.withText(R.string.error1)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
    }

    @Test
    fun addAlbumCommentEmptyRatingTest() {
        //Main Activity - Click button collector
        onView(withId(R.id.buttonCollector)).perform(scrollTo(), click())
        //Collector Home Activity - Click button get albums
        onView(withId(R.id.buttonGetAlbums)).perform(scrollTo(), click())
        //Collector Get firts album
        SystemClock.sleep(2000)
        onView(withId(R.id.recycler_view_albums))
            .perform(
                RecyclerViewActions.scrollToPosition<AlbumAdapter.AlbumViewHolder>(1),
                RecyclerViewActions.actionOnItemAtPosition<AlbumAdapter.AlbumViewHolder>(1, click())
            )
        SystemClock.sleep(2000)
        onView(withId(R.id.btnAddComment)).perform(scrollTo(), click())
        onView(withId(R.id.name)).perform(scrollTo(), typeText("Collector 1"))
        onView(withId(R.id.email)).perform(scrollTo(), typeText("Collector1@test.com"))
        onView(withId(R.id.telephone)).perform(scrollTo(), typeText("3057100878"))
        onView(withId(R.id.rating)).perform(click())
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val ratingsArray: Array<String> = context.resources.getStringArray(R.array.rating)
        Espresso.onData(
            Matchers.allOf(
                Matchers.`is`(
                    Matchers.instanceOf<Any>(
                        String::class.java
                    )
                ), Matchers.`is`(ratingsArray[0])
            )
        ).perform(click())
        onView(withId(R.id.description)).perform(scrollTo(), typeText("Test"))
        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())
        //Check if alert
        onView(ViewMatchers.withText(R.string.error5)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
    }

}