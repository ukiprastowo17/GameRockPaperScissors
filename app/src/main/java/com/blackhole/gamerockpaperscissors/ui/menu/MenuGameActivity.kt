package com.blackhole.gamerockpaperscissors.ui.menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.blackhole.gamerockpaperscissors.R
import com.blackhole.gamerockpaperscissors.databinding.ActivityMenuGameBinding
import com.blackhole.gamerockpaperscissors.ui.main.MainActivity
import com.google.android.material.snackbar.Snackbar


class MenuGameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuGameBinding

    private val name: String? by lazy {
        intent.getStringExtra(EXTRAS_NAME)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setNameOnTitle()
        setMenuClickListeners()
    }

    private fun setMenuClickListeners() {


        binding.ivMenuVsComputer.setOnClickListener {
            val snack = Snackbar.make(it,getString(R.string.vs_computer),Snackbar.LENGTH_LONG)
            snack.show()
            name?.let { it1 -> MainActivity.starActivity(this,false, it1) }

        }
        binding.ivMenuVsPlayer.setOnClickListener {
            val snack = Snackbar.make(it,getString(R.string.vs_player),Snackbar.LENGTH_LONG)
            snack.show()
            name?.let { it1 -> MainActivity.starActivity(this,true, it1) }

        }


    }

    private fun setNameOnTitle() {
        binding.tvTitleMenu.text = getString(R.string.placeholder_title_menu_game, name)
    }




    companion object {
        private const val EXTRAS_NAME = "EXTRAS_NAME"

        fun startActivity(context: Context, name: String) {
            context.startActivity(Intent(context,MenuGameActivity::class.java).apply {
                putExtra(EXTRAS_NAME,name)
            })
        }
    }
}