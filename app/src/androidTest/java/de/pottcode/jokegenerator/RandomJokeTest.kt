package de.pottcode.jokegenerator

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test

/**
 * (c) Dimitri Simon on 08.07.20
 */
class RandomJokeTest {

    @get:Rule
    val mainActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun verifyThatRandomJokeIsDisplayed() {
        onView(withId(R.id.button_get_random_joke)).perform(click())
        onView(withId(R.id.text_view_random_joke)).check(matches(not(withText("Hello World!"))))
    }

}