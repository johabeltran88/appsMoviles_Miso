package com.misolutions.vinilos.ui.activities;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.test.ui.MainActivity;
import com.example.test.R;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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


@LargeTest
@RunWith(AndroidJUnit4.class)
public class CrearAlbumTest {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void crearAlbumCollecctorTest() {
        ViewInteraction collectorBtn = onView(allOf(withId(R.id.buttonCollector), withText("Coleccionista"),isDisplayed()));
        collectorBtn.perform(click());

        ViewInteraction addAlbum = onView(allOf(withId(R.id.btnAddAlbum), withText("Agregar Álbum"),isDisplayed()));
        addAlbum.perform(click());

        ViewInteraction albumNombre = onView(withId(R.id.name));
        albumNombre.perform(scrollTo(), replaceText("Lo mejor de Pastor Lopez"), closeSoftKeyboard());

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

        onView(withText("El album ha sido creado exitosamente")).inRoot(isDialog()).check(matches(isDisplayed()));



    }

    @Test
    public void crearAlbumConNombreIncorrectoTest() {
        // Click on the "Coleccionista" button
        ViewInteraction collectorBtn = onView(allOf(withId(R.id.buttonCollector), withText("Coleccionista"), isDisplayed()));
        collectorBtn.perform(click());

        // Click on the "Agregar Álbum" button
        ViewInteraction addAlbum = onView(allOf(withId(R.id.btnAddAlbum), withText("Agregar Álbum"), isDisplayed()));
        addAlbum.perform(click());

        // Enter an incorrect album name
        ViewInteraction albumNombre = onView(withId(R.id.name));
        albumNombre.perform(scrollTo(), replaceText(""), closeSoftKeyboard());

        // Enter an album image URL
        ViewInteraction imagenAlbum = onView(withId(R.id.image));
        imagenAlbum.perform(scrollTo(), replaceText("https://i.scdn.co/image/ab67616d0000b2732813f4432008eca545c53626"), closeSoftKeyboard());

        // Check for the expected error message or behavior
        onView(withText("El campo es requerido y no debe estar vacio")).check(matches(isDisplayed()));
    }

    @Test
    public void  crearAlbumURLvacia() {
        // Click on the "Coleccionista" button
        ViewInteraction collectorBtn = onView(allOf(withId(R.id.buttonCollector), withText("Coleccionista"), isDisplayed()));
        collectorBtn.perform(click());

        // Click on the "Agregar Álbum" button
        ViewInteraction addAlbum = onView(allOf(withId(R.id.btnAddAlbum), withText("Agregar Álbum"), isDisplayed()));
        addAlbum.perform(click());

        // Enter an album name
        ViewInteraction albumNombre = onView(withId(R.id.name));
        albumNombre.perform(scrollTo(), replaceText("Los 14 cañonazos Bailables vol 25"), closeSoftKeyboard());

        // Enter an incorrect album image URL
        ViewInteraction imagenAlbum = onView(withId(R.id.image));
        imagenAlbum.perform(scrollTo(), replaceText(""), closeSoftKeyboard());

        ViewInteraction lanzamiento = onView(withId(R.id.releaseDate));
        lanzamiento.perform(scrollTo(), replaceText("12/15/1985"), closeSoftKeyboard());

        // Check for the expected error message or behavior
        onView(withText("La imagen es requerida y no debe estar vacia")).check(matches(isDisplayed()));

    }

    @Test
    public void  crearAlbumURLinvalida() {
        // Click on the "Coleccionista" button
        ViewInteraction collectorBtn = onView(allOf(withId(R.id.buttonCollector), withText("Coleccionista"), isDisplayed()));
        collectorBtn.perform(click());

        // Click on the "Agregar Álbum" button
        ViewInteraction addAlbum = onView(allOf(withId(R.id.btnAddAlbum), withText("Agregar Álbum"), isDisplayed()));
        addAlbum.perform(click());

        // Enter an album name
        ViewInteraction albumNombre = onView(withId(R.id.name));
        albumNombre.perform(scrollTo(), replaceText("Los 14 cañonazos Bailables vol 25"), closeSoftKeyboard());

        // Enter an incorrect album image URL
        ViewInteraction imagenAlbum = onView(withId(R.id.image));
        imagenAlbum.perform(scrollTo(), replaceText("Esta no es una URL válida"), closeSoftKeyboard());

        // Check for the expected error message or behavior
        onView(withText("La url ingresada no es valida")).check(matches(isDisplayed()));

    }

    // Agregar una fecha vacia
    @Test
    public void  crearAlbumfechavacia() {
        // Click on the "Coleccionista" button
        ViewInteraction collectorBtn = onView(allOf(withId(R.id.buttonCollector), withText("Coleccionista"), isDisplayed()));
        collectorBtn.perform(click());

        // Click on the "Agregar Álbum" button
        ViewInteraction addAlbum = onView(allOf(withId(R.id.btnAddAlbum), withText("Agregar Álbum"), isDisplayed()));
        addAlbum.perform(click());

        // Enter an album name
        ViewInteraction albumNombre = onView(withId(R.id.name));
        albumNombre.perform(scrollTo(), replaceText("Los 14 cañonazos Bailables vol 25"), closeSoftKeyboard());

        // Enter an incorrect album image URL
        ViewInteraction imagenAlbum = onView(withId(R.id.image));
        imagenAlbum.perform(scrollTo(), replaceText("https://i.scdn.co/image/ab67616d0000b2732813f4432008eca545c53626"), closeSoftKeyboard());

        //Ingresamos fecha vacia
        ViewInteraction lanzamiento = onView(withId(R.id.releaseDate));
        lanzamiento.perform(scrollTo(), replaceText(""), closeSoftKeyboard());

        ViewInteraction description = onView(withId(R.id.description));
        description.perform(scrollTo(), replaceText("Solo clasicazos pesados del Maestro Pastor Lopez"), closeSoftKeyboard());

        // Check for the expected error message or behavior
        onView(withText("El campo es requerido y no debe estar vacio")).check(matches(isDisplayed()));

    }



}
