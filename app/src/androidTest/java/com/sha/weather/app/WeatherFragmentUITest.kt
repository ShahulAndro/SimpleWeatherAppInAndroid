package com.sha.weather.app

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import org.hamcrest.Matcher
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeoutException

/**
 * Created by Shahul Hameed on 14/11/2024.
 */

@RunWith(AndroidJUnit4::class)
class WeatherFragmentUITest {

    @Test
    fun testLocationPermissionAndNavigateToWeatherFragment() {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        val allowButton = device.findObject(UiSelector().text("Allow this time"))
        if (allowButton.exists()) {
            allowButton.click()
        }

        onView(withId(R.id.weatherRootLayout))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testCityInputAndButtonVisible() {

        clickAllowButtonIfExits()

        onView(withId(R.id.cityInput)).perform(typeText("London"), closeSoftKeyboard())

        onView(withId(R.id.cityInput)).check(matches(isDisplayed()))

        onView(withId(R.id.buttonSearch)).check(matches(isDisplayed()))
    }

    @Test
    fun testWeatherDetailsDisplayedCorrectly() {

        clickAllowButtonIfExits()

        onView(withId(R.id.textViewWeather)).check(matches(withText("Weather Information")))

        onView(withId(R.id.weatherCard)).check(matches(isDisplayed()))

        onView(withId(R.id.locationTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.currentTempTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.weatherDescriptionTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.tempMaxTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.tempMinTextView)).check(matches(isDisplayed()))
    }

    @Test
    fun testEnterCityAndSearch() {
        clickAllowButtonIfExits()

        onView(withId(R.id.cityInput)).perform(typeText("London"), closeSoftKeyboard())

        onView(withId(R.id.buttonSearch)).perform(click())

        onView(withId(R.id.locationTextView)).check(matches(withText("London")))
        onView(withId(R.id.currentTempTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.weatherDescriptionTextView)).check(matches(isDisplayed()))
    }

    private fun clickAllowButtonIfExits() {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        val allowButton = device.findObject(UiSelector().text("Allow this time"))
        if (allowButton.exists()) {
            allowButton.click()
        }
    }

    private fun waitForView(matcher: Matcher<View>, timeout: Long = 5000) {
        val startTime = System.currentTimeMillis()
        val endTime = startTime + timeout

        do {
            try {
                onView(matcher).check(matches(isDisplayed()))
                return
            } catch (e: Exception) {
                if (System.currentTimeMillis() > endTime) throw TimeoutException("View not found within $timeout milliseconds.")
            }
            Thread.sleep(100) // Poll every 100ms
        } while (System.currentTimeMillis() < endTime)

        throw TimeoutException("View not found within $timeout milliseconds.")
    }
}