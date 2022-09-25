package com.blackhole.gamerockpaperscissors.ui

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.ViewCompat
import com.blackhole.gamerockpaperscissors.R
import com.blackhole.gamerockpaperscissors.databinding.ActivityMainBinding
import com.blackhole.gamerockpaperscissors.enum.GamePlay
import com.blackhole.gamerockpaperscissors.enum.GameRole
import com.blackhole.gamerockpaperscissors.manager.GameEngine
import com.blackhole.gamerockpaperscissors.manager.GameState
import com.blackhole.gamerockpaperscissors.util.Routines


class MainActivity : AppCompatActivity(), GameState, View.OnClickListener {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val dataRoutines = Routines()
    private val gameEngine: GameEngine = GameEngine()
    private var scoreFinal:Int = 0

    private var dialog: AlertDialog? = null

    private var gameRole: GameRole? = null
    private var gamePlay: GamePlay? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        gameEngine.init(this)
        binding.tvInfo.text = getString(R.string.get_to_ready)
        binding.tvScore.text = getString(R.string.score_label, scoreFinal.toString())
        binding.tvTime.text =  getString(R.string.time_label, gameEngine.dataCounter() .toString())

        generateDialog()

        binding.tvTime.postDelayed({
            gameEngine.start()
            binding.tvInfo.visibility = View.GONE

        }, 1000)

    }

    override fun onStartEngine() {
        binding.btnPaper.setOnClickListener(this)
        binding.btnRock.setOnClickListener(this)
        binding.btnScissor.setOnClickListener(this)
        randomEnemy()
        gamePlay = GamePlay.ON
    }

    override fun onUpdate(tm: Long) {
        if(gamePlay == GamePlay.ON) {
            gameEngine.decrementCounter()
            binding.tvTime.text = getString(R.string.time_label, gameEngine.dataCounter() .toString())
        }

        if( gameEngine.dataCounter() == 0){
            dialog!!.setMessage(getString(R.string.score_label, scoreFinal.toString()) )
            dialog!!.show()
            gamePlay = GamePlay.FINISH
            gameEngine.stop()
            ViewCompat.animate(binding.imgEnemy).translationY(-400f).duration = 100
        }
    }

    override fun onFinish() {

    }

    fun generateDialog(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val view: View = LayoutInflater.from(this).inflate(R.layout.layout_dialog, null, false)
        builder.setView(view)
        builder.setTitle(getString(R.string.result))
        builder.setMessage(getString(R.string.score_final_label, scoreFinal.toString() ))
        builder.setPositiveButton(getString(R.string.restart)) { dialog, _ ->
            dialog.dismiss()
            restart()
        }
        builder.setCancelable(false)
        dialog = builder.create()
    }



    override fun onClick(v: View) {
        if(gamePlay == GamePlay.FINISH)
            return

        when(v.id){
            R.id.btn_rock->{
                binding.imgMc.setImageResource(R.drawable.ic_rock)
                checkAnswer(GameRole.ROCK)
            }
            R.id.btn_paper->{
                binding.imgMc.setImageResource(R.drawable.ic_paper)

                checkAnswer(GameRole.PAPER)
            }
            R.id.btn_scissor->{
                binding.imgMc.setImageResource(R.drawable.ic_scissor)

                checkAnswer(GameRole.SCISSOR)
            }
        }


        ViewCompat.animate(binding.imgMc).translationY(-600f).duration = 100

    }


    private fun checkAnswer(tp: GameRole){

        val dataScore = 0

        val (info, score) = gameEngine.returnScore(tp, gameRole,  dataScore + scoreFinal)

        scoreFinal = score

        binding.tvScore.text = getString(R.string.score_label, scoreFinal.toString())
        binding.tvInfo.visibility = View.VISIBLE
        binding.tvInfo.text = info

        playerAI()

    }

    private fun playerAI(){
                binding.tvInfo.postDelayed({
            binding.tvInfo.visibility = View.GONE
            ViewCompat.animate(binding.imgEnemy).translationY(-400f).duration = 100
            ViewCompat.animate(binding.imgMc).translationY(600f).duration = 100
            binding.tvInfo.postDelayed({
                randomEnemy()
            }, 500)

        }, 500)
    }

    private fun randomEnemy(){

        when (gameEngine.random(0, 3)){
            2-> {
                gameRole = GameRole.ROCK
                binding.imgEnemy.setImageResource(R.drawable.ic_rock)
            }
            1-> {
                gameRole = GameRole.PAPER
                binding.imgEnemy.setImageResource(R.drawable.ic_paper)
            }
            0-> {
                gameRole = GameRole.SCISSOR
                binding.imgEnemy.setImageResource(R.drawable.ic_scissor)
            }

        }

        ViewCompat.animate(binding.imgEnemy).translationY(400f).duration = 100


    }



    private fun restart(){
        scoreFinal = 0
        gameEngine.dataCounterStart()
        gameRole = null
        gamePlay = null

        gameEngine.init(this)
        gameEngine.start()
    }
}
