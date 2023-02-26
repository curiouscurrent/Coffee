package com.anusha.coffee;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.debug.testing.DebugAppCheckTestHelper;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.anusha.coffee", appContext.getPackageName());

    }
    public class YourTests {
        private final DebugAppCheckTestHelper debugAppCheckTestHelper =
                DebugAppCheckTestHelper.fromInstrumentationArgs();

        @Test
        public void testWithDefaultApp() {
            debugAppCheckTestHelper.withDebugProvider(() -> {
                // Test code that requires a debug AppCheckToken.
            });
        }

        @Test
        public void testWithNonDefaultApp() {
            debugAppCheckTestHelper.withDebugProvider(
                    FirebaseApp.getInstance("nonDefaultApp"),
                    () -> {
                        // Test code that requires a debug AppCheckToken.
                    });
        }
    }

}