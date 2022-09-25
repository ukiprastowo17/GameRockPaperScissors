package com.blackhole.gamerockpaperscissors.manager

interface GameState {

    fun onStartEngine()
    fun onUpdate(time:Long)
    fun onFinish()
}