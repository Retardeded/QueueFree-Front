package com.example.myapplication;

import android.app.Activity;
import android.app.Instrumentation;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.test.rule.ActivityTestRule;

import com.example.myapplication.activities.ClientPanel;
import com.example.myapplication.activities.DashBoard;
import com.example.myapplication.activities.MainActivity;
import com.example.myapplication.activities.MainPanel;
import com.example.myapplication.activities.Shopping;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public class MainPanelTest {

    @Rule
    public ActivityTestRule<MainPanel> mMainPanelRuleTest = new ActivityTestRule<>(MainPanel.class);
    public ActivityTestRule<MainActivity> mMainActivtyRuleTest = new ActivityTestRule<>(MainActivity.class);
    MainPanel mMainPanel = null;
    MainActivity mMainActivity = null;
    Instrumentation.ActivityMonitor clientPanelMonitor = getInstrumentation().addMonitor(ClientPanel.class.getName(), null, false);
    Instrumentation.ActivityMonitor shoppingMonitor = getInstrumentation().addMonitor(Shopping.class.getName(), null, false);
    Instrumentation.ActivityMonitor mainActivityMonitor = getInstrumentation().addMonitor(MainActivity.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {

        mMainPanel = mMainPanelRuleTest.getActivity();
        mMainActivity = mMainActivtyRuleTest.getActivity();
    }

    @Test
    public void testLanuchOfClientPanel(){
        assertNotNull(mMainPanel.findViewById(R.id.btnClientPanel));
        onView(withId(R.id.btnClientPanel)).perform(click());
        Activity clientPanel = getInstrumentation().waitForMonitorWithTimeout(clientPanelMonitor,5000);
        assertNotNull(clientPanel);
        clientPanel.finish();
    }

    @Test
    public void testLanuchOfShopping(){
        mMainPanel.lanuchOfShopping();
        Activity shopping = getInstrumentation().waitForMonitorWithTimeout(shoppingMonitor,5000);
        assertNotNull(shopping);
    }

    @Test
    public void testLanuchOfMainActivty(){
        mMainPanel.lanuchOfMainActivty();
        Activity mainActivty = getInstrumentation().waitForMonitorWithTimeout(mainActivityMonitor,5000);
        assertNotNull(mainActivty);
    }

    /*
    @Test
    public void testShowQr(){
        assertNotNull(mMainPanel.findViewById(R.id.qrImg));
        ImageView imageView = mMainPanel.findViewById(R.id.qrImg);
        Matrix matrix = imageView.getImageMatrix();
        mMainPanel.showQr("1");
        Assert.assertFalse(matrix == imageView.getImageMatrix());
    }

     */


    @After
    public void tearDown() throws Exception {
    }

}

