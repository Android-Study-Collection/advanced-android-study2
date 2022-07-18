package com.raywenderlich.android.cocktails.model

/**
 * 1
 */
//class Game {
//    var currentScore = 0
//        private set
//
//    fun incrementScore() {
//        // currentScore++
//    }
//}

/**
 * 2
 */
//class Game {
//    var currentScore = 0
//        private set
//    var highestScore = 0
//        private set
//
//    fun incrementScore() {
//        // currentScore++
//        // highestScore++
//    }
//}

/**
 * 3
 */
//class Game(highest: Int = 0) {
//    var currentScore = 0
//        private set
//    var highestScore = highest
//        private set
//
//    // Before
//    fun incrementScore() {
//        // currentScore++
//        // highestScore++
//    }
//    // After fix
//    fun incrementScore() {
//        // currentScore++
//        // if(currentScore > highestScore) {
//        //    highestScore = currentScore
//        // }
//    }
//}