package com.example.myapplication;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;
import android.widget.EditText;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRuleTest = new ActivityTestRule<MainActivity>(MainActivity.class);

    MainActivity mActivity = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(Register.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {

        mActivity = mActivityRuleTest.getActivity();
    }


    @Test
    public void testLanuchOfRegister(){

        assertNotNull(mActivity.findViewById(R.id.btnDoRegister));
        onView(withId(R.id.btnDoRegister)).perform(click());

        Activity register = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);

        assertNotNull(register);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void onCreate() {
    }

    @Test
    public void doRegister() {
    }

    @Test
    public void logIn() {
    }
}