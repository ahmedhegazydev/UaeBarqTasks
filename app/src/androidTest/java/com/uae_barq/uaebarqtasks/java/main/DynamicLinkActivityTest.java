package com.uae_barq.uaebarqtasks.java.main;

import androidx.test.core.app.ActivityScenario;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.uae_barq.uaebarqtasks.R;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4ClassRunner.class)
public class DynamicLinkActivityTest {

    @Test
    public void testMainViewContainer() {
        ActivityScenario activityScenario = ActivityScenario.launch(DynamicLinkActivity.class);

        //testing visibility of main activity layout view
        onView(withId(R.id.main_container)).check(matches(isDisplayed()));

    }

    @Test
    public void testVisibilityOfCardAndChildViews() {

        ActivityScenario activityScenario = ActivityScenario.launch(DynamicLinkActivity.class);

        //testing visibility of some views
        onView(withId(R.id.childCard)).check(matches(isDisplayed()));
        onView(withId(R.id.tvCapFname)).check(matches(withEffectiveVisibility(VISIBLE)));
        onView(withId(R.id.tvCapLname)).check(matches(withEffectiveVisibility(VISIBLE)));
        onView(withId(R.id.tvCapAge)).check(matches(isDisplayed()));
        onView(withId(R.id.tvCapPhone)).check(matches(withEffectiveVisibility(VISIBLE)));
        onView(withId(R.id.tvCapCountry)).check(matches(withEffectiveVisibility(VISIBLE)));

        //testing the texts matching
        onView(withId(R.id.tvCapFname)).check(matches(withText(R.string.firstname)));
        onView(withId(R.id.tvCapLname)).check(matches(withText(R.string.lastname)));
        onView(withId(R.id.tvCapAge)).check(matches(withText(R.string.age)));
        onView(withId(R.id.tvCapPhone)).check(matches(withText(R.string.phone)));
        onView(withId(R.id.tvCapCountry)).check(matches(withText(R.string.country)));


    }

}