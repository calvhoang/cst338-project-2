package com.example.restauranttracker;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.restauranttracker", appContext.getPackageName());
    }
    @Rule
    public IntentsTestRule<LoginActivity> intentsTestRule = new IntentsTestRule<>(LoginActivity.class);


    @Test
    public void navigateFromLogin_ToSignIn_ToMain() {
        // Click the button to start SignUpActivity
        onView(withId(R.id.registerButton)).perform(click());
        // Verify an Intent was sent to start SignUpActivity
        intended(toPackage(SignUpActivity.class.getPackageName()));

        // Click the button to start to MainActivity
        onView(withId(R.id.signUpButton)).perform(click());
        // Verify an Intent was sent to start MainActivity
        intended(toPackage(MainActivity.class.getPackageName()));

    }
}