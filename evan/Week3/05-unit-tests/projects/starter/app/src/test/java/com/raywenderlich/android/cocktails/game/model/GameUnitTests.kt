package com.raywenderlich.android.cocktails.game.model

import org.jetbrains.annotations.TestOnly
import org.junit.Assert
import org.junit.Test

/**
 * Set Up: 테스트 환경 준비
 * Assertion: 테스트하고자 하는 메소드를 실행하고, 결과를 확인한다.
 * Teardown: 환경 리셋
 */
class GameUnitTests {
    @Test
    fun whenIncrementingScore_shouldIncrementCurrentScore() {
//        val game = Game() // Test할 클래스. 존재하지 않음.
//        game.incrementScore() // Test할 해당 클래스의 메소드
//        Assert.assertEquals("Current score should have been 1", 1, game.currentScore)
    }

    @Test
    fun whenIncrementingScore_aboveHighScore_shouldAlsoIncrementHighScore() {
//        val game = Game()
//        game.incrementScore()
//        Assert.assertEquals(1, game.highestScore)
    }

    @Test
    fun whenIncrementingScore_belowHighScore_shouldNotIncrementHighScore() {
//        val game = Game(10)
//        game.incrementScore()
//        Assert.assertEquals(10, game.highestScore)
    }
}