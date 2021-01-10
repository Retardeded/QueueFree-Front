package com.example.myapplication;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Bundle;
import android.view.View;

import androidx.test.espresso.ViewInteraction;
import androidx.test.rule.ActivityTestRule;

import com.example.myapplication.activities.DashBoard;
import com.example.myapplication.activities.Register;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static java.util.EnumSet.allOf;
import static org.junit.Assert.*;

public class RegisterTest {

    @Rule
    public ActivityTestRule<Register> mRegisterRuleTest = new ActivityTestRule<Register>(Register.class);
    Register mRegister = null;
    Instrumentation.ActivityMonitor dashBoardMonitor = getInstrumentation().addMonitor(DashBoard.class.getName(), null, false);


    @Before
    public void setUp() throws Exception {

        mRegister = mRegisterRuleTest.getActivity();
    }

    @Test
    public void testLanuchOfDashBoard(){

        String user = "this";
        String password = "guy";
        mRegister.user = user;
        mRegister.password = password;
        mRegister.loadDashboard();

        Activity dashBoard = getInstrumentation().waitForMonitorWithTimeout(dashBoardMonitor,5000);
        assertNotNull(dashBoard);

        Bundle bundle = dashBoard.getIntent().getExtras();

        String[] userInfo = bundle.getString("userInfo").split(" ");

        boolean condtion = userInfo[0].equals(user) && userInfo[1].equals(password);
        Assert.assertTrue(condtion);
        dashBoard.finish();
        //mRegister.
    }

    @Test
    public void goodRegisterDataTest() {

        mRegister.user = "som";
        mRegister.password = "som";
        mRegister.confirmPassword = "som";
        mRegister.fullName = "som";

        Assert.assertTrue(mRegister.validateInputs());
    }

    @Test
    public void emptyNameTest() {

        mRegister.setUpEditTexts();
        mRegister.user = "";
        mRegister.password = "som";
        mRegister.confirmPassword = "som";
        mRegister.fullName = "som";

        Assert.assertFalse(mRegister.validateInputs());
    }

    @Test
    public void emptyPasswordTest() {

        mRegister.setUpEditTexts();
        mRegister.user = "som";
        mRegister.password = "";
        mRegister.confirmPassword = "som";
        mRegister.fullName = "som";

        Assert.assertFalse(mRegister.validateInputs());
    }

    @Test
    public void passwordsDontMatchTest() {

        mRegister.setUpEditTexts();
        mRegister.user = "som";
        mRegister.password = "som";
        mRegister.confirmPassword = "notsom";
        mRegister.fullName = "som";

        Assert.assertFalse(mRegister.validateInputs());
    }



    


    @After
    public void tearDown() throws Exception {
    }

}

