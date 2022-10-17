package com.blackhole.gamerockpaperscissors.ui.main

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.blackhole.gamerockpaperscissors.*
import com.blackhole.gamerockpaperscissors.databinding.ActivityMainBinding
import com.blackhole.gamerockpaperscissors.databinding.DialogResultBinding
import com.blackhole.gamerockpaperscissors.enum.GamePlay
import com.blackhole.gamerockpaperscissors.enum.GameRole
import com.blackhole.gamerockpaperscissors.manager.GameEngine
import com.blackhole.gamerockpaperscissors.manager.GameState
import com.blackhole.gamerockpaperscissors.util.Routines


class MainActivity : AppCompatActivity(), GameState {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val bindingDialog: DialogResultBinding by lazy {
        DialogResultBinding.inflate(layoutInflater)
    }

    private val name: String? by lazy {
        intent.getStringExtra(MainActivity.EXTRAS_NAME)
    }

    private val isUsingMultiplayerMode : Boolean by lazy {
        intent.getBooleanExtra(EXTRAS_MULTIPLAYER_MODE, false)
    }

    private val dataRoutines = Routines()
    private val gameEngine: GameEngine = GameEngine()
    private var scoreFinal:Int = 0
    private var scoreFinalP2:Int = 0

//    private var dialog: AlertDialog? = null
    private var dialog: Dialog? = null
    private var gameRole: GameRole? = null
    private var gamePlay: GamePlay? = null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        gameEngine.init(this)
        binding.tvInfo.text = getString(R.string.get_to_ready)
        viewScore(scoreFinal, scoreFinalP2)
        binding.tvTime.text =  getString(R.string.time_label, gameEngine.dataCounter() .toString())

//        generateDialog()
        dialog = Dialog(this@MainActivity)

        binding.tvTime.postDelayed({
            gameEngine.start()
            binding.tvInfo.visibility = View.GONE

        }, 1000)

    }

    override fun onStartEngine() {
        actionPlayerMc()

        if (isUsingMultiplayerMode){
            actionPlayerEnemy()
        }else{
            randomEnemy()
        }

        gamePlay = GamePlay.ON
    }

    override fun onUpdate(tm: Long) {
        if(gamePlay == GamePlay.ON) {
            gameEngine.decrementCounter()
            binding.tvTime.text = getString(R.string.time_label, gameEngine.dataCounter() .toString())
        }

        if( gameEngine.dataCounter() == 0){
//            dialog!!.setMessage((getString( R.string.score_final_label, name.toString(), scoreFinal.toString(), gameEngine.playerNameEnemy(isUsingMultiplayerMode) , scoreFinalP2.toString() ,  gameEngine.returnGame(scoreFinal, scoreFinalP2) )))
//
//            dialog!!.show()

            showDialog()
            gamePlay = GamePlay.FINISH
            gameEngine.stop()
            ViewCompat.animate(binding.imgEnemy).translationY(-400f).duration = 100
        }
    }

    override fun onFinish() {

    }

//    private fun generateDialog(){
//        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
//        val view: View = LayoutInflater.from(this).inflate(R.layout.layout_dialog, null, false)
//        builder.setView(view)
//        builder.setTitle(getString(R.string.result))
//        builder.setMessage((getString( R.string.score_final_label, name.toString(), scoreFinal.toString(), gameEngine.playerNameEnemy(isUsingMultiplayerMode) , scoreFinalP2.toString() ,  gameEngine.returnGame(scoreFinal, scoreFinalP2) )))
//        builder.setPositiveButton(getString(R.string.restart)) { dialog, _ ->
//            dialog.dismiss()
//            restart()
//        }
//        builder.setNegativeButton(getString(R.string.menu_back)) { dialog, _ ->
//          finish()
//        }
//        builder.setCancelable(false)
//        dialog = builder.create()
//    }






    private fun checkAnswer(tp: GameRole){

        val dataScore = 0
        val dataScoreP2 = 0

        val (info, score, scoreP2) = gameEngine.returnScore(tp, gameRole,  dataScore + scoreFinal, dataScoreP2 + scoreFinalP2)

        scoreFinal = score
        scoreFinalP2 = scoreP2

        viewScore(scoreFinal, scoreFinalP2)
        binding.tvInfo.visibility = View.VISIBLE
        binding.tvInfo.text = info


        if (isUsingMultiplayerMode){
            playerEnemy()
        }else{
            playerAI()
        }



    }

    private fun viewScore(scoreFinal : Int , scoreFinalP2: Int){
        binding.tvScore.text = getString( R.string.score_label, name.toString(), scoreFinal.toString())
        binding.tvScoreP2.text = getString(R.string.score_label, gameEngine.playerNameEnemy(isUsingMultiplayerMode), scoreFinalP2.toString())

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

        viewToast(gameRole.toString())
        ViewCompat.animate(binding.imgEnemy).translationY(400f).duration = 100


    }

    private fun playerEnemy(){
        binding.tvInfo.postDelayed({
            binding.tvInfo.visibility = View.GONE
            ViewCompat.animate(binding.imgEnemy).translationY(-400f).duration = 100
            ViewCompat.animate(binding.imgMc).translationY(600f).duration = 100
            binding.tvInfo.postDelayed({
                actionPlayerEnemy()
            }, 500)

        }, 500)



    }

    private fun viewToast(dataText : String){
        Toast.makeText(this, dataText, Toast.LENGTH_SHORT).show()
    }

    private fun actionPlayerEnemy(){
        binding.btnRockEnemy.setOnClickListener {
            gameRole = GameRole.ROCK
            binding.imgEnemy.setImageResource(R.drawable.ic_rock)
            ViewCompat.animate(binding.imgEnemy).translationY(400f).duration = 100
            viewToast(gameRole.toString())

        }
        binding.btnPaperEnemy.setOnClickListener {
            gameRole = GameRole.PAPER
            binding.imgEnemy.setImageResource(R.drawable.ic_paper)
            ViewCompat.animate(binding.imgEnemy).translationY(400f).duration = 100
            viewToast(gameRole.toString())
        }
        binding.btnScissorEnemy.setOnClickListener {
            gameRole = GameRole.SCISSOR
            binding.imgEnemy.setImageResource(R.drawable.ic_scissor)
            ViewCompat.animate(binding.imgEnemy).translationY(400f).duration = 100
            viewToast(gameRole.toString())
        }
    }

    private fun actionPlayerMc(){
        binding.btnRock.setOnClickListener {
            binding.imgMc.setImageResource(R.drawable.ic_rock)
            checkAnswer(GameRole.ROCK)
            ViewCompat.animate(binding.imgMc).translationY(-600f).duration = 100

        }
        binding.btnPaper.setOnClickListener {
            binding.imgMc.setImageResource(R.drawable.ic_paper)
            checkAnswer(GameRole.PAPER)
            ViewCompat.animate(binding.imgMc).translationY(-600f).duration = 100

        }
        binding.btnScissor.setOnClickListener {
            binding.imgMc.setImageResource(R.drawable.ic_scissor)
            checkAnswer(GameRole.SCISSOR)
            ViewCompat.animate(binding.imgMc).translationY(-600f).duration = 100
        }
    }



    private fun restart(){
        scoreFinal = 0
        gameEngine.dataCounterStart()
        gameRole = null
        gamePlay = null

        gameEngine.init(this)
        gameEngine.start()
    }

    companion object{
        private const val EXTRAS_MULTIPLAYER_MODE = "EXTRAS_MULTIPLAYER_MODE"
        private const val EXTRAS_NAME = "EXTRAS_NAME"
        fun starActivity(context: Context, isUsingMultiplayerMode : Boolean, name : String){
            context.startActivity(Intent(context, MainActivity::class.java).apply{
                putExtra(EXTRAS_MULTIPLAYER_MODE, isUsingMultiplayerMode)
                putExtra(EXTRAS_NAME, name)
            })
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        gamePlay = GamePlay.FINISH
        gameEngine.stop()
    }

private fun showDialog(){


    dialog!!.setContentView(bindingDialog.root)
    dialog!!.setCancelable(false);
    bindingDialog.tvResult.text = gameEngine.returnGame(scoreFinal, scoreFinalP2)
    bindingDialog.tvInfoScore.text =  getString( R.string.score_final_label, name.toString(), scoreFinal.toString(), gameEngine.playerNameEnemy(isUsingMultiplayerMode) , scoreFinalP2.toString() )

    bindingDialog.btRestart.setOnClickListener(View.OnClickListener {
        dialog!!.dismiss()
            restart()
    })
    bindingDialog.btMenuBack.setOnClickListener(View.OnClickListener {
        finish()
    })

    dialog!!.show()
}
}
