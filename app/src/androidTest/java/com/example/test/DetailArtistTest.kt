package com.example.test

import android.app.Activity
import android.os.SystemClock.sleep
import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.RootMatchers.isDialog
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
import com.example.test.ui.adapters.CommentAdapter.CommentViewHolder
import com.github.javafaker.Faker
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat

@LargeTest
@RunWith(AndroidJUnit4::class)
class DetailArtistTest {

    @get:Rule
    val mainActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun detailArtistAsVisitorAfterCreateArtistSuccess() {
        val name = Faker().name().fullName()
        val image = Faker().internet().image()
        val format = SimpleDateFormat("MM/dd/yyy")
        val birthDate = format.format(Faker().date().birthday())
        val description = Faker().lorem().sentence(15)

        //Main Activity - Click button collector
        onView(withId(R.id.buttonCollector)).perform(scrollTo(), click())
        //Collector Home Activity - Click button add artist
        onView(withId(R.id.btnAddArtist)).perform(scrollTo(), click())
        //Collector Add Artist Activity - Type name in the form
        onView(withId(R.id.name)).perform(scrollTo(), typeText(name))
        //Collector Add Artist Activity - Type image in the form
        onView(withId(R.id.image)).perform(scrollTo(), typeText(image))
        //Collector Add Artist Activity - Type birth date in the form
        onView(withId(R.id.birthDate)).perform(scrollTo(), typeText(birthDate))
        //Collector Add Artist Activity - Type birth date in the form
        onView(withId(R.id.description)).perform(scrollTo(), typeText(description))
        //Collector Add Artist Activity - Click button submit form
        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())
        //Check if dialog alert
        onView(withText(R.string.addArtist)).inRoot(isDialog()).check(matches(isDisplayed()))

        onView(withText(R.string.aceptar)).inRoot(isDialog()).check(
            matches(isDisplayed())
        )
            .perform(
                click()
            )
        sleep(2000)
        onView(withContentDescription("Navigate up")).perform(click())
        sleep(2000)
        onView(withContentDescription("Navigate up")).perform(click())
        sleep(2000)
        onView(withId(R.id.buttonVisitor)).perform(
            scrollTo(),
            click()
        )
        onView(withId(R.id.btnListArtist)).perform(
            scrollTo(),
            click()
        )
        sleep(2000)
        onView(withText(R.string.listado_de_artistas)).check(
            matches(isDisplayed())
        )
        sleep(2000)
        val visitorListArtistActivity = getCurrentActivity<VisitorListArtistActivity>()
        var position = 0
        for (artist in visitorListArtistActivity?.artistAdapter?.artists!!) {
            if (artist.name == name) {
                break
            }
            position += 1
        }
        onView(withId(R.id.recycler_view_artist))
            .perform(
                scrollToPosition<CommentViewHolder>(position),
                actionOnItemAtPosition<CommentViewHolder>(position, click())
            )
        onView(withId(R.id.name)).check(
            matches(withText(Matchers.containsString(name)))
        )
        onView(withId(R.id.birthDate)).check(
            matches(withText(Matchers.containsString(birthDate)))
        )
        onView(withId(R.id.description)).check(
            matches(withText(Matchers.containsString(description)))
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