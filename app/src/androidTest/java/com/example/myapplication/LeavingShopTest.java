package com.example.myapplication;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.rule.ActivityTestRule;

import com.example.myapplication.activities.LeavingShop;
import com.example.myapplication.activities.MainPanel;
import com.example.myapplication.activities.ShoppingFinalize;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public class LeavingShopTest {

    @Rule
    public ActivityTestRule<LeavingShop> mShoppingFinalizeRuleTest = new ActivityTestRule<>(LeavingShop.class);
    LeavingShop mLeavingShop = null;
    Instrumentation.ActivityMonitor mainPanelMonitor = getInstrumentation().addMonitor(MainPanel.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {

        mLeavingShop = mShoppingFinalizeRuleTest.getActivity();
    }

    @Test
    public void testLanuchOfMainPanel(){
        mLeavingShop.launchMainPanel();
        Activity maiPanel = getInstrumentation().waitForMonitorWithTimeout(mainPanelMonitor,5000);
        assertNotNull(maiPanel);
        maiPanel.finish();
    }



    @After
    public void tearDown() throws Exception {
    }

}

