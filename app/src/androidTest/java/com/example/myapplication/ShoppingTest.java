package com.example.myapplication;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.rule.ActivityTestRule;

import com.example.myapplication.activities.Shopping;
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

public class ShoppingTest {

    @Rule
    public ActivityTestRule<Shopping> mShoppingRuleTest = new ActivityTestRule<>(Shopping.class);
    Shopping mShopping = null;
    Instrumentation.ActivityMonitor shoppingFinalizeMonitor = getInstrumentation().addMonitor(ShoppingFinalize.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {

        mShopping = mShoppingRuleTest.getActivity();
    }

    @Test
    public void testLanuchOfShoppingFinalize(){
        assertNotNull(mShopping.findViewById(R.id.btnEndShopping));
        onView(withId(R.id.btnEndShopping)).perform(click());
        Activity shoppingFinalize = getInstrumentation().waitForMonitorWithTimeout(shoppingFinalizeMonitor,5000);
        assertNotNull(shoppingFinalize);
        shoppingFinalize.finish();
    }
    


    @After
    public void tearDown() throws Exception {
    }

}

