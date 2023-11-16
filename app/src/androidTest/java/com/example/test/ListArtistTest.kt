package com.example.test

import android.app.Activity
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import com.example.test.ui.MainActivity
import com.example.test.ui.VisitorListArtistActivity
import com.example.test.ui.adapters.ArtistAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.github.javafaker.Faker


@LargeTest
@RunWith(AndroidJUnit4::class)
class ListArtistTest {

    @get:Rule
    val mainActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun listArtistSuccess() {
        onView(withId(R.id.buttonVisitor)).perform(
            ViewActions.scrollTo(),
            click()
        )
        onView(withId(R.id.btnListArtist)).perform(
            ViewActions.scrollTo(),
            click()
        )
        onView(withText("Lista de Artistas")).check(
            matches(isDisplayed())
        )
    }

    @Test
    fun listArtistAfterCreatedSuccess() {
        val name =  Faker().name().fullName()
        onView(withId(R.id.buttonCollector)).perform(
            ViewActions.scrollTo(),
            click()
        )
        onView(withId(R.id.btnAddArtist)).perform(
            ViewActions.scrollTo(),
            click()
        )
        onView(withId(R.id.name)).perform(
            ViewActions.scrollTo(),
            ViewActions.typeText(name)
        )
        onView(withId(R.id.image)).perform(
            ViewActions.scrollTo(),
            ViewActions.typeText(Faker().internet().image())
        )
        onView(withId(R.id.birthDate)).perform(
            ViewActions.scrollTo(),
            ViewActions.typeText("05/10/2023")
        )
        onView(withId(R.id.description)).perform(
            ViewActions.scrollTo(),
            ViewActions.typeText(Faker().yoda().quote())
        )
        onView(withId(R.id.btnSubmit)).perform(
            ViewActions.scrollTo(),
            click()
        )
        onView(withText("Aceptar")).inRoot(RootMatchers.isDialog()).check(matches(isDisplayed()))
            .perform(
                click()
            )
        SystemClock.sleep(2000)
        onView(withContentDescription("Navigate up")).perform(click())
        SystemClock.sleep(2000)
        onView(withContentDescription("Navigate up")).perform(click())
        SystemClock.sleep(2000)
        onView(withId(R.id.buttonVisitor)).perform(
            ViewActions.scrollTo(),
            click()
        )
        onView(withId(R.id.btnListArtist)).perform(
            ViewActions.scrollTo(),
            click()
        )
        SystemClock.sleep(2000)
        onView(withText("Lista de Artistas")).check(
            matches(isDisplayed())
        )
        SystemClock.sleep(2000)
        val visitorListArtistActivity = getCurrentActivity<VisitorListArtistActivity>()
        var position = 0
        for (artist in visitorListArtistActivity?.artistAdapter?.artists!!) {
            if (artist.name == name) {
                break
            }
            position += 1
        }
        onView(withId(R.id.recycler_view_artist))
            .perform(RecyclerViewActions.scrollToPosition<ArtistAdapter.ArtistViewHolder>(position))
        onView(withText(name)).check(
            matches(isDisplayed())
        )
    }

    private fun <T : AppCompatActivity?> getCurrentActivity(): T? {
        val activity = arrayOfNulls<Activity>(1)
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            val activities =
                ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
            if (activities != null && !activities.isEmpty()) {
                activity[0] = activities.iterator().next()
            }
        }
        return activity[0] as T?
    }
}