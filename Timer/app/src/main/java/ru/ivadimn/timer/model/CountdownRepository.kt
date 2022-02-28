package ru.ivadimn.timer.model

class CountdownRepository {

    private var totalSeconds = 0
    private var currentSeconds = 0


    fun seTotalSeconds(totalSec : Int) {
        totalSeconds = totalSec
        currentSeconds = totalSec
    }

    fun setCurrentSeconds(curSec: Int) {
        currentSeconds = curSec
    }

    fun getTotalSeconds() : Int {
        return totalSeconds
    }

    fun getCurrentSeconds() : Int {
        return totalSeconds
    }

}