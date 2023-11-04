package com.example.test

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.test.ui.MainActivity
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
class VinilAppTest {
    @get:Rule
    val mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)
    @Test
    fun addArtistCorrectlyTest() {
        //Main Activity - Click button collector
        onView(withId(R.id.buttonCollector)).perform(scrollTo(), click())
        //Collector Home Activity - Click button add artist
        onView(withId(R.id.btnAddArtist)).perform(scrollTo(), click())
        //Collector Add Artist Activity - Type name in the form
        onView(withId(R.id.name)).perform(scrollTo(), typeText("Bob Marley"))
        //Collector Add Artist Activity - Type image in the form
        onView(withId(R.id.image)).perform(scrollTo(), typeText("https://th.bing.com/th/id/OIP.Kq1MMaCkDoTJ3m9kx7GA9QHaKj?pid=ImgDet&rs=1"))
        //Collector Add Artist Activity - Type birth date in the form
        onView(withId(R.id.birthDate)).perform(scrollTo(), typeText("05/10/2023"))
        //Collector Add Artist Activity - Type birth date in the form
        onView(withId(R.id.description)).perform(scrollTo(), typeText("Bod Marley was an important Jamaican singer"))
        //Collector Add Artist Activity - Click button submit form
        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())
        //Check if dialog alert
        onView(withText("El artista ha sido creado exitosamente")).inRoot(isDialog()).check(matches(isDisplayed()))
    }

    @Test
    fun addArtistWrongDateFormatTest() {
        //Main Activity - Click button collector
        onView(withId(R.id.buttonCollector)).perform(scrollTo(), click())
        //Collector Home Activity - Click button add artist
        onView(withId(R.id.btnAddArtist)).perform(scrollTo(), click())
        //Collector Add Artist Activity - Type name in the form
        onView(withId(R.id.name)).perform(scrollTo(), typeText("Bob Marley"))
        //Collector Add Artist Activity - Type image in the form
        onView(withId(R.id.image)).perform(scrollTo(), typeText("https://th.bing.com/th/id/OIP.Kq1MMaCkDoTJ3m9kx7GA9QHaKj?pid=ImgDet&rs=1"))
        //Collector Add Artist Activity - Type birth date in the form
        onView(withId(R.id.birthDate)).perform(scrollTo(), typeText("23/23/2023"))
        //Collector Add Artist Activity - Type birth date in the form
        onView(withId(R.id.description)).perform(scrollTo(), typeText("Bod Marley was an important Jamaican singer"))
        //Check if alert
        onView(withText("El formato de la fecha ingresada debe ser mm/dd/aaaa")).check(matches(isDisplayed()))
    }

    @Test
    fun addArtistWrongImageFormatTest() {
        //Main Activity - Click button collector
        onView(withId(R.id.buttonCollector)).perform(scrollTo(), click())
        //Collector Home Activity - Click button add artist
        onView(withId(R.id.btnAddArtist)).perform(scrollTo(), click())
        //Collector Add Artist Activity - Type name in the form
        onView(withId(R.id.name)).perform(scrollTo(), typeText("Bob Marley"))
        //Collector Add Artist Activity - Type image in the form
        onView(withId(R.id.image)).perform(scrollTo(), typeText("imagen sin formato url"))
        //Collector Add Artist Activity - Type birth date in the form
        onView(withId(R.id.birthDate)).perform(scrollTo(), typeText("05/23/2023"))
        //Collector Add Artist Activity - Type birth date in the form
        onView(withId(R.id.description)).perform(scrollTo(), typeText("Bod Marley was an important Jamaican singer"))
        //Check if alert
        onView(withText("La url ingresada no es valida")).check(matches(isDisplayed()))
    }

    @Test
    fun addArtistEmptyNameTest() {
        //Main Activity - Click button collector
        onView(withId(R.id.buttonCollector)).perform(scrollTo(), click())
        //Collector Home Activity - Click button add artist
        onView(withId(R.id.btnAddArtist)).perform(scrollTo(), click())
        //Collector Add Artist Activity - Type image in the form
        onView(withId(R.id.image)).perform(scrollTo(), typeText("https://th.bing.com/th/id/OIP.Kq1MMaCkDoTJ3m9kx7GA9QHaKj?pid=ImgDet&rs=1"))
        //Collector Add Artist Activity - Type birth date in the form
        onView(withId(R.id.birthDate)).perform(scrollTo(), typeText("05/23/2023"))
        //Collector Add Artist Activity - Type birth date in the form
        onView(withId(R.id.description)).perform(scrollTo(), typeText("Bod Marley was an important Jamaican singer"))
        //Collector Add Artist Activity - Click button submit form
        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())
        //Check if alert
        onView(withText("El campo es requerido y no debe estar vacio")).check(matches(isDisplayed()))
    }

    @Test
    fun addArtistEmptyImageTest() {
        //Main Activity - Click button collector
        onView(withId(R.id.buttonCollector)).perform(scrollTo(), click())
        //Collector Home Activity - Click button add artist
        onView(withId(R.id.btnAddArtist)).perform(scrollTo(), click())
        //Collector Add Artist Activity - Type name in the form
        onView(withId(R.id.name)).perform(scrollTo(), typeText("Bob Marley"))
        //Collector Add Artist Activity - Type birth date in the form
        onView(withId(R.id.birthDate)).perform(scrollTo(), typeText("05/23/2023"))
        //Collector Add Artist Activity - Type birth date in the form
        onView(withId(R.id.description)).perform(scrollTo(), typeText("Bod Marley was an important Jamaican singer"))
        //Collector Add Artist Activity - Click button submit form
        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())
        //Check if dialog alert
        onView(withText("La imagen es requerida y no debe estar vacia")).check(matches(isDisplayed()))
    }

    @Test
    fun addArtistEmptyDateTest() {
        //Main Activity - Click button collector
        onView(withId(R.id.buttonCollector)).perform(scrollTo(), click())
        //Collector Home Activity - Click button add artist
        onView(withId(R.id.btnAddArtist)).perform(scrollTo(), click())
        //Collector Add Artist Activity - Type name in the form
        onView(withId(R.id.name)).perform(scrollTo(), typeText("Bob Marley"))
        //Collector Add Artist Activity - Type image in the form
        onView(withId(R.id.image)).perform(scrollTo(), typeText("https://th.bing.com/th/id/OIP.Kq1MMaCkDoTJ3m9kx7GA9QHaKj?pid=ImgDet&rs=1"))
        //Collector Add Artist Activity - Type birth date in the form
        onView(withId(R.id.description)).perform(scrollTo(), typeText("Bod Marley was an important Jamaican singer"))
        //Collector Add Artist Activity - Click button submit form
        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())
        //Check if alert
        onView(withText("El campo es requerido y no debe estar vacio")).check(matches(isDisplayed()))
    }

    @Test
    fun addArtistEmptyDescriptionTest() {
        //Main Activity - Click button collector
        onView(withId(R.id.buttonCollector)).perform(scrollTo(), click())
        //Collector Home Activity - Click button add artist
        onView(withId(R.id.btnAddArtist)).perform(scrollTo(), click())
        //Collector Add Artist Activity - Type name in the form
        onView(withId(R.id.name)).perform(scrollTo(), typeText("Bob Marley"))
        //Collector Add Artist Activity - Type image in the form
        onView(withId(R.id.image)).perform(scrollTo(), typeText("https://th.bing.com/th/id/OIP.Kq1MMaCkDoTJ3m9kx7GA9QHaKj?pid=ImgDet&rs=1"))
        //Collector Add Artist Activity - Type birth date in the form
        onView(withId(R.id.birthDate)).perform(scrollTo(), typeText("05/10/2023"))
        //Collector Add Artist Activity - Click button submit form
        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())
        //Check if alert
        onView(withText("El campo es requerido y no debe estar vacio")).check(matches(isDisplayed()))
    }
    @Test
    fun addArtistMaxNameCharactersTest() {
        //Main Activity - Click button collector
        onView(withId(R.id.buttonCollector)).perform(scrollTo(), click())
        //Collector Home Activity - Click button add artist
        onView(withId(R.id.btnAddArtist)).perform(scrollTo(), click())
        //Collector Add Artist Activity - Type name in the form
        onView(withId(R.id.name)).perform(scrollTo(), typeText("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"))
        //Collector Add Artist Activity - Type image in the form
        onView(withId(R.id.image)).perform(scrollTo(), typeText("https://th.bing.com/th/id/OIP.Kq1MMaCkDoTJ3m9kx7GA9QHaKj?pid=ImgDet&rs=1"))
        //Collector Add Artist Activity - Type birth date in the form
        onView(withId(R.id.birthDate)).perform(scrollTo(), typeText("05/10/2023"))
        //Collector Add Artist Activity - Type birth date in the form
        onView(withId(R.id.description)).perform(scrollTo(), typeText("Bod Marley was an important Jamaican singer"))
        //Collector Add Artist Activity - Click button submit form
        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())
        //Check if alert
        onView(withText("El campo es de maximo de 50 caracteres")).check(matches(isDisplayed()))
    }
    @Test
    fun addArtistMaxNameCharactersDescription() {
        //Main Activity - Click button collector
        onView(withId(R.id.buttonCollector)).perform(scrollTo(), click())
        //Collector Home Activity - Click button add artist
        onView(withId(R.id.btnAddArtist)).perform(scrollTo(), click())
        //Collector Add Artist Activity - Type name in the form
        onView(withId(R.id.name)).perform(scrollTo(), typeText("Bob Marley"))
        //Collector Add Artist Activity - Type image in the form
        onView(withId(R.id.image)).perform(scrollTo(), typeText("https://th.bing.com/th/id/OIP.Kq1MMaCkDoTJ3m9kx7GA9QHaKj?pid=ImgDet&rs=1"))
        //Collector Add Artist Activity - Type birth date in the form
        onView(withId(R.id.birthDate)).perform(scrollTo(), typeText("05/10/2023"))
        //Collector Add Artist Activity - Type birth date in the form
        onView(withId(R.id.description)).perform(scrollTo(), typeText("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"))
        //Collector Add Artist Activity - Click button submit form
        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())
        //Check if alert
        onView(withText("El campo es de maximo de 500 caracteres")).check(matches(isDisplayed()))
    }
}