package com.lunaticapps.tictactoe

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.lunaticapps.tictactoe.databinding.ActivityChooseGameBinding

class ChooseGameActivity : AppCompatActivity() {
    lateinit var binding: ActivityChooseGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_game)
        val intent = Intent(this@ChooseGameActivity, PlayerNameActivity::class.java)
        binding.imageButton3x3.setOnClickListener {
            intent.putExtra("GAME", "3x3")
            startActivity(intent)
        }
        binding.imageButton4x4.setOnClickListener {
            intent.putExtra("GAME", "4x4")
            startActivity(intent)
        }
        binding.imageButton5x5.setOnClickListener {
            intent.putExtra("GAME", "5x5")
            startActivity(intent)
        }
    }
}