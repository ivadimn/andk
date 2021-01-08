package com.example.tetris

import android.graphics.Point
import com.example.tetris.constants.CellConstants
import com.example.tetris.constants.FieldConstants
import com.example.tetris.helpers.array2dOfByte
import com.example.tetris.models.Block

class AppModel  {
    var score = 0
    var preferences : AppPreferences? = null

    var currentBlock : Block? = null

    var currentState: String = Statuses.AWAITING_START.name

    private val field = array2dOfByte(
            FieldConstants.ROW_COUNT.value,
            FieldConstants.COLUMN_COUNT.value
    )

    fun getCellStatus(row: Int, column: Int) : Byte {
        return field[row][column]
    }

    fun setCellStatus(row: Int, column: Int, status : Byte?) {
        if (status != null) {
            field[row][column] = status
        }
    }

    fun boostScore() {
        score += 10
        if (score > preferences?.getHighScore() as Int) {
            preferences?.saveHighScore(score)
        }
    }

    fun generateNextBlock() {
        currentBlock = Block.createBlock()
    }

    private fun validTranslation(position : Point, shape : Array<ByteArray>) : Boolean {
        return if (position.y < 0 || position.x < 0) {
            false
        }
        else if (position.y + shape.size > FieldConstants.ROW_COUNT.value) {
            false
        }
        else if (position.x + shape[0].size > FieldConstants.COLUMN_COUNT.value) {
            false
        }
        else {
            for (i in 0 until shape.size) {
                for (j in 0 until shape[i].size) {
                    val y = position.y + i
                    val x = position.x + j
                    if (CellConstants.EMPTY.value != shape[i][j] &&
                            CellConstants.EMPTY.value != field[y][x]) {
                        return false
                    }
                }
            }
            true
        }
    }

    fun moveValid(position: Point, frameNumber : Int?) : Boolean {
        val shape = currentBlock?.getShape(frameNumber as Int)
        return validTranslation(position, shape as Array<ByteArray>)
    }

    fun generateField(action: String) {
        if (isGameActive) {
            resetField()
            var frameNumber: Int? = currentBlock?.frameNumber
            var coordinate : Point? = Point()
            coordinate?.x = currentBlock?.position?.x
            coordinate?.y = currentBlock?.position?.y
            when(action) {
                Motions.LEFT.name -> {
                    coordinate?.x = currentBlock?.position?.x?.minus(1)
                }
                Motions.RIGHT.name -> {
                    coordinate?.x = currentBlock?.position?.x?.plus(1)
                }
                Motions.DOWN.name -> {
                    coordinate?.y = currentBlock?.position?.y?.plus(1)
                }
                Motions.ROTATE.name -> {
                    frameNumber = frameNumber?.plus(1)
                    if (frameNumber != null) {
                        if (frameNumber >= currentBlock?.frameCount as Int) {
                            frameNumber = 0
                        }
                    }
                }
            }
            if (!moveValid(coordinate as Point, frameNumber)) {
                translateBlock(currentBlock?.position as Point,
                    currentBlock?.frameNumber as Int)
                if (Motions.DOWN.name == action) {
                    boostScore()
                    persistCellData()
                    assessField()
                    generateNextBlock()
                    if (!blockAdditionPossible()) {
                        currentState = Statuses.OVER.name
                        currentBlock = null
                        resetField(false)
                    }
                }
            }
            else {
                if (frameNumber != null) {
                    translateBlock(coordinate, frameNumber)
                    currentBlock?.setState(frameNumber, coordinate)
                }
            }
        }
    }

    private fun resetField(ephemeralCellOnly: Boolean = true) {
        for (i in 0 until FieldConstants.ROW_COUNT.value) {
            (0 until FieldConstants.COLUMN_COUNT.value)
                    .filter { !ephemeralCellOnly || field[i][it] ==
                    CellConstants.EPHEMERAL.value
                    }
                    .forEach {field[i][it] = CellConstants.EMPTY.value }
        }
    }

    private fun persistCellData() {
        for (i in 0 until field.size) {
            for (j in 0 until field[i].size) {
                var status = getCellStatus(i, j)
                if (status == CellConstants.EPHEMERAL.value) {
                    status = currentBlock?.staticValue as Byte
                    setCellStatus(i, j, status)
                }
            }
        }
    }

    private fun assessField() {
        for (i in 0 until field.size) {
            var emptyCells = 0
            for (j in 0 until field[i].size) {
                val status = getCellStatus(i, j)
                val isEmpty = CellConstants.EMPTY.value == status
                if (isEmpty) {
                    emptyCells++
                }
            }
            if (emptyCells == 0)
                shiftRows(i)
        }
    }

    private fun translateBlock(position: Point, frameNumber: Int) {
        val shape = currentBlock?.getShape(frameNumber)
        if (shape != null) {
            for (i in shape.indices) {
                for (j in 0 until shape[i].size) {
                    val y = position.y + i
                    val x = position.x + j
                    if (CellConstants.EMPTY.value != shape[i][j])
                        field[y][x] = shape[i][j]
                }
            }
        }
    }

    private fun blockAdditionPossible() : Boolean {
        if (!moveValid(currentBlock?.position as Point,
                currentBlock?.frameNumber)) {
            return false
        }
        return true
    }

    private fun shiftRows(nToRow : Int) {
        if (nToRow > 0) {
            for (j in nToRow - 1 downTo 0) {
                for (m in 0 until field[j].size)
                    setCellStatus(j + 1, m, getCellStatus(j, m))
            }
        }
        for (j in 0 until field[0].size) {
            setCellStatus(0, j, CellConstants.EMPTY.value)
        }
    }

    fun startGame() {
        if (!isGameActive) {
            currentState = Statuses.ACTIVE.name
            generateNextBlock()
        }
    }

    private fun resetModel() {
        resetField(false)
        currentState = Statuses.AWAITING_START.name
        score = 0
    }

    fun restartGame() {
        resetModel()
        startGame()
    }

    fun endGame() {
        score = 0
        currentState = Statuses.OVER.name
    }

    val isGameOver
    get() = currentState == Statuses.OVER.name

    val isGameActive
    get() = currentState == Statuses.ACTIVE.name

    val isGameAwaitingStart
    get() = currentState == Statuses.AWAITING_START.name

    enum class Statuses {
        AWAITING_START, ACTIVE, INACTIVE, OVER
    }
    enum class Motions {
        LEFT, RIGHT, DOWN, ROTATE
    }

}