package com.example.myapplication;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.rule.ActivityTestRule;

import com.example.myapplication.activities.LeavingShop;
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

public class ShoppingFinalizeTest {

    @Rule
    public ActivityTestRule<ShoppingFinalize> mShoppingFinalizeRuleTest = new ActivityTestRule<>(ShoppingFinalize.class);
    ShoppingFinalize mShoppingFinalize = null;
    Instrumentation.ActivityMonitor leavingShopMonitor = getInstrumentation().addMonitor(LeavingShop.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {

        mShoppingFinalize = mShoppingFinalizeRuleTest.getActivity();
    }

    @Test
    public void testLanuchOfLeavingShop(){
        mShoppingFinalize.launchLeavingShop();
        Activity shoppingFinalize = getInstrumentation().waitForMonitorWithTimeout(leavingShopMonitor,5000);
        assertNotNull(shoppingFinalize);
        shoppingFinalize.finish();
    }
    


    @After
    public void tearDown() throws Exception {
    }

}

