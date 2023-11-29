package com.example.test

import android.app.Activity
import android.os.SystemClock.sleep
import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.Espresso.onData
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
import com.example.test.ui.CollectorListAlbums
import com.example.test.ui.MainActivity
import com.example.test.ui.VisitorListAlbumsActivity
import com.example.test.ui.adapters.AlbumAdapter.AlbumViewHolder
import com.example.test.ui.adapters.CommentAdapter.CommentViewHolder
import com.github.javafaker.Faker
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat

@LargeTest
@RunWith(AndroidJUnit4::class)
class DetailAlbumTest {

    companion object {
        val GENRE: List<String> = listOf("Classical", "Salsa", "Rock", "Folk")
        val RATING: List<String> = listOf("Uno", "Dos", "Tres", "Cuatro", "Cinco")
        val RECORD_LABEL: List<String> =
            listOf("Sony Music", "EMI", "Discos Fuentes", "Elektra", "Fania Records")
    }

    @get:Rule
    val mainActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun detailAlbumAsVisitorAfterCreateAlbumSuccess() {
        val name = Faker().name().fullName()
        val image = Faker().internet().image()
        val format = SimpleDateFormat("MM/dd/yyy")
        val releaseDate = format.format(Faker().date().birthday())
        val genre = GENRE[Faker().random().nextInt(0, 3)]
        val recordLabel = RECORD_LABEL[Faker().random().nextInt(0, 4)]
        val description = Faker().lorem().sentence(15)

        onView(withId(R.id.buttonCollector)).perform(
            scrollTo(),
            click()
        )
        onView(withId(R.id.btnAddAlbum)).perform(
            scrollTo(),
            click()
        )
        onView(withId(R.id.name)).perform(
            scrollTo(),
            typeText(name)
        )
        onView(withId(R.id.image)).perform(
            scrollTo(),
            typeText(image)
        )
        onView(withId(R.id.releaseDate)).perform(
            scrollTo(),
            typeText(releaseDate)
        )
        onView(withId(R.id.genre)).perform(
            scrollTo(),
            click()
        )
        onData(
            Matchers.allOf(
                Matchers.`is`(
                    Matchers.instanceOf<Any>(
                        String::class.java
                    )
                ), Matchers.`is`(genre)
            )
        ).perform(click())
        onView(withId(R.id.recordLabel)).perform(
            scrollTo(),
            click()
        )
        onData(
            Matchers.allOf(
                Matchers.`is`(
                    Matchers.instanceOf<Any>(
                        String::class.java
                    )
                ), Matchers.`is`(recordLabel)
            )
        ).perform(click())
        onView(withId(R.id.description)).perform(
            scrollTo(),
            typeText(description)
        )
        onView(withId(R.id.btnSubmit)).perform(
            scrollTo(),
            click()
        )
        onView(withText("Aceptar")).inRoot(isDialog()).check(
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
        onView(withId(R.id.btnListAlbum)).perform(
            scrollTo(),
            click()
        )
        sleep(2000)
        onView(withText("Lista de Álbumes")).check(
            matches(isDisplayed())
        )
        sleep(2000)
        val visitorListAlbumActivity = getCurrentActivity<VisitorListAlbumsActivity>()
        var position = 0
        for (album in visitorListAlbumActivity?.albumAdapter?.albums!!) {
            if (album.name == name) {
                break
            }
            position += 1
        }
        onView(withId(R.id.recycler_view_albums))
            .perform(
                scrollToPosition<CommentViewHolder>(position),
                actionOnItemAtPosition<CommentViewHolder>(position, click())
            )
        onView(withId(R.id.name)).check(
            matches(withText(Matchers.containsString(name)))
        )
        onView(withId(R.id.releaseDate)).check(
            matches(withText(Matchers.containsString(releaseDate)))
        )
        onView(withId(R.id.recordLabel)).check(
            matches(withText(Matchers.containsString(recordLabel)))
        )
        onView(withId(R.id.genre)).check(
            matches(withText(Matchers.containsString(genre)))
        )
        onView(withId(R.id.description)).check(
            matches(withText(Matchers.containsString(description)))
        )
    }

    @Test
    fun detailAlbumAsCollectorAfterCreateAlbumSuccess() {
        val name = Faker().name().fullName()
        val image = Faker().internet().image()
        val format = SimpleDateFormat("MM/dd/yyy")
        val releaseDate = format.format(Faker().date().birthday())
        val genre = GENRE[Faker().random().nextInt(0, 3)]
        val recordLabel = RECORD_LABEL[Faker().random().nextInt(0, 4)]
        val description = Faker().lorem().sentence(15)

        onView(withId(R.id.buttonCollector)).perform(
            scrollTo(),
            click()
        )
        onView(withId(R.id.btnAddAlbum)).perform(
            scrollTo(),
            click()
        )
        onView(withId(R.id.name)).perform(
            scrollTo(),
            typeText(name)
        )
        onView(withId(R.id.image)).perform(
            scrollTo(),
            typeText(image)
        )
        onView(withId(R.id.releaseDate)).perform(
            scrollTo(),
            typeText(releaseDate)
        )
        onView(withId(R.id.genre)).perform(
            scrollTo(),
            click()
        )
        onData(
            Matchers.allOf(
                Matchers.`is`(
                    Matchers.instanceOf<Any>(
                        String::class.java
                    )
                ), Matchers.`is`(genre)
            )
        ).perform(click())
        onView(withId(R.id.recordLabel)).perform(
            scrollTo(),
            click()
        )
        onData(
            Matchers.allOf(
                Matchers.`is`(
                    Matchers.instanceOf<Any>(
                        String::class.java
                    )
                ), Matchers.`is`(recordLabel)
            )
        ).perform(click())
        onView(withId(R.id.description)).perform(
            scrollTo(),
            typeText(description)
        )
        onView(withId(R.id.btnSubmit)).perform(
            scrollTo(),
            click()
        )
        onView(withText("Aceptar")).inRoot(isDialog()).check(
            matches(isDisplayed())
        )
            .perform(
                click()
            )
        sleep(2000)
        onView(withContentDescription("Navigate up")).perform(click())
        sleep(2000)
        onView(withId(R.id.buttonGetAlbums)).perform(
            scrollTo(),
            click()
        )
        sleep(2000)
        onView(withText("Lista de Álbumes")).check(
            matches(isDisplayed())
        )
        sleep(2000)
        val visitorListAlbumActivity = getCurrentActivity<CollectorListAlbums>()
        var position = 0
        for (album in visitorListAlbumActivity?.albumAdapter?.albums!!) {
            if (album.name == name) {
                break
            }
            position += 1
        }
        onView(withId(R.id.recycler_view_albums))
            .perform(
                scrollToPosition<CommentViewHolder>(position),
                actionOnItemAtPosition<CommentViewHolder>(position, click())
            )
        onView(withId(R.id.name)).check(
            matches(withText(Matchers.containsString(name)))
        )
        onView(withId(R.id.releaseDate)).check(
            matches(withText(Matchers.containsString(releaseDate)))
        )
        onView(withId(R.id.recordLabel)).check(
            matches(withText(Matchers.containsString(recordLabel)))
        )
        onView(withId(R.id.genre)).check(
            matches(withText(Matchers.containsString(genre)))
        )
        onView(withId(R.id.description)).check(
            matches(withText(Matchers.containsString(description)))
        )
    }

    @Test
    fun detailAlbumAsCollectorAfterCreateAlbumAndAddCommentSuccess() {
        val name = Faker().name().fullName()
        val image = Faker().internet().image()
        val format = SimpleDateFormat("MM/dd/yyy")
        val releaseDate = format.format(Faker().date().birthday())
        val genre = GENRE[Faker().random().nextInt(0, 3)]
        val recordLabel = RECORD_LABEL[Faker().random().nextInt(0, 4)]
        val description = Faker().lorem().sentence(15)

        val collectorName = Faker().name().fullName()
        val collectorEmail = Faker().internet().emailAddress()
        var collectorTelephone = Faker().number().digits(7)
        val ratingPos = Faker().random().nextInt(0, 4);
        val rating = RATING[ratingPos]
        val comment = Faker().lorem().sentence(15)

        onView(withId(R.id.buttonCollector)).perform(
            scrollTo(),
            click()
        )
        onView(withId(R.id.btnAddAlbum)).perform(
            scrollTo(),
            click()
        )
        onView(withId(R.id.name)).perform(
            scrollTo(),
            typeText(name)
        )
        onView(withId(R.id.image)).perform(
            scrollTo(),
            typeText(image)
        )
        onView(withId(R.id.releaseDate)).perform(
            scrollTo(),
            typeText(releaseDate)
        )
        onView(withId(R.id.genre)).perform(
            scrollTo(),
            click()
        )
        onData(
            Matchers.allOf(
                Matchers.`is`(
                    Matchers.instanceOf<Any>(
                        String::class.java
                    )
                ), Matchers.`is`(genre)
            )
        ).perform(click())
        onView(withId(R.id.recordLabel)).perform(
            scrollTo(),
            click()
        )
        onData(
            Matchers.allOf(
                Matchers.`is`(
                    Matchers.instanceOf<Any>(
                        String::class.java
                    )
                ), Matchers.`is`(recordLabel)
            )
        ).perform(click())
        onView(withId(R.id.description)).perform(
            scrollTo(),
            typeText(description)
        )
        onView(withId(R.id.btnSubmit)).perform(
            scrollTo(),
            click()
        )
        onView(withText("Aceptar")).inRoot(isDialog()).check(
            matches(isDisplayed())
        )
            .perform(
                click()
            )
        sleep(2000)
        onView(withContentDescription("Navigate up")).perform(click())
        sleep(2000)
        onView(withId(R.id.buttonGetAlbums)).perform(
            scrollTo(),
            click()
        )
        sleep(2000)
        onView(withText("Lista de Álbumes")).check(
            matches(isDisplayed())
        )
        sleep(2000)
        val visitorListAlbumActivity = getCurrentActivity<CollectorListAlbums>()
        var position = 0
        for (album in visitorListAlbumActivity?.albumAdapter?.albums!!) {
            if (album.name == name) {
                break
            }
            position += 1
        }
        onView(withId(R.id.recycler_view_albums))
            .perform(
                scrollToPosition<CommentViewHolder>(position),
                actionOnItemAtPosition<CommentViewHolder>(position, click())
            )
        onView(withId(R.id.name)).check(
            matches(withText(Matchers.containsString(name)))
        )
        onView(withId(R.id.releaseDate)).check(
            matches(withText(Matchers.containsString(releaseDate)))
        )
        onView(withId(R.id.recordLabel)).check(
            matches(withText(Matchers.containsString(recordLabel)))
        )
        onView(withId(R.id.genre)).check(
            matches(withText(Matchers.containsString(genre)))
        )
        onView(withId(R.id.description)).check(
            matches(withText(Matchers.containsString(description)))
        )
        onView(withId(R.id.btnAddComment)).perform(
            scrollTo(),
            click()
        )
        onView(withId(R.id.name)).perform(
            scrollTo(),
            typeText(collectorName)
        )
        onView(withId(R.id.email)).perform(
            scrollTo(),
            typeText(collectorEmail)
        )
        onView(withId(R.id.telephone)).perform(
            scrollTo(),
            typeText(collectorTelephone)
        )
        onView(withId(R.id.rating)).perform(
            scrollTo(),
            click()
        )
        onData(
            Matchers.allOf(
                Matchers.`is`(
                    Matchers.instanceOf<Any>(
                        String::class.java
                    )
                ), Matchers.`is`(rating)
            )
        ).perform(click())
        onView(withId(R.id.description)).perform(
            scrollTo(),
            typeText(comment)
        )
        onView(withId(R.id.btnSubmit)).perform(
            scrollTo(),
            click()
        )
        sleep(2000)
        onView(withText("Aceptar")).inRoot(isDialog()).check(
            matches(isDisplayed())
        ).perform(
            click()
        )
        sleep(2000)
        onView(withText("$comment - Puntaje: " + (ratingPos + 1).toString())).check(
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