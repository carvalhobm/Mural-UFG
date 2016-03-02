package br.ufg.inf.muralufg;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import br.ufg.inf.muralufg.activity.InboxActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AppInstrumentationTest extends TestCase{

    @Rule
    public ActivityTestRule<InboxActivity> mActivityRule = new ActivityTestRule<>(InboxActivity.class);


    @Test
    public void transicaoInicioParaListaFiltroIT(){

        onView(withId(R.id.actionFilter)).perform(click()); //line 1

        onView(withId(R.id.FilterList))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

    }

    @Test
    public void swipeToDeleteIT(){

            onView(withId(R.id.RVNews)).perform(swipeLeft()); //line 1
            onView(withId(R.id.RVNews)).perform(swipeLeft()); //line 1
            onView(withId(R.id.RVNews)).perform(swipeLeft()); //line 1
            onView(withId(R.id.RVNews)).perform(swipeDown()); //line 1

    }

}
