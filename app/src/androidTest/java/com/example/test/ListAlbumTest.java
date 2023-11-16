package com.example.test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.instanceOf;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.test.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ListAlbumTest {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void listarAlbumCollecctorTest(){
        ViewInteraction collectorBtn = onView(allOf(withId(R.id.buttonCollector), isDisplayed()));
        collectorBtn.perform(click());

        ViewInteraction listarAlbumBtn = onView(allOf(withId(R.id.buttonGetAlbums), isDisplayed()));
        listarAlbumBtn.perform(scrollTo(), click());

        onView(withId(R.id.textView5)).check(matches(withText("Listado de álbumes")));
    }

    @Test
    public void listarAlbumVisitorTest(){
        ViewInteraction collectorBtn = onView(allOf(withId(R.id.buttonVisitor), isDisplayed()));
        collectorBtn.perform(click());

        ViewInteraction listarAlbumBtn = onView(allOf(withId(R.id.btnListAlbum), isDisplayed()));
        listarAlbumBtn.perform(click());

        onView(withText("Listado de álbumes")).check(matches(isDisplayed()));
    }

    @Test
    public void createAndListCollectorTest() {
        ViewInteraction collectorBtn = onView(allOf(withId(R.id.buttonCollector), withText("Coleccionista"), isDisplayed()));
        collectorBtn.perform(click());

        ViewInteraction addAlbum = onView(allOf(withId(R.id.btnAddAlbum), withText("Agregar Álbum"), isDisplayed()));
        addAlbum.perform(click());

        ViewInteraction albumNombre = onView(withId(R.id.name));
        albumNombre.perform(scrollTo(), replaceText("a aabbccdd"), closeSoftKeyboard());

        ViewInteraction imagenAlbum = onView(withId(R.id.image));
        imagenAlbum.perform(scrollTo(), replaceText("https://i.scdn.co/image/ab67616d0000b2732813f4432008eca545c53626"), closeSoftKeyboard());

        ViewInteraction lanzamiento = onView(withId(R.id.releaseDate));
        lanzamiento.perform(scrollTo(), replaceText("12/15/1985"), closeSoftKeyboard());

        onView(withId(R.id.genre)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Classical"))).perform(click());

        onView(withId(R.id.recordLabel)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("EMI"))).perform(click());

        ViewInteraction description = onView(withId(R.id.description));
        description.perform(scrollTo(), replaceText("Los clasicazos pesados del Maestro Pastor Lopez"), closeSoftKeyboard());

        ViewInteraction confirmBtn = onView(allOf(withId(R.id.btnSubmit), withText("Agregar")));
        confirmBtn.perform(scrollTo(), click());

        onView(withText("Aceptar")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());

        ViewInteraction atrasBtn = onView(allOf(withId(R.id.btnCancel), withText("Cancelar")));
        atrasBtn.perform(scrollTo(), click());

        ViewInteraction listarAlbumBtn = onView(allOf(withId(R.id.buttonGetAlbums), isDisplayed()));
        listarAlbumBtn.perform(scrollTo(), click());

        onView(withText("AABC")).check(matches(isDisplayed()));
    }

    @Test
    public void createAndListVisitorTest() {
        ViewInteraction collectorBtn = onView(allOf(withId(R.id.buttonCollector), withText("Coleccionista"), isDisplayed()));
        collectorBtn.perform(click());

        ViewInteraction addAlbum = onView(allOf(withId(R.id.btnAddAlbum), withText("Agregar Álbum"), isDisplayed()));
        addAlbum.perform(click());

        ViewInteraction albumNombre = onView(withId(R.id.name));
        albumNombre.perform(scrollTo(), replaceText("a aabbccdd"), closeSoftKeyboard());

        ViewInteraction imagenAlbum = onView(withId(R.id.image));
        imagenAlbum.perform(scrollTo(), replaceText("https://i.scdn.co/image/ab67616d0000b2732813f4432008eca545c53626"), closeSoftKeyboard());

        ViewInteraction lanzamiento = onView(withId(R.id.releaseDate));
        lanzamiento.perform(scrollTo(), replaceText("12/15/1985"), closeSoftKeyboard());

        onView(withId(R.id.genre)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Classical"))).perform(click());

        onView(withId(R.id.recordLabel)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("EMI"))).perform(click());

        ViewInteraction description = onView(withId(R.id.description));
        description.perform(scrollTo(), replaceText("Los clasicazos pesados del Maestro Pastor Lopez"), closeSoftKeyboard());

        ViewInteraction confirmBtn = onView(allOf(withId(R.id.btnSubmit), withText("Agregar")));
        confirmBtn.perform(scrollTo(), click());

        onView(withText("Aceptar")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());

        ViewInteraction atrasBtn = onView(allOf(withId(R.id.btnCancel), withText("Cancelar")));
        atrasBtn.perform(scrollTo(), click());

        //COMO VOLVER?

        //ViewInteraction listarAlbumBtn = onView(allOf(withId(R.id.btnListAlbum), isDisplayed()));
        //listarAlbumBtn.perform(scrollTo(), click());

        //onView(withText("AABC")).check(matches(isDisplayed()));
    }
}
