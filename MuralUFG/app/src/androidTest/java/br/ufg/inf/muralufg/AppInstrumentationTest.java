package br.ufg.inf.muralufg;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import junit.framework.TestCase;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.ufg.inf.muralufg.activity.InboxActivity;
import br.ufg.inf.muralufg.utils.db.DBOpenHelper;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AppInstrumentationTest extends TestCase{

    @Rule
    public ActivityTestRule<InboxActivity> mActivityRule = new ActivityTestRule<>(InboxActivity.class);


    @Test
    public void transicaoInicioParaListaFiltroIT(){

        onView(withId(R.id.actionFilter)).perform(click());

        onView(withId(R.id.FilterList))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

    }

    @Test
    public void swipeToDeleteIT(){
        int initialNewsCount;
        int finalNewsCount;
        DBOpenHelper db = new DBOpenHelper(mActivityRule.getActivity().getApplicationContext());
        initialNewsCount = db.getNews().size();

        onView(withId(R.id.RVNews)).perform(swipeLeft());
        onView(withId(R.id.RVNews)).perform(swipeDown());
        onView(withId(R.id.RVNews)).perform(swipeRight());
        onView(withId(R.id.RVNews)).perform(swipeDown());
        onView(withId(R.id.RVNews)).perform(swipeUp());
        // Checar quantidade de cards antes e depois pra comparar;

        finalNewsCount = db.getNews().size();

        assertEquals(initialNewsCount - 2, finalNewsCount);
    }

    @Test
    public void abrirNoticiaIT(){

        onView(withId(R.id.RVNews)).perform(click()); //line 1

        onView(withId(R.id.NewsTitle)).check(matches(isDisplayed()));
    }

    @Test
    public void abrirNavigationDrawerIT(){

        onView(withId(R.id.drawer_layout)).perform(actionOpenDrawer());

        //To click - talvez funfe
        // Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.drawerItemNameTextView), ViewMatchers.hasSibling(ViewMatchers.withText(((NavDrawerItem)item).getItemName())))).perform(ViewActions.click());

        //onView(withId(R.id.drawer_layout)).check(matches());
    }


    private static ViewAction actionOpenDrawer() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(DrawerLayout.class);
            }

            @Override
            public String getDescription() {
                return "open drawer";
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((DrawerLayout) view).openDrawer(GravityCompat.START);
            }
        };
    }
    private static ViewAction actionCloseDrawer() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(DrawerLayout.class);
            }

            @Override
            public String getDescription() {
                return "close drawer";
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((DrawerLayout) view).closeDrawer(GravityCompat.START);
            }
        };
    }

}
