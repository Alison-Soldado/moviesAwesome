package com.example.test;

import android.support.annotation.IdRes;
import android.support.test.espresso.ViewInteraction;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.core.AllOf.allOf;

public class BaseRobotTest {
    public ViewInteraction fillEditText(@IdRes int idRes, String text) {
        return onView(withId(idRes)).perform(replaceText(text), closeSoftKeyboard());
    }

    public ViewInteraction clickButton(@IdRes int idRes) {
        return onView((withId(idRes))).perform(click());
    }

    public ViewInteraction textView(@IdRes int idRes) {
        return onView(withId(idRes));
    }

    public ViewInteraction matchText(ViewInteraction viewInteraction, String text) {
        return viewInteraction.check(matches(withText(text)));
    }

    public ViewInteraction matchText(@IdRes int idRes, String text) {
        return matchText(textView(idRes), text);
    }

    public void clickListItem(@IdRes int listRes, int position) {
        onData(anything())
                .inAdapterView(allOf(withId(listRes)))
                .atPosition(position).perform(click());
    }

    public ViewInteraction checkDisplayed(@IdRes int idRes) {
        return onView(withId(idRes)).check(matches(isDisplayed()));
    }
}
