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
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.lunaticapps.tictactoe.databinding.ActivityMain5x5Binding


class MainActivity5x5 : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityMain5x5Binding
    private lateinit var board: Array<Array<Button>>
    private var PLAYER = true
    private var LAST_GAME_STARTER_PLAYER = true
    private var TURN_COUNT = 0
    private var boardStatus = Array(5) { IntArray(5) }
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
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main5x5)
        FIRST_PLAYER_NAME = intent.getStringExtra("PLAYER1").toString()
        FIRST_PLAYER_NAME = makeSentenceCase(FIRST_PLAYER_NAME)
        SECOND_PLAYER_NAME = intent.getStringExtra("PLAYER2").toString()
        SECOND_PLAYER_NAME = makeSentenceCase(SECOND_PLAYER_NAME)
        binding.firstPlayer.setText(FIRST_PLAYER_NAME + " - X")
        binding.p1Score.setText("" + PLAYER1_SCORE)
        binding.secondPlayer.setText(SECOND_PLAYER_NAME + " - 0")
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
        binding.adViewBottom.loadAd(adRequest)



        board = arrayOf(
            arrayOf(binding.R1C1, binding.R1C2, binding.R1C3, binding.R1C4, binding.R1C5),
            arrayOf(binding.R2C1, binding.R2C2, binding.R2C3, binding.R2C4, binding.R2C5),
            arrayOf(binding.R3C1, binding.R3C2, binding.R3C3, binding.R3C4, binding.R3C5),
            arrayOf(binding.R4C1, binding.R4C2, binding.R4C3, binding.R4C4, binding.R4C5),
            arrayOf(binding.R5C1, binding.R5C2, binding.R5C3, binding.R5C4, binding.R5C5)
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

            for (i in 0..4) {
                for (j in 0..4) {
                    board[i][j].setBackgroundResource(R.drawable.button_shape)
                }
            }
            haptic()
        }

        binding.reset.setOnClickListener() {
            PLAYER1_SCORE = 0
            PLAYER2_SCORE = 0
            updateScore()
            haptic()
        }

        binding.menu.setOnClickListener() {
            val menuFragment = MenuFragment()
            menuFragment.show(supportFragmentManager, menuFragment.getTag())
            haptic()
        }


    }

    private fun initializeBoardStatus() {
        for (i in 0..4) {
            for (j in 0..4) {
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
                if(PLAYER){
                    binding.R1C1.setBackgroundResource(R.drawable.p1_button_clicked_shape)
                }else{
                    binding.R1C1.setBackgroundResource(R.drawable.p2_button_clicked_shape)
                }
                haptic()
            }

            R.id.R1C2 -> {
                updateValue(row = 0, col = 1, player = PLAYER)
                if(PLAYER){
                    binding.R1C2.setBackgroundResource(R.drawable.p1_button_clicked_shape)
                }else{
                    binding.R1C2.setBackgroundResource(R.drawable.p2_button_clicked_shape)
                }
                haptic()
            }

            R.id.R1C3 -> {
                updateValue(row = 0, col = 2, player = PLAYER)
                if(PLAYER){
                    binding.R1C3.setBackgroundResource(R.drawable.p1_button_clicked_shape)
                }else{
                    binding.R1C3.setBackgroundResource(R.drawable.p2_button_clicked_shape)
                }
                haptic()
            }

            R.id.R1C4 -> {
                updateValue(row = 0, col = 3, player = PLAYER)
                if(PLAYER){
                    binding.R1C4.setBackgroundResource(R.drawable.p1_button_clicked_shape)
                }else{
                    binding.R1C4.setBackgroundResource(R.drawable.p2_button_clicked_shape)
                }
                haptic()
            }

            R.id.R1C5 -> {
                updateValue(row = 0, col = 4, player = PLAYER)
                if(PLAYER){
                    binding.R1C5.setBackgroundResource(R.drawable.p1_button_clicked_shape)
                }else{
                    binding.R1C5.setBackgroundResource(R.drawable.p2_button_clicked_shape)
                }
                haptic()
            }

            R.id.R2C1 -> {
                updateValue(row = 1, col = 0, player = PLAYER)
                if(PLAYER){
                    binding.R2C1.setBackgroundResource(R.drawable.p1_button_clicked_shape)
                }else{
                    binding.R2C1.setBackgroundResource(R.drawable.p2_button_clicked_shape)
                }
                haptic()
            }

            R.id.R2C2 -> {
                updateValue(row = 1, col = 1, player = PLAYER)
                if(PLAYER){
                    binding.R2C2.setBackgroundResource(R.drawable.p1_button_clicked_shape)
                }else{
                    binding.R2C2.setBackgroundResource(R.drawable.p2_button_clicked_shape)
                }
                haptic()
            }

            R.id.R2C3 -> {
                updateValue(row = 1, col = 2, player = PLAYER)
                if(PLAYER){
                    binding.R2C3.setBackgroundResource(R.drawable.p1_button_clicked_shape)
                }else{
                    binding.R2C3.setBackgroundResource(R.drawable.p2_button_clicked_shape)
                }
                haptic()
            }

            R.id.R2C4 -> {
                updateValue(row = 1, col = 3, player = PLAYER)
                if(PLAYER){
                    binding.R2C4.setBackgroundResource(R.drawable.p1_button_clicked_shape)
                }else{
                    binding.R2C4.setBackgroundResource(R.drawable.p2_button_clicked_shape)
                }
                haptic()
            }

            R.id.R2C5 -> {
                updateValue(row = 1, col = 4, player = PLAYER)
                if(PLAYER){
                    binding.R2C5.setBackgroundResource(R.drawable.p1_button_clicked_shape)
                }else{
                    binding.R2C5.setBackgroundResource(R.drawable.p2_button_clicked_shape)
                }
                haptic()
            }

            R.id.R3C1 -> {
                updateValue(row = 2, col = 0, player = PLAYER)
                if(PLAYER){
                    binding.R3C1.setBackgroundResource(R.drawable.p1_button_clicked_shape)
                }else{
                    binding.R3C1.setBackgroundResource(R.drawable.p2_button_clicked_shape)
                }
                haptic()
            }

            R.id.R3C2 -> {
                updateValue(row = 2, col = 1, player = PLAYER)
                if(PLAYER){
                    binding.R3C2.setBackgroundResource(R.drawable.p1_button_clicked_shape)
                }else{
                    binding.R3C2.setBackgroundResource(R.drawable.p2_button_clicked_shape)
                }
                haptic()
            }

            R.id.R3C3 -> {
                updateValue(row = 2, col = 2, player = PLAYER)
                if(PLAYER){
                    binding.R3C3.setBackgroundResource(R.drawable.p1_button_clicked_shape)
                }else{
                    binding.R3C3.setBackgroundResource(R.drawable.p2_button_clicked_shape)
                }
                haptic()
            }

            R.id.R3C4 -> {
                updateValue(row = 2, col = 3, player = PLAYER)
                if(PLAYER){
                    binding.R3C4.setBackgroundResource(R.drawable.p1_button_clicked_shape)
                }else{
                    binding.R3C4.setBackgroundResource(R.drawable.p2_button_clicked_shape)
                }
                haptic()
            }

            R.id.R3C5 -> {
                updateValue(row = 2, col = 4, player = PLAYER)
                if(PLAYER){
                    binding.R3C5.setBackgroundResource(R.drawable.p1_button_clicked_shape)
                }else{
                    binding.R3C5.setBackgroundResource(R.drawable.p2_button_clicked_shape)
                }
                haptic()
            }

            R.id.R4C1 -> {
                updateValue(row = 3, col = 0, player = PLAYER)
                if(PLAYER){
                    binding.R4C1.setBackgroundResource(R.drawable.p1_button_clicked_shape)
                }else{
                    binding.R4C1.setBackgroundResource(R.drawable.p2_button_clicked_shape)
                }
                haptic()
            }

            R.id.R4C2 -> {
                updateValue(row = 3, col = 1, player = PLAYER)
                if(PLAYER){
                    binding.R4C2.setBackgroundResource(R.drawable.p1_button_clicked_shape)
                }else{
                    binding.R4C2.setBackgroundResource(R.drawable.p2_button_clicked_shape)
                }
                haptic()
            }

            R.id.R4C3 -> {
                updateValue(row = 3, col = 2, player = PLAYER)
                if(PLAYER){
                    binding.R4C3.setBackgroundResource(R.drawable.p1_button_clicked_shape)
                }else{
                    binding.R4C3.setBackgroundResource(R.drawable.p2_button_clicked_shape)
                }
                haptic()
            }

            R.id.R4C4 -> {
                updateValue(row = 3, col = 3, player = PLAYER)
                if(PLAYER){
                    binding.R4C4.setBackgroundResource(R.drawable.p1_button_clicked_shape)
                }else{
                    binding.R4C4.setBackgroundResource(R.drawable.p2_button_clicked_shape)
                }
                haptic()
            }

            R.id.R4C5 -> {
                updateValue(row = 3, col = 4, player = PLAYER)
                if(PLAYER){
                    binding.R4C5.setBackgroundResource(R.drawable.p1_button_clicked_shape)
                }else{
                    binding.R4C5.setBackgroundResource(R.drawable.p2_button_clicked_shape)
                }
                haptic()
            }

            R.id.R5C1 -> {
                updateValue(row = 4, col = 0, player = PLAYER)
                if(PLAYER){
                    binding.R5C1.setBackgroundResource(R.drawable.p1_button_clicked_shape)
                }else{
                    binding.R5C1.setBackgroundResource(R.drawable.p2_button_clicked_shape)
                }
                haptic()
            }

            R.id.R5C2 -> {
                updateValue(row = 4, col = 1, player = PLAYER)
                if(PLAYER){
                    binding.R5C2.setBackgroundResource(R.drawable.p1_button_clicked_shape)
                }else{
                    binding.R5C2.setBackgroundResource(R.drawable.p2_button_clicked_shape)
                }
                haptic()
            }

            R.id.R5C3 -> {
                updateValue(row = 4, col = 2, player = PLAYER)
                if(PLAYER){
                    binding.R5C3.setBackgroundResource(R.drawable.p1_button_clicked_shape)
                }else{
                    binding.R5C3.setBackgroundResource(R.drawable.p2_button_clicked_shape)
                }
                haptic()
            }

            R.id.R5C4 -> {
                updateValue(row = 4, col = 3, player = PLAYER)
                if(PLAYER){
                    binding.R5C4.setBackgroundResource(R.drawable.p1_button_clicked_shape)
                }else{
                    binding.R5C4.setBackgroundResource(R.drawable.p2_button_clicked_shape)
                }
                haptic()
            }

            R.id.R5C5 -> {
                updateValue(row = 4, col = 4, player = PLAYER)
                if(PLAYER){
                    binding.R5C5.setBackgroundResource(R.drawable.p1_button_clicked_shape)
                }else{
                    binding.R5C5.setBackgroundResource(R.drawable.p2_button_clicked_shape)
                }
                haptic()
            }

        }
        PLAYER = !PLAYER
        TURN_COUNT++

        if (PLAYER) {
            updateDisplay(FIRST_PLAYER_NAME + "'s turn")
        } else {
            updateDisplay(SECOND_PLAYER_NAME + "'s turn")
        }

        if (TURN_COUNT == 25) {
            updateDisplay("GAME OVER - It's a Draw")
        }

        checkWinner()
    }

    private fun checkWinner() {
        //checking Rows
        for (i in 0..4) {
            if (boardStatus[i][0] == boardStatus[i][1] && boardStatus[i][0] == boardStatus[i][2] && boardStatus[i][0] == boardStatus[i][3] && boardStatus[i][0] == boardStatus[i][4]) {
                if (boardStatus[i][0] == 1) {
                    updateDisplay(FIRST_PLAYER_NAME + " is winner")
                    PLAYER1_SCORE += 1
                    updateScore()

                    for (j in 0..4) {
                        board[i][j].setBackgroundResource(R.drawable.winner_button_background)
                    }
                    break
                } else if (boardStatus[i][0] == 0) {
                    updateDisplay(SECOND_PLAYER_NAME + " is winner")
                    PLAYER2_SCORE += 1
                    updateScore()
                    for (j in 0..4) {
                        board[i][j].setBackgroundResource(R.drawable.winner_button_background)
                    }
                    break
                }
            }
        }

        for (i in 0..4) {
            if (boardStatus[0][i] == boardStatus[1][i] && boardStatus[0][i] == boardStatus[2][i]  && boardStatus[0][i] == boardStatus[3][i] && boardStatus[0][i] == boardStatus[4][i]) {
                if (boardStatus[0][i] == 1) {
                    updateDisplay(FIRST_PLAYER_NAME + " is winner")
                    PLAYER1_SCORE += 1
                    updateScore()
                    for (j in 0..4) {
                        board[j][i].setBackgroundResource(R.drawable.winner_button_background)
                    }
                    break
                } else if (boardStatus[0][i] == 0) {
                    updateDisplay(SECOND_PLAYER_NAME + " is winner")
                    PLAYER2_SCORE += 1
                    updateScore()
                    for (j in 0..4) {
                        board[j][i].setBackgroundResource(R.drawable.winner_button_background)
                    }
                    break
                }
            }
        }

        //diagonal 1
        if (boardStatus[0][0] == boardStatus[1][1] && boardStatus[0][0] == boardStatus[2][2] && boardStatus[0][0] == boardStatus[3][3] && boardStatus[0][0] == boardStatus[4][4]) {
            if (boardStatus[0][0] == 1) {
                updateDisplay(FIRST_PLAYER_NAME + " is winner")
                PLAYER1_SCORE += 1
                updateScore()
                for (i in 0..4) {
                    board[i][i].setBackgroundResource(R.drawable.winner_button_background)
                }
            } else if (boardStatus[0][0] == 0) {
                updateDisplay(SECOND_PLAYER_NAME + " is winner")
                PLAYER2_SCORE += 1
                updateScore()
                for (i in 0..4) {
                    board[i][i].setBackgroundResource(R.drawable.winner_button_background)
                }
            }
        }

        //diagonal 2
        if (boardStatus[0][4] == boardStatus[1][3] && boardStatus[1][3] == boardStatus[2][2] && boardStatus[2][2] == boardStatus[3][1]  && boardStatus[3][1] == boardStatus[4][0]) {
            if (boardStatus[0][4] == 1) {
                updateDisplay(FIRST_PLAYER_NAME + " is winner")
                PLAYER1_SCORE += 1
                updateScore()
                board[0][4].setBackgroundResource(R.drawable.winner_button_background)
                board[1][3].setBackgroundResource(R.drawable.winner_button_background)
                board[2][2].setBackgroundResource(R.drawable.winner_button_background)
                board[3][1].setBackgroundResource(R.drawable.winner_button_background)
                board[4][0].setBackgroundResource(R.drawable.winner_button_background)
            } else if (boardStatus[0][4] == 0) {
                updateDisplay(SECOND_PLAYER_NAME + " is winner")
                PLAYER2_SCORE += 1
                updateScore()
                board[0][4].setBackgroundResource(R.drawable.winner_button_background)
                board[1][3].setBackgroundResource(R.drawable.winner_button_background)
                board[2][2].setBackgroundResource(R.drawable.winner_button_background)
                board[3][1].setBackgroundResource(R.drawable.winner_button_background)
                board[4][0].setBackgroundResource(R.drawable.winner_button_background)
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
        for (i in 0..4) {
            for (j in 0..4) {
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

    @SuppressLint("NewApi")
    private fun haptic() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager

            vibrator = vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        }
        val vibrationEffect = VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK)
        vibrator.vibrate(vibrationEffect)
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
        musicPlayer.release()
    }
}