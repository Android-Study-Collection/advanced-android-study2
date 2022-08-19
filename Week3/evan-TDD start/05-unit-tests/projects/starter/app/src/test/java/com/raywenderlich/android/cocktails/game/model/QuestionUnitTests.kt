package com.raywenderlich.android.cocktails.game.model

import androidx.core.app.RemoteInput
import org.junit.Before
import org.junit.Test

/**
 * Set Up: 테스트 환경 준비
 * Assertion: 테스트하고자 하는 메소드를 실행하고, 결과를 확인한다.
 * Teardown: 환경 리셋
 */
class QuestionUnitTests {
//    private lateinit var question: Question

    /**
     * @After: 테스트 이후 실행
     * @BeforeClass: 전체적인 테스트가 실행되기 전에 한 번만 실행됨.
     * @AfterClass: 모든 테스트가 끝나고 한 번만 실행됨.
     */
    @Before
    fun setup(){
//        val question = Question("CORRECT", "INCORRECT")
    }

    // expected를 사용해서 예외처리 시 pass되도록 할 수 있다
    @Test(expected = IllegalArgumentException::class)
    fun whenAnswering_withInvalidOption_shouldThrowException() {
//        val question = Question("CORRECT", "INCORRECT") // boilerplate될 수 있으므로 setup한다.
//        question.answer("INVALID")
    }
}