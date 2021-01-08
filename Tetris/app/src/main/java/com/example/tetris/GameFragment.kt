package com.example.tetris

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.tetris.view.TetrisView
import java.util.prefs.NodeChangeEvent

class GameFragment : Fragment(R.layout.fragment_game) {

    lateinit var currentScoreTextView: TextView
    lateinit var highScoreTextView: TextView
    private lateinit var restartGameButton: Button
    private lateinit var tetrisView: TetrisView
    var appPreferences : AppPreferences? = null
    private val appModel = AppModel()


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        appPreferences = AppPreferences(requireContext())
        appModel.preferences = appPreferences

        currentScoreTextView = requireView().findViewById(R.id.currentScoreTextView)
        highScoreTextView = requireView().findViewById(R.id.highScoreTextView)
        restartGameButton = requireView().findViewById(R.id.restartGameButton)
        tetrisView = requireView().findViewById(R.id.view_tetris)
        tetrisView.setFragment(this)
        tetrisView.setModel(appModel)
        tetrisView.setOnTouchListener(this::onTetrisViewTouch)
        restartGameButton.setOnClickListener(this::btnRestartClick)

        updateHighScore()
        updateCurrentScore()
    }

    private fun btnRestartClick(view: View) {
        appModel.restartGame()
    }

    private fun onTetrisViewTouch(view: View, event: MotionEvent) : Boolean {
        if (appModel.isGameOver || appModel.isGameAwaitingStart) {
            appModel.startGame()
            tetrisView.setGameCommandWithDelay(AppModel.Motions.DOWN)
        }
        else {
            if (appModel.isGameActive) {
                when(resolveTouchDirection(view, event)) {
                    0 -> moveTetromino(AppModel.Motions.LEFT)
                    1 -> moveTetromino(AppModel.Motions.ROTATE)
                    2 -> moveTetromino(AppModel.Motions.DOWN)
                    3 -> moveTetromino(AppModel.Motions.RIGHT)
                }
            }
        }
        return true
    }

    private fun resolveTouchDirection(view: View, event: MotionEvent) : Int {
        val x = event.x / view.width
        val y = event.y / view.height
        val direction = if (y > x) {
            if (x > 1 - y) 2 else 0
        }
        else {
            if (x > 1 - y) 3 else 1
        }
        return direction
    }

    private fun moveTetromino(motion : AppModel.Motions) {
        if (appModel.isGameActive) {
            tetrisView.setGameCommand(motion)
        }
    }

    fun updateHighScore() {
        highScoreTextView.text = "${AppPreferences(requireContext()).getHighScore()}"
    }

    private fun updateCurrentScore() {
        currentScoreTextView.text = "0"
    }
}