package com.example.myapplication;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class DashBoardTest {

    @Rule
    public ActivityTestRule<DashBoard> mActivityRuleTest = new ActivityTestRule<DashBoard>(DashBoard.class);

    @Rule
    public ActivityTestRule<Register> mRegisterRuleTest = new ActivityTestRule<Register>(Register.class);

    DashBoard mActivity = null;
    Register registerActivty = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(MainActivity.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {

        mActivity = mActivityRuleTest.getActivity();
        registerActivty = mRegisterRuleTest.getActivity();
    }

    @Test
    public void testLaunchOfLogin(){

        assertNotNull(mActivity.findViewById(R.id.btnGoLogIn));
        onView(withId(R.id.btnGoLogIn)).perform(click());

        Activity login = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);

        assertNotNull(login);

    }

    @After
    public void tearDown() throws Exception {
    }
}