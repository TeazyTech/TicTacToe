package com.lunaticapps.tictactoe

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.lunaticapps.tictactoe.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityMainBinding
    private lateinit var board: Array<Array<Button>>
    private var PLAYER = true
    private var LAST_GAME_STARTER_PLAYER = true
    private var TURN_COUNT = 0
    private var boardStatus = Array(3) { IntArray(3) }
    private var PLAYER1_SCORE = 0
    private var PLAYER2_SCORE = 0
    private lateinit var FIRST_PLAYER_NAME: String
    private lateinit var SECOND_PLAYER_NAME: String
    private lateinit var vibrator: Vibrator
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var musicPlayer: MediaPlayer
    private var isMediaPlayerPlaying = false


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        FIRST_PLAYER_NAME = intent.getStringExtra("PLAYER1").toString()
        FIRST_PLAYER_NAME = makeSentenceCase(FIRST_PLAYER_NAME)
        SECOND_PLAYER_NAME = intent.getStringExtra("PLAYER2").toString()
        SECOND_PLAYER_NAME = makeSentenceCase(SECOND_PLAYER_NAME)
        binding.firstPlayer.setText(FIRST_PLAYER_NAME + "")
        binding.p1Score.setText("" + PLAYER1_SCORE)
        binding.secondPlayer.setText(SECOND_PLAYER_NAME + "")
        binding.p2Score.setText("" + PLAYER2_SCORE)
        binding.displayText.setText(FIRST_PLAYER_NAME + "'s turn")

        //Music
        musicPlayer = MediaPlayer.create(applicationContext, R.raw.game_background_music)
        musicPlayer.isLooping = true
        musicPlayer.start()
        isMediaPlayerPlaying = true;


        //for Ads
        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().build()
        binding.adViewTop.loadAd(adRequest)
        binding.adViewBottom.loadAd(adRequest)



        board = arrayOf(
            arrayOf(binding.R1C1, binding.R1C2, binding.R1C3),
            arrayOf(binding.R2C1, binding.R2C2, binding.R2C3),
            arrayOf(binding.R3C1, binding.R3C2, binding.R3C3)
        )

        for (i: Array<Button> in board) {
            for (button: Button in i) {
                button.setOnClickListener(this)
            }
        }

        initializeBoardStatus()

        binding.restart.setOnClickListener() {
            initializeBoardStatus()
            TURN_COUNT = 0
            PLAYER = !LAST_GAME_STARTER_PLAYER
            LAST_GAME_STARTER_PLAYER = PLAYER

            if (PLAYER) {
                binding.displayText.setText(FIRST_PLAYER_NAME + "'s turn")
            } else {
                binding.displayText.setText(SECOND_PLAYER_NAME + "'s turn")
            }

            for (i in 0..2) {
                for (j in 0..2) {
                    board[i][j].setBackgroundResource(R.drawable.button_shape)
                }
            }
        }

        binding.reset.setOnClickListener() {
            PLAYER1_SCORE = 0
            PLAYER2_SCORE = 0
            updateScore()
        }

        binding.menu.setOnClickListener() {
            val menuFragment = MenuFragment()
            menuFragment.show(supportFragmentManager, menuFragment.getTag())
        }


    }

    private fun initializeBoardStatus() {
        for (i in 0..2) {
            for (j in 0..2) {
                boardStatus[i][j] = -1
                board[i][j].isEnabled = true
                board[i][j].text = ""
            }
        }
    }

    @SuppressLint("ResourceType")
    override fun onClick(v: View) {
        when (v.id) {
            R.id.R1C1 -> {
                updateValue(row = 0, col = 0, player = PLAYER)
                binding.R1C1.setBackgroundResource(R.drawable.button_clicked_shape)
            }

            R.id.R1C2 -> {
                updateValue(row = 0, col = 1, player = PLAYER)
                binding.R1C2.setBackgroundResource(R.drawable.button_clicked_shape)
            }

            R.id.R1C3 -> {
                updateValue(row = 0, col = 2, player = PLAYER)
                binding.R1C3.setBackgroundResource(R.drawable.button_clicked_shape)
            }

            R.id.R2C1 -> {
                updateValue(row = 1, col = 0, player = PLAYER)
                binding.R2C1.setBackgroundResource(R.drawable.button_clicked_shape)
            }

            R.id.R2C2 -> {
                updateValue(row = 1, col = 1, player = PLAYER)
                binding.R2C2.setBackgroundResource(R.drawable.button_clicked_shape)
            }

            R.id.R2C3 -> {
                updateValue(row = 1, col = 2, player = PLAYER)
                binding.R2C3.setBackgroundResource(R.drawable.button_clicked_shape)
            }

            R.id.R3C1 -> {
                updateValue(row = 2, col = 0, player = PLAYER)
                binding.R3C1.setBackgroundResource(R.drawable.button_clicked_shape)
            }

            R.id.R3C2 -> {
                updateValue(row = 2, col = 1, player = PLAYER)
                binding.R3C2.setBackgroundResource(R.drawable.button_clicked_shape)
            }

            R.id.R3C3 -> {
                updateValue(row = 2, col = 2, player = PLAYER)
                binding.R3C3.setBackgroundResource(R.drawable.button_clicked_shape)
            }

        }
        PLAYER = !PLAYER
        TURN_COUNT++

        if (PLAYER) {
            updateDisplay(FIRST_PLAYER_NAME + "'s turn")
        } else {
            updateDisplay(SECOND_PLAYER_NAME + "'s turn")
        }

        if (TURN_COUNT == 9) {
            updateDisplay("GAME OVER - It's a Draw")
        }

        checkWinner()
    }

    private fun checkWinner() {
        //checking Rows
        for (i in 0..2) {
            if (boardStatus[i][0] == boardStatus[i][1] && boardStatus[i][1] == boardStatus[i][2]) {
                if (boardStatus[i][0] == 1) {
                    updateDisplay(FIRST_PLAYER_NAME + " is winner")
                    PLAYER1_SCORE += 1
                    updateScore()

                    for (j in 0..2) {
                        board[i][j].setBackgroundResource(R.drawable.winner_button_background)
                    }
                    break
                } else if (boardStatus[i][0] == 0) {
                    updateDisplay(SECOND_PLAYER_NAME + " is winner")
                    PLAYER2_SCORE += 1
                    updateScore()
                    for (j in 0..2) {
                        board[i][j].setBackgroundResource(R.drawable.winner_button_background)
                    }
                    break
                }
            }
        }

        for (i in 0..2) {
            if (boardStatus[0][i] == boardStatus[1][i] && boardStatus[1][i] == boardStatus[2][i]) {
                if (boardStatus[0][i] == 1) {
                    updateDisplay(FIRST_PLAYER_NAME + " is winner")
                    PLAYER1_SCORE += 1
                    updateScore()
                    for (j in 0..2) {
                        board[j][i].setBackgroundResource(R.drawable.winner_button_background)
                    }
                    break
                } else if (boardStatus[0][i] == 0) {
                    updateDisplay(SECOND_PLAYER_NAME + " is winner")
                    PLAYER2_SCORE += 1
                    updateScore()
                    for (j in 0..2) {
                        board[j][i].setBackgroundResource(R.drawable.winner_button_background)
                    }
                    break
                }
            }
        }

        if (boardStatus[0][0] == boardStatus[1][1] && boardStatus[1][1] == boardStatus[2][2]) {
            if (boardStatus[0][0] == 1) {
                updateDisplay(FIRST_PLAYER_NAME + " is winner")
                PLAYER1_SCORE += 1
                updateScore()
                for (i in 0..2) {
                    board[i][i].setBackgroundResource(R.drawable.winner_button_background)
                }
            } else if (boardStatus[0][0] == 0) {
                updateDisplay(SECOND_PLAYER_NAME + " is winner")
                PLAYER2_SCORE += 1
                updateScore()
                for (i in 0..2) {
                    board[i][i].setBackgroundResource(R.drawable.winner_button_background)
                }
            }
        }

        if (boardStatus[0][2] == boardStatus[1][1] && boardStatus[1][1] == boardStatus[2][0]) {
            if (boardStatus[0][2] == 1) {
                updateDisplay(FIRST_PLAYER_NAME + " is winner")
                PLAYER1_SCORE += 1
                updateScore()
                board[0][2].setBackgroundResource(R.drawable.winner_button_background)
                board[1][1].setBackgroundResource(R.drawable.winner_button_background)
                board[2][0].setBackgroundResource(R.drawable.winner_button_background)
            } else if (boardStatus[0][2] == 0) {
                updateDisplay(SECOND_PLAYER_NAME + " is winner")
                PLAYER2_SCORE += 1
                updateScore()
                board[0][2].setBackgroundResource(R.drawable.winner_button_background)
                board[1][1].setBackgroundResource(R.drawable.winner_button_background)
                board[2][0].setBackgroundResource(R.drawable.winner_button_background)
            }
        }

    }


    private fun updateScore() {
        binding.p1Score.text = "$PLAYER1_SCORE"
        binding.p2Score.text = "$PLAYER2_SCORE"
    }

    private fun updateDisplay(s: String) {
        binding.displayText.text = s
        if (s.contains("winner")) {
            disableButtons()
            vivrate()
            mediaPlayer = MediaPlayer.create(applicationContext, R.raw.fireworks_sound)
            mediaPlayer.start()
            binding.animation.setAnimation(R.raw.anim1)
            binding.animation1.setAnimation(R.raw.anim2)
            binding.animation2.setAnimation(R.raw.anim3)
            binding.animation.playAnimation()
            binding.animation1.playAnimation()
            binding.animation2.playAnimation()

        }
    }

    private fun disableButtons() {
        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j].isEnabled = false
            }
        }
    }

    private fun updateValue(row: Int, col: Int, player: Boolean) {
        val text = if (player) "X" else "0"
        val value = if (player) 1 else 0
        board[row][col].apply {
            setText(text)
            isEnabled = false
        }
        boardStatus[row][col] = value
    }

    private fun vivrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibrator = vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        }
        vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
    }

    private fun makeSentenceCase(name: String): String {
        return name.substring(0, 1).uppercase() + name.substring(1)
    }

    private fun swipTurn() {
        if(PLAYER){
            PLAYER = false
        }else{
            PLAYER = true
        }
    }

//    fun toggleMediaPlayer( switchStatus: Boolean) {
//        if(switchStatus){
//            if (musicPlayer.isPlaying) {
//            }else{
//                musicPlayer.start()
//            }
//        }else{
//            if (musicPlayer.isPlaying) {
//                musicPlayer.pause()
//            }
//        }
//
//    }

    fun startMediaPlayer() {
        if (!musicPlayer.isPlaying) {
            musicPlayer.start()
            isMediaPlayerPlaying = true;
        }
    }

    fun stopMediaPlayer() {
        if (musicPlayer.isPlaying) {
            musicPlayer.pause()
            isMediaPlayerPlaying = false;
        }
    }

    fun isMediaPlayerPlaying(): Boolean {
        return isMediaPlayerPlaying;
    }

    override fun onResume() {
        super.onResume()
        if (isMediaPlayerPlaying()) {
            musicPlayer.start()
        }

    }

    override fun onPause() {
        super.onPause()
        musicPlayer.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        musicPlayer.release()
    }
}