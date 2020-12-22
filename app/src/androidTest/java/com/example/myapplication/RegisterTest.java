package com.example.myapplication;

import android.app.Activity;
import android.app.Instrumentation;
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
        /*
        assertNotNull(mRegister.findViewById(R.id.btnRegister));
        onView(withId(R.id.btnRegister)).perform(click());

        EditText etUsername = mRegister.findViewById(R.id.etUsername);
        etUsername.setText("Som1");

        Activity dashBoard = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);

        assertNotNull(dashBoard);

        dashBoard.finish();
        //mRegister.
         */
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

/*

@Test
    public void emptyNameTest() {

        mRegister = mRegisterRuleTest.getActivity();

        mRegister.user = "";
        mRegister.password = "som";
        mRegister.confirmPassword = "som";
        mRegister.fullName = "som";

        Assert.assertFalse(mRegister.validateInputs());
    }

@Test
    public void onCreate() {
    }

    @Test
    public void validateInputs() {
    }

package com.example.myapplication;

        import org.junit.After;
        import org.junit.Assert;
        import org.junit.Before;
        import org.junit.Rule;
        import org.junit.Test;

        import static org.junit.Assert.*;

public class RegisterTest {

    //String user;
    //String password;
    //String confirmPassword;
    //String fullName;


    @Rule
    public ActivityTestRule<Register> mRuleTest = new ActivityTestRule<Register>(Register.class);

    @Before
    public void setUp() throws Exception {

    }

    Register register = new Register();
    @Test
    public void emptyNameTest() {

        register.user = "";
        register.password = "som";
        register.confirmPassword = "som";
        register.fullName = "som";

        Assert.assertFalse(register.validateInputs());
    }

    @After
    public void tearDown() throws Exception {
    }

 }

 */
