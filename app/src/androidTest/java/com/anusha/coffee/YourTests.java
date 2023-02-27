package com.anusha.coffee;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.debug.testing.DebugAppCheckTestHelper;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
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
