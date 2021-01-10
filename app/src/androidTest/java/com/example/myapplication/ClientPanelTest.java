package com.example.myapplication;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.rule.ActivityTestRule;

import com.example.myapplication.activities.ClientPanel;
import com.example.myapplication.activities.MainActivity;
import com.example.myapplication.activities.MainPanel;
import com.example.myapplication.activities.Shopping;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public class ClientPanelTest {

    @Rule
    public ActivityTestRule<ClientPanel> mClientPanelRuleTest = new ActivityTestRule<>(ClientPanel.class);
    ClientPanel mClientPanel = null;
    Instrumentation.ActivityMonitor mainPanelMonitor = getInstrumentation().addMonitor(MainPanel.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {

        mClientPanel = mClientPanelRuleTest.getActivity();
    }

    @Test
    public void testLanuchOfMainPanel(){
        assertNotNull(mClientPanel.findViewById(R.id.btnGoToMainPanel));
        onView(withId(R.id.btnGoToMainPanel)).perform(click());
        Activity mainPanel = getInstrumentation().waitForMonitorWithTimeout(mainPanelMonitor,5000);
        assertNotNull(mainPanel);
        mainPanel.finish();
    }
    


    @After
    public void tearDown() throws Exception {
    }

}

