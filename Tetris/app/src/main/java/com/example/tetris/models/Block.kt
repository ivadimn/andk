package com.example.tetris.models

import android.graphics.Color
import android.graphics.Point
import com.example.tetris.constants.FieldConstants
import kotlin.random.Random

class Block private constructor (
        private val shapeIndex : Int,
        private val blockColor : BlockColor
) {
    var frameNumber = 0
    var position = Point(FieldConstants.COLUMN_COUNT.value / 2, 0)

    val frameCount
    get() = Shape.values()[shapeIndex].frameCount

    val color
    get() = blockColor.rgbValue

    val staticValue
    get() = blockColor.byteValue

    fun setState(frame : Int, position: Point) {
        frameNumber = frame
        this.position = position
    }

    fun getShape(frameNumber : Int) : Array<ByteArray> {
        return Shape.values()[shapeIndex].getFrame(frameNumber)
                .as2dByteArray()
    }

    enum class BlockColor (
        val rgbValue : Int,
        val byteValue : Byte
    ){
        PINK(Color.rgb(255, 105, 120), 2.toByte()),
        GREEN(Color.rgb(0, 128, 120), 3.toByte()),
        ORANGE(Color.rgb(255, 140, 0), 4.toByte()),
        YELLOW(Color.rgb(255, 255, 0), 5.toByte()),
        CYAN(Color.rgb(0, 255, 255), 6.toByte());
    }


    companion object {
        fun createBlock() : Block {
            val shapeIndex = Random.nextInt(0, Shape.values().size)
            val blockColor = BlockColor.values()[Random.nextInt(0, BlockColor.values().size)]
            return Block(shapeIndex, blockColor).apply {
                position.x = position.x - Shape.values()[shapeIndex].startPosition
            }
        }

        fun getColor(value : Byte) : Int {
            for (color in BlockColor.values()) {
                if (value == color.byteValue)
                    return color.rgbValue
            }
            return -1
        }
    }
}