package com.example.main;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.example.core.data.source.remote.CallApi;
import com.example.main.data.source.remote.WebServiceMain;

import org.junit.Assert;
import org.junit.Rule;

import br.com.concretesolutions.requestmatcher.InstrumentedTestRequestMatcherRule;
import br.com.concretesolutions.requestmatcher.RequestMatcherRule;

public class BaseInstrumentedTest {

    private final static int DEFAULT_MILLIS = 500;

    @Rule
    public final RequestMatcherRule serverRule = new InstrumentedTestRequestMatcherRule();

    protected void setupServerRuleRepository() {
        String url = serverRule.url("/").toString();
        CallApi.getClient(url).create(WebServiceMain.class);
    }

    protected Context getApplicationContext() {
        return InstrumentationRegistry.getTargetContext().getApplicationContext();
    }

    protected void doWait() {
        doWait(DEFAULT_MILLIS);
    }

    private void doWait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            Assert.fail("Could not sleep in test");
        }
    }
}
