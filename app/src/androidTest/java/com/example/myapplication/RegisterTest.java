package com.example.myapplication;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Bundle;
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

public class RegisterTest {

    @Rule
    public ActivityTestRule<Register> mRegisterRuleTest = new ActivityTestRule<Register>(Register.class);

    Register mRegister = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(DashBoard.class.getName(), null, false);


    @Before
    public void setUp() throws Exception {

        mRegister = mRegisterRuleTest.getActivity();
    }

    @Test
    public void testLanuchOfDashBoard(){

        mRegister.user = "1";
        mRegister.password = "2";
        mRegister.loadDashboard();

        Activity dashBoard = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNotNull(dashBoard);

        Bundle bundle = dashBoard.getIntent().getExtras();

        String[] userInfo = bundle.getString("userInfo").split(" ");

        boolean condtion = userInfo[0].equals("1") && userInfo[1].equals("2");

        Assert.assertTrue(condtion);

        dashBoard.finish();
        //mRegister.
    }

    @Test
    public void testLanuch()
    {
        View view = mRegister.findViewById(R.id.etUsername);
        assertNotNull(view);
    }


    @Test
    public void emptyNameTest() {

        mRegister.user = "";
        mRegister.password = "som";
        mRegister.confirmPassword = "som";
        mRegister.fullName = "som";

        Assert.assertFalse(mRegister.validateInputs());
    }

    


    @After
    public void tearDown() throws Exception {
    }

}

