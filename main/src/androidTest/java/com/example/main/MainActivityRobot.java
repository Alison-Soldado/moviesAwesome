package com.example.main;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import com.example.test.BaseRobotTest;

public class MainActivityRobot extends BaseRobotTest {

    private ActivityTestRule mainActivityRule;

    MainActivityRobot(ActivityTestRule mainActivityRule) {
        this.mainActivityRule = mainActivityRule;
    }

    protected void initActivity() {
        mainActivityRule.launchActivity(new Intent());
    }

    protected void checkDisplayedList() {
        checkDisplayed(R.id.activity_main_recycler_movie);
    }

    protected void clickListItemInPosition(int position) {
        clickListItem(R.id.activity_main_recycler_movie, position);
    }
}
