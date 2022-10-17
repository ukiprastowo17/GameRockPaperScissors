package com.blackhole.gamerockpaperscissors.manager

import android.os.CountDownTimer
import androidx.core.view.ViewCompat
import com.blackhole.gamerockpaperscissors.enum.GameRole
import com.blackhole.gamerockpaperscissors.model.GameScore
import com.blackhole.gamerockpaperscissors.util.Routines
import java.util.*


class GameEngine {




    var timer:CountDownTimer? = null

    private val dataRoutines  = Routines()
    private var time:Int = dataRoutines.TIME_DEFAULT

    fun init(state: GameState){
        state.onStartEngine()
        timer = object : CountDownTimer(dataRoutines.TIME_UNIT, 1000){

            override fun onFinish() {
                state.onFinish()
            }

            override fun onTick(p0: Long) {
                state.onUpdate(p0)
            }
        }
    }

    fun decrementCounter(){
        time -= 1
    }

    fun dataCounter() : Int{
        return time
    }

    fun dataCounterStart() : Int{
        time = dataRoutines.TIME_DEFAULT
        return time
    }

    fun start(){
        timer?.start()
    }

    fun stop(){
        timer?.cancel()
    }

    fun returnGame(scoreP1: Int, scoreP2 : Int) : String{
        var returnGame = ""
        returnGame = if (scoreP1 > scoreP2) {
            dataRoutines.WIN
        } else if (scoreP1 < scoreP2)
            dataRoutines.LOSE
        else{
            dataRoutines.DRAW
        }

        return returnGame
    }

    fun playerNameEnemy(typePlayer : Boolean) : String {
        return if (typePlayer){
            "Player 2"
        }else{
            "Robot"
        }

    }


    fun random(from: Int, to: Int) : Int {
        return Random().nextInt(to - from) + from
    }

    fun returnScore(tp: GameRole, curType: GameRole?, score : Int,  scoreP2 : Int  ) : GameScore {
        var info = ""
        var score = score
        var scoreP2 = scoreP2



        when (curType) {
            GameRole.ROCK -> {
                when (tp) {
                    GameRole.ROCK -> {
                        info = dataRoutines.DRAW
                    }
                    GameRole.PAPER -> {
                        info = dataRoutines.WIN
                        score+=10
                    }
                    else -> {
                        info = dataRoutines.LOSE
                        scoreP2+=10
                    }
                }
            }
            GameRole.PAPER -> {
                when (tp) {
                    GameRole.ROCK -> {
                        info = dataRoutines.LOSE
                        scoreP2+=10
                    }
                    GameRole.PAPER -> {
                        info = dataRoutines.DRAW
                    }
                    else -> {
                        info = dataRoutines.WIN
                        score+=10
                    }
                }
            }
            else -> {
                when (tp) {
                    GameRole.ROCK -> {
                        info = dataRoutines.WIN
                        score+=10
                    }
                    GameRole.PAPER -> {
                        info = dataRoutines.LOSE
                        scoreP2+=10
                    }
                    else -> {
                        info = dataRoutines.DRAW
                    }
                }
            }
        }


        return GameScore(info, score,  scoreP2 )

    }


}