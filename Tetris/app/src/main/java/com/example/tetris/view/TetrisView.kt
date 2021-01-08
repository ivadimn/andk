package com.example.tetris.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import com.example.tetris.AppModel
import com.example.tetris.GameFragment
import com.example.tetris.constants.CellConstants
import com.example.tetris.constants.FieldConstants
import com.example.tetris.models.Block
import kotlinx.coroutines.Delay

class TetrisView : View {

    private val paint =  Paint()
    private var lastMove: Long = 0
    private var model : AppModel? = null
    private var fragment : GameFragment? = null
    private val viewHandler = ViewHandler(this)
    private var cellSize = Dimension(0, 0)
    private var frameOffset = Dimension(0, 0)


    constructor(context: Context, attrs: AttributeSet) :
            super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) :
            super(context, attrs, defStyle)


    fun setFragment(fragment : GameFragment) {
        this.fragment = fragment
    }

    fun setModel(model : AppModel) {
        this.model = model
    }
    fun setGameCommand(move : AppModel.Motions) {
        if (model != null && (model?.currentState ==
                        AppModel.Statuses.ACTIVE.name)) {
            if (AppModel.Motions.DOWN == move) {
                model?.generateField(move.name)
                invalidate()
                return
            }
            setGameCommandWithDelay(move)
        }
    }

    fun setGameCommandWithDelay(move: AppModel.Motions) {
        val now = System.currentTimeMillis()
        if (now - lastMove > DELAY) {
            model?.generateField(move.name)
            invalidate()
            lastMove = now
        }
        updateScores()
        viewHandler.sleep(DELAY.toLong())
    }

    private fun updateScores() {
        fragment?.currentScoreTextView?.text = "${model?.score}"
        fragment?.updateHighScore()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawFrame(canvas)
        if (model != null) {
            for (i in 0 until FieldConstants.ROW_COUNT.value) {
                for (j in 0 until FieldConstants.COLUMN_COUNT.value) {
                    drawCell(canvas, i, j)
                }
            }
        }
    }

    private fun drawFrame(canvas: Canvas) {
        paint.color = Color.LTGRAY
        canvas.drawRect(frameOffset.width.toFloat(),
        frameOffset.height.toFloat(),
        width - frameOffset.width.toFloat(),
        height - frameOffset.height.toFloat(), paint)
    }

    private fun drawCell(canvas: Canvas, row: Int, col: Int) {
        val cellStatus = model?.getCellStatus(row, col)
        if (cellStatus != CellConstants.EMPTY.value) {
            val color = if (CellConstants.EPHEMERAL.value == cellStatus) {
                model?.currentBlock?.color
            }
            else {
                Block.getColor(cellStatus as Byte)
            }
            drawCell(canvas, col, row, color as Int)
        }
    }

    private fun drawCell(canvas: Canvas, x: Int, y: Int, rgbColor: Int) {
        paint.color = rgbColor
        val top = (frameOffset.height + y * cellSize.height + BLOCK_OFFSET).toFloat()
        val left = (frameOffset.width + x * cellSize.width + BLOCK_OFFSET).toFloat()
        val bottom = (frameOffset.height + (y + 1) * cellSize.height + BLOCK_OFFSET).toFloat()
        val right = (frameOffset.width + (x + 1) * cellSize.width + BLOCK_OFFSET).toFloat()

        val rectangle = RectF(left, top, right, bottom)
        canvas.drawRoundRect(rectangle, 4F, 4F, paint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val cellWidth = (w - 2 / FRAME_OFFSET_BASE) / FieldConstants.COLUMN_COUNT.value
        val cellHeight = (h - 2 / FRAME_OFFSET_BASE) / FieldConstants.ROW_COUNT.value

        val n = Math.min(cellWidth, cellHeight)
        this.cellSize = Dimension(n, n)
        val offsetX = (w - FieldConstants.COLUMN_COUNT.value * 2) / 2
        val offsetY = (h - FieldConstants.ROW_COUNT.value * 2) / 2
        this.frameOffset = Dimension(offsetX, offsetY)
    }

    private class ViewHandler(private val owner: TetrisView) : Handler(Looper.myLooper()!!) {
        override fun handleMessage(msg: Message) {
            if (msg.what == 0) {
                if (owner.model != null) {
                    if (owner.model!!.isGameOver) {
                        owner.model?.endGame()
                        Toast.makeText(owner.fragment?.requireContext(), "Game over",
                            Toast.LENGTH_LONG).show()
                    }
                    if (owner.model!!.isGameActive) {
                        owner.setGameCommandWithDelay(AppModel.Motions.DOWN)
                    }
                }
            }
        }
        fun sleep(delay: Long) {
            this.removeMessages(0)
            sendMessageDelayed(obtainMessage(0), delay)
        }
    }

    private class Dimension(val width: Int, val height: Int)

    companion object {
        private val DELAY = 500
        private val BLOCK_OFFSET = 2
        private val FRAME_OFFSET_BASE = 10
    }
}