package com.example.main;

import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.main.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest extends BaseInstrumentedTest {

    private MainActivityRobot mainActivityRobot;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule =
            new ActivityTestRule<>(MainActivity.class, false, false);

    @Before
    public void setup() {
        mainActivityRobot = new MainActivityRobot(mainActivityRule);
    }

    @Test
    public void givenLoadDisplay_WhenRequestSuccess_ThenShowList() {
        setupServerRuleRepository();
        requestListMovies();
        mainActivityRobot.initActivity();
        mainActivityRobot.checkDisplayedList();
    }

    @Test
    public void givenLoadList_WhenClickListItem_ThenIntent() {
        setupServerRuleRepository();
        requestListMovies();
        mainActivityRobot.initActivity();
        mainActivityRobot.clickListItemInPosition(1);
    }

    private void requestListMovies() {
        serverRule.addFixture(200, "success_list_movies.json");
    }
}
