package com.example.myapplication;

import android.app.Activity;
import android.app.Instrumentation;
import android.widget.TextView;

import androidx.test.rule.ActivityTestRule;

import com.example.myapplication.activities.DashBoard;
import com.example.myapplication.activities.MainActivity;
import com.example.myapplication.activities.MainPanel;
import com.example.myapplication.activities.Register;

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
    public ActivityTestRule<MainActivity> mActivityRuleTest = new ActivityTestRule<>(MainActivity.class);

    MainActivity mActivity = null;

    Instrumentation.ActivityMonitor registerMonitor = getInstrumentation().addMonitor(Register.class.getName(), null, false);
    Instrumentation.ActivityMonitor mainPanelMonitor = getInstrumentation().addMonitor(MainPanel.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {

        mActivity = mActivityRuleTest.getActivity();
    }

    @Test
    public void testLanuchOfRegister(){

        assertNotNull(mActivity.findViewById(R.id.btnDoRegister));
        onView(withId(R.id.btnDoRegister)).perform(click());
        Activity register = getInstrumentation().waitForMonitorWithTimeout(registerMonitor,5000);
        assertNotNull(register);
        register.finish();
    }

    @Test
    public void testLanuchOfMainPanel(){

        mActivity.lanuchOfMainPanel();
        Activity mainPanel = getInstrumentation().waitForMonitorWithTimeout(mainPanelMonitor,5000);
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

    @Test
    public void autoFillTest() {

        assertNotNull(mActivity.findViewById(R.id.etLoginUsername));
        assertNotNull(mActivity.findViewById(R.id.etLoginPassword));

        TextView loginText = mActivity.findViewById(R.id.etLoginUsername);
        TextView passwordText = mActivity.findViewById(R.id.etLoginPassword);

        String login = "this";
        String password = "guy";

        mActivity.autoFillCredentials(login, password);
        Assert.assertEquals(loginText.getText().toString(), login);
        Assert.assertEquals(passwordText.getText().toString(), password);
    }


    @After
    public void tearDown() throws Exception {
    }

}