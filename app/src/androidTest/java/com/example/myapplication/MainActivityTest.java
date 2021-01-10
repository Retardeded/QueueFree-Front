package com.example.myapplication;

import android.app.Activity;
import android.app.Instrumentation;
import android.widget.TextView;

import androidx.test.rule.ActivityTestRule;

import com.example.myapplication.activities.MainActivity;

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

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(MainPanel.class.getName(), null, false);

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

    @Test
    public void testLogIn(){

        assertNotNull(mActivity.findViewById(R.id.btnLogin));

        TextView loginText = mActivity.findViewById(R.id.etLoginUsername);
        TextView passwordText = mActivity.findViewById(R.id.etLoginPassword);

        mActivity.createClient();

        loginText.setText("user");
        passwordText.setText("haslo");

        onView(withId(R.id.btnLogin)).perform(click());

        Activity mainPanel = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);

        assertNotNull(mainPanel);

    }

    @Test
    public void emptyNameTest() {

        mActivity.user = "";
        mActivity.password = "som";

        Assert.assertFalse(mActivity.validateInputs());
    }

    @Test
    public void emptyPasswordTest() {

        mActivity.user = "som";
        mActivity.password = "";

        Assert.assertFalse(mActivity.validateInputs());
    }

    @Test
    public void nonEmptyLoginDataTest() {

        mActivity.user = "som";
        mActivity.password = "som";

        Assert.assertTrue(mActivity.validateInputs());
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