package com.example.test

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.test.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ListArtistTest {

    @get:Rule
    val mainActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun listArtistSuccess() {
        onView(ViewMatchers.withId(R.id.buttonVisitor)).perform(
            ViewActions.scrollTo(),
            ViewActions.click()
        )
        onView(ViewMatchers.withId(R.id.btnListArtist)).perform(
            ViewActions.scrollTo(),
            ViewActions.click()
        )
        onView(ViewMatchers.withText("Listado de artistas")).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
    }
}