package com.uae_barq.uaebarqtasks.main;

import androidx.test.core.app.ActivityScenario;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.uae_barq.uaebarqtasks.R;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4ClassRunner.class)
public class DynamicLinkActivityTest {

    @Test
    public void testMainViewContainer() {
        ActivityScenario activityScenario = ActivityScenario.launch(DynamicLinkActivity.class);

        onView(withId(R.id.main_container)).check(matches(isDisplayed()));

    }

    @Test
    public void testVisibilityOfCardAndChildViews() {

        ActivityScenario activityScenario = ActivityScenario.launch(DynamicLinkActivity.class);

        onView(withId())

    }

}