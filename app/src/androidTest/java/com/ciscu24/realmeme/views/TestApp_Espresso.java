package com.ciscu24.realmeme.views;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.ciscu24.realmeme.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestApp_Espresso {

    @Rule
    public ActivityTestRule<LogoActivity> mActivityTestRule = new ActivityTestRule<>(LogoActivity.class);

    @Test
    public void testApp_Espresso() {
        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            System.out.println("Error");
        }
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        floatingActionButton.perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Error");
        }

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.NameTextForm),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.NameInputForm),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText.perform(click());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.NameTextForm),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.NameInputForm),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("Meme Ejemplo"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.DescriptionTextForm),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.DescriptionInputForm),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("Esto es una descripción del meme"), closeSoftKeyboard());

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.AuthorTextForm),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.AuthorInputForm),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText4.perform(replaceText("Ciscu24"), closeSoftKeyboard());

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.LikeTextForm),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.LikeInputForm),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText5.perform(replaceText("5"), closeSoftKeyboard());

        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.DateTextForm),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.DateInputForm),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText6.perform(replaceText("16/02/2021"), closeSoftKeyboard());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.CategorySpinner),
                        childAtPosition(
                                allOf(withId(R.id.FavCategoryLayout),
                                        childAtPosition(
                                                withId(R.id.GeneralLayout),
                                                4)),
                                3)));
        appCompatSpinner.perform(scrollTo(), click());

        DataInteraction materialTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(3);
        materialTextView.perform(click());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.SaveFormButton), withText("Save"),
                        childAtPosition(
                                allOf(withId(R.id.ButtonLayout),
                                        childAtPosition(
                                                withId(R.id.GeneralLayout),
                                                6)),
                                1)));
        materialButton.perform(scrollTo(), click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.memelist),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView.perform(actionOnItemAtPosition(6, click()));

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.DeleteFormButton), withText("Delete"),
                        childAtPosition(
                                allOf(withId(R.id.ButtonLayout),
                                        childAtPosition(
                                                withId(R.id.GeneralLayout),
                                                6)),
                                0)));
        materialButton2.perform(scrollTo(), click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(android.R.id.button1), withText("Delete"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        materialButton3.perform(scrollTo(), click());

        ViewInteraction textView = onView(
                allOf(withText("RealMeme"),
                        withParent(allOf(withId(R.id.toolbar),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView.check(matches(withText("RealMeme")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
