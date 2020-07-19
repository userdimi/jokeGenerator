package de.pottcode.jokegenerator

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * (c) Dimitri Simon on 08.07.20
 */
@HiltAndroidTest
class RandomJokeTest {

    @get:Rule
    val mainActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Inject
    lateinit var retrofit: Retrofit

    @Before
    fun setUp() {
        hiltRule.inject()
        IdlingRegistry.getInstance().register(
            OkHttp3IdlingResource.create(
                "okhttp",
                okHttpClient
            )
        )
    }

    @Test
    fun verifyThatRandomJokeIsDisplayed() {
        onView(withId(R.id.text_view_random_joke)).check(matches(withText("Hello World!")))
        //onView(withId(R.id.button_get_random_joke)).perform(click())
    }
}