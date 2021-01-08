package com.example.tetris

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(
        private val context: Context
) {
    companion object {
        private const val APP_REFERENCES = "TetrisRefernces"
        private const val HIGH_SCORE = "HighScore"
    }

    val data = context.getSharedPreferences(APP_REFERENCES, Context.MODE_PRIVATE)

    fun saveHighScore(highScore : Int) {
        data.edit().putInt(HIGH_SCORE, highScore).apply()
    }

    fun getHighScore() : Int {
        return data.getInt(HIGH_SCORE, 0)
    }

    fun clearHighScore() {
        saveHighScore(0)
    }
}