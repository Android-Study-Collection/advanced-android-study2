package com.example.tdd

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception

@LargeTest // UI 테스트는 LargeTest이다. 모든 리소스(DB, Network 등 모든 컴포넌트를 사용하는 테스트임을 나타내며, 1000ms 이상 걸리는 테스트)
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    // 시나리오 객체를 정의해준다.
    private lateinit var scenario: ActivityScenario<MainActivity>
    private val conversionTable = hashMapOf<Int, Int>()

    init {
        conversionTable[0] = 32
        conversionTable[100] = 212
        conversionTable[-1] = 30
        conversionTable[-100] = -148
        conversionTable[32] = 90
        conversionTable[-40] = -40
        conversionTable[-273] = -459
    }

    @Before
    fun setUp() {
        // 시나리오 객체를 생성한다.
        scenario = ActivityScenario.launch(MainActivity::class.java) // 해당 Activity를 실행한다.
        scenario.moveToState(Lifecycle.State.RESUMED) // 해당 Activity의 특정 lifecycle로 전환한다.
    }

    @Test
    fun testConversion() {
        conversionTable.forEach {
            val c = it.key
            val f = it.value
            onView(withId(R.id.acet_celsius)).perform(typeText("$c"))
            onView(withId(R.id.acbtn_convert)).perform(click())
            onView(withId(R.id.acet_fahrenheit)).check(matches(withText("$f")))
        }
    }
}