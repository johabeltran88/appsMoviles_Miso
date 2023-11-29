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
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import android.app.Activity;
import android.os.SystemClock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

import com.example.test.model.Album;
import com.example.test.ui.CollectorListAlbums;
import com.example.test.ui.MainActivity;
import com.example.test.ui.VisitorListAlbumsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;
import java.util.UUID;

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

        onView(withId(R.id.textView5)).check(matches(withText(R.string.listado_de_lbumes)));
    }

    @Test
    public void listarAlbumVisitorTest(){
        ViewInteraction collectorBtn = onView(allOf(withId(R.id.buttonVisitor), isDisplayed()));
        collectorBtn.perform(click());

        ViewInteraction listarAlbumBtn = onView(allOf(withId(R.id.btnListAlbum), isDisplayed()));
        listarAlbumBtn.perform(click());

        onView(withText(R.string.listado_de_lbumes)).check(matches(isDisplayed()));
    }

    @Test
    public void createAndListCollectorTest() {
        String nombre = "A"+ UUID.randomUUID().toString();

        ViewInteraction collectorBtn = onView(allOf(withId(R.id.buttonCollector), isDisplayed()));
        collectorBtn.perform(click());

        ViewInteraction addAlbum = onView(allOf(withId(R.id.btnAddAlbum), isDisplayed()));
        addAlbum.perform(click());

        ViewInteraction albumNombre = onView(withId(R.id.name));
        albumNombre.perform(scrollTo(), replaceText(nombre), closeSoftKeyboard());

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

        ViewInteraction confirmBtn = onView(allOf(withId(R.id.btnSubmit)));
        confirmBtn.perform(scrollTo(), click());

        onView(withText(R.string.aceptar)).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());

        ViewInteraction atrasBtn = onView(allOf(withId(R.id.btnCancel), withText(R.string.cancelar)));
        atrasBtn.perform(scrollTo(), click());

        ViewInteraction listarAlbumBtn = onView(allOf(withId(R.id.buttonGetAlbums), isDisplayed()));
        listarAlbumBtn.perform(scrollTo(), click());

        CollectorListAlbums collectorListAlbumsActivity = getCurrentActivity();
        SystemClock.sleep(2000);

        int position = 0;
        for (Album artist : collectorListAlbumsActivity.albumAdapter.getAlbums()) {
            if (Objects.equals(artist.getName(), nombre)) {
                break;
            }
            position += 1;
        }

        onView(withId(R.id.recycler_view_albums))
                .perform(RecyclerViewActions.scrollToPosition(position));
        SystemClock.sleep(2000);

        onView(withText(nombre)).check(
                matches(isDisplayed())
        );
    }

    @Test
    public void createAndListVisitorTest() {
        String nombre = "A"+ UUID.randomUUID().toString();

        ViewInteraction collectorBtn = onView(allOf(withId(R.id.buttonCollector), isDisplayed()));
        collectorBtn.perform(click());

        ViewInteraction addAlbum = onView(allOf(withId(R.id.btnAddAlbum), isDisplayed()));
        addAlbum.perform(click());

        ViewInteraction albumNombre = onView(withId(R.id.name));
        albumNombre.perform(scrollTo(), replaceText(nombre), closeSoftKeyboard());

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

        ViewInteraction confirmBtn = onView(allOf(withId(R.id.btnSubmit)));
        confirmBtn.perform(scrollTo(), click());

        onView(withText(R.string.aceptar)).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());

        ViewInteraction atrasBtn = onView(allOf(withId(R.id.btnCancel), withText(R.string.cancelar)));
        atrasBtn.perform(scrollTo(), click());

        ViewInteraction navigateUpButton = Espresso.onView(ViewMatchers.withContentDescription("Navigate up"));
        navigateUpButton.perform(ViewActions.click());

        ViewInteraction visitorBtn = onView(allOf(withId(R.id.buttonVisitor), isDisplayed()));
        visitorBtn.perform(click());

        ViewInteraction listarAlbumBtn = onView(allOf(withId(R.id.btnListAlbum), isDisplayed()));
        listarAlbumBtn.perform(click());

        VisitorListAlbumsActivity visitorListAlbumsActivity = getCurrentActivity();
        SystemClock.sleep(2000);

        int position = 0;
        for (Album artist : visitorListAlbumsActivity.albumAdapter.getAlbums()) {
            if (Objects.equals(artist.getName(), nombre)) {
                break;
            }
            position += 1;
        }

        onView(withId(R.id.recycler_view_albums))
                .perform(RecyclerViewActions.scrollToPosition(position));
        SystemClock.sleep(2000);

        onView(withText(nombre)).check(
                matches(isDisplayed())
        );
    }

    private <T extends AppCompatActivity> T getCurrentActivity() {
        final Activity[] activity = new Activity[1];

        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            Iterable<Activity> activities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
            if (activities != null) {
                activity[0] = activities.iterator().next();
            }
        });

        return (T) activity[0];
    }
}
