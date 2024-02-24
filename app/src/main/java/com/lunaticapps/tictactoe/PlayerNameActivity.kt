package com.lunaticapps.tictactoe

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.lunaticapps.tictactoe.databinding.ActivityPlayerNameBinding


class PlayerNameActivity : AppCompatActivity() {
    lateinit var binding: ActivityPlayerNameBinding
    private lateinit var vibrator: Vibrator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        binding = DataBindingUtil.setContentView(this, R.layout.activity_player_name)
        binding.enterBTN.setOnClickListener {
            if (TextUtils.isEmpty(binding.player1.text) || TextUtils.isEmpty(binding.player2.text)) {
                binding.msgTXT.visibility = View.VISIBLE
                vibrate()
                binding.msgTXT.text = buildString { append("Enter both player's name") }
            } else {
                haptic()
                val player1Name = binding.player1.text.toString()
                val player2Name = binding.player2.text.toString()
                val intent: Intent = when (intent.getStringExtra("GAME")) {
                    "3x3" -> {
                        Intent(this@PlayerNameActivity, MainActivity::class.java)
                    }
                    "4x4" -> {
                        Intent(this@PlayerNameActivity, MainActivity4x4::class.java)
                    }
                    else -> {
                        Intent(this@PlayerNameActivity, MainActivity5x5::class.java)
                    }
                }
                intent.putExtra("PLAYER1", player1Name)
                intent.putExtra("PLAYER2", player2Name)
                startActivity(intent)
            }
        }
    }

    private fun vibrate() {
        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(
            VibrationEffect.createOneShot(
                200,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
        )
    }

    private fun haptic() {
        vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = getSystemService(
                VibratorManager::class.java
            )
            vibratorManager.defaultVibrator
        } else {
            getSystemService(VIBRATOR_SERVICE) as Vibrator
        }
        if (vibrator.hasVibrator()) {
            lateinit var vibrationEffect: VibrationEffect
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                vibrationEffect = VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK)
            }
            vibrator.vibrate(vibrationEffect)
        }
    }
}