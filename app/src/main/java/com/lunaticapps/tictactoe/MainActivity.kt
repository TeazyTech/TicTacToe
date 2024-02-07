package com.lunaticapps.tictactoe

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.lunaticapps.tictactoe.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var  binding: ActivityMainBinding
    lateinit var board: Array<Array<Button>>
    var PLAYER = true
    var TURN_COUNT = 0
    var boardStatus = Array(3){IntArray(3)}
    var PLAYER1_SCORE = 0
    var PLAYER2_SCORE = 0
    lateinit var FIRST_PLAYER_NAME: String
    lateinit var SECOND_PLAYER_NAME: String
    lateinit var vibrator: Vibrator
    lateinit var mediaPlayer: MediaPlayer


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        FIRST_PLAYER_NAME = intent.getStringExtra("PLAYER1").toString()
        SECOND_PLAYER_NAME = intent.getStringExtra("PLAYER2").toString()
        binding.firstPlayer.setText(FIRST_PLAYER_NAME+"")
        binding.p1Score.setText(""+PLAYER1_SCORE)
        binding.secondPlayer.setText(SECOND_PLAYER_NAME+"")
        binding.p2Score.setText(""+PLAYER2_SCORE)
        binding.displayText.setText(FIRST_PLAYER_NAME+"'s turn")




        board = arrayOf(
            arrayOf(binding.R1C1, binding.R1C2, binding.R1C3),
            arrayOf(binding.R2C1, binding.R2C2, binding.R2C3),
            arrayOf(binding.R3C1, binding.R3C2, binding.R3C3)
        )

        for(i: Array<Button> in board){
            for (button : Button in i){
                button.setOnClickListener(this)
            }
        }

        initializeBoardStatus();

        binding.restart.setOnClickListener(){
            initializeBoardStatus()
            PLAYER = true
            TURN_COUNT = 0
            binding.displayText.setText(FIRST_PLAYER_NAME+"'s turn")
            for(i in 0..2){
                for (j in 0..2){
                    board[i][j].setBackgroundResource(R.drawable.button_shape)
                }
            }


        }

        binding.reset.setOnClickListener(){
            PLAYER1_SCORE = 0
            PLAYER2_SCORE = 0
            updateScore()
        }

        binding.menu.setOnClickListener(){
            val menuFragment = MenuFragment()
            menuFragment.show(supportFragmentManager, menuFragment.getTag())
        }


    }

    private fun initializeBoardStatus() {
        for(i in 0..2){
            for(j in 0..2){
                boardStatus[i][j] = -1
                board[i][j].isEnabled = true
                board[i][j].text = ""
            }
        }
    }

    @SuppressLint("ResourceType")
    override fun onClick(v: View) {
        when(v.id){
            R.id.R1C1 ->{
                updateValue(row = 0, col = 0, player = PLAYER)
                binding.R1C1.setBackgroundResource(R.drawable.button_clicked_shape)
            }
            R.id.R1C2 ->{
                updateValue(row = 0, col = 1, player = PLAYER)
                binding.R1C2.setBackgroundResource(R.drawable.button_clicked_shape)
            }
            R.id.R1C3 ->{
                updateValue(row = 0, col = 2, player = PLAYER)
                binding.R1C3.setBackgroundResource(R.drawable.button_clicked_shape)
            }
            R.id.R2C1 ->{
                updateValue(row = 1, col = 0, player = PLAYER)
                binding.R2C1.setBackgroundResource(R.drawable.button_clicked_shape)
            }
            R.id.R2C2 ->{
                updateValue(row = 1, col = 1, player = PLAYER)
                binding.R2C2.setBackgroundResource(R.drawable.button_clicked_shape)
            }
            R.id.R2C3 ->{
                updateValue(row = 1, col = 2, player = PLAYER)
                binding.R2C3.setBackgroundResource(R.drawable.button_clicked_shape)
            }
            R.id.R3C1 ->{
                updateValue(row = 2, col = 0, player = PLAYER)
                binding.R3C1.setBackgroundResource(R.drawable.button_clicked_shape)
            }
            R.id.R3C2 ->{
                updateValue(row = 2, col = 1, player = PLAYER)
                binding.R3C2.setBackgroundResource(R.drawable.button_clicked_shape)
            }
            R.id.R3C3 ->{
                updateValue(row = 2, col = 2, player = PLAYER)
                binding.R3C3.setBackgroundResource(R.drawable.button_clicked_shape)
            }

        }
        PLAYER = !PLAYER
        TURN_COUNT++

        if(PLAYER){
            updateDisplay(FIRST_PLAYER_NAME + "'s turn")
        }else{
            updateDisplay(SECOND_PLAYER_NAME+ "'s turn")
        }

        if(TURN_COUNT == 9){
            updateDisplay("GAME OVER - It's a Draw")
        }

        checkWinner()
    }

    private fun checkWinner() {
        //checking Rows
        for(i in 0..2){
            if(boardStatus[i][0] == boardStatus[i][1] && boardStatus[i][1] == boardStatus[i][2]){
                if(boardStatus[i][0] == 1){
                    updateDisplay(FIRST_PLAYER_NAME+ " is winner")
                    PLAYER1_SCORE +=1
                    updateScore()

                    for(j in 0..2){
                        board[i][j].setBackgroundResource(R.drawable.winner_button_background)
                    }
                    break
                }else if(boardStatus[i][0] == 0){
                    updateDisplay(SECOND_PLAYER_NAME+" is winner")
                    PLAYER2_SCORE +=1
                    updateScore()
                    for(j in 0..2){
                        board[i][j].setBackgroundResource(R.drawable.winner_button_background)
                    }
                    break
                }
            }
        }

        for(i in 0..2){
            if(boardStatus[0][i] == boardStatus[1][i] && boardStatus[1][i] == boardStatus[2][i]){
                if(boardStatus[0][i] == 1){
                    updateDisplay(FIRST_PLAYER_NAME+" is winner")
                    PLAYER1_SCORE +=1
                    updateScore()
                    for(j in 0..2){
                        board[j][i].setBackgroundResource(R.drawable.winner_button_background)
                    }
                    break
                }else if(boardStatus[0][i] == 0){
                    updateDisplay(SECOND_PLAYER_NAME+" is winner")
                    PLAYER2_SCORE +=1
                    updateScore()
                    for(j in 0..2){
                        board[j][i].setBackgroundResource(R.drawable.winner_button_background)
                    }
                    break
                }
            }
        }

        if(boardStatus[0][0] == boardStatus[1][1] && boardStatus[1][1] == boardStatus[2][2]){
            if(boardStatus[0][0] == 1){
                updateDisplay(FIRST_PLAYER_NAME+" is winner")
                PLAYER1_SCORE +=1
                updateScore()
                for(i in 0..2){
                    board[i][i].setBackgroundResource(R.drawable.winner_button_background)
                }
            }else if(boardStatus[0][0] == 0){
                updateDisplay(SECOND_PLAYER_NAME+" is winner")
                PLAYER2_SCORE +=1
                updateScore()
                for(i in 0..2){
                    board[i][i].setBackgroundResource(R.drawable.winner_button_background)
                }
            }
        }

        if(boardStatus[0][2] == boardStatus[1][1] && boardStatus[1][1] == boardStatus[2][0]){
            if(boardStatus[0][2] == 1){
                updateDisplay(FIRST_PLAYER_NAME+" is winner")
                PLAYER1_SCORE +=1
                updateScore()
                board[0][2].setBackgroundResource(R.drawable.winner_button_background)
                board[1][1].setBackgroundResource(R.drawable.winner_button_background)
                board[2][0].setBackgroundResource(R.drawable.winner_button_background)
            }else if(boardStatus[0][2] == 0){
                updateDisplay(SECOND_PLAYER_NAME+" is winner")
                PLAYER2_SCORE +=1
                updateScore()
                board[0][2].setBackgroundResource(R.drawable.winner_button_background)
                board[1][1].setBackgroundResource(R.drawable.winner_button_background)
                board[2][0].setBackgroundResource(R.drawable.winner_button_background)
            }
        }

    }

    private fun updateScore() {
        binding.p1Score.setText(""+PLAYER1_SCORE)
        binding.p2Score.setText(""+PLAYER2_SCORE)
    }

    private fun updateDisplay(s: String) {
        binding.displayText.setText(s)
        if(s.contains("winner")){
            disableButtons()
            vivrate()
            binding.animation.setAnimation(R.raw.anim1)
            binding.animation1.setAnimation(R.raw.anim2)
            binding.animation2.setAnimation(R.raw.anim3)
            mediaPlayer = MediaPlayer.create(applicationContext, R.raw.fireworks_sound)
            mediaPlayer.start()
            binding.animation.playAnimation()
            binding.animation1.playAnimation()
            binding.animation2.playAnimation()

        }
    }

    private fun disableButtons() {
        for(i in 0..2){
            for(j in 0..2){
                board[i][j].isEnabled = false
            }
        }
    }

    private fun updateValue(row: Int, col: Int, player: Boolean) {
        val text = if(PLAYER) "X" else "0"
        val value = if(PLAYER) 1 else 0
        board[row][col].apply {
            setText(text)
            isEnabled = false
        }
        boardStatus[row][col] = value
    }

    private fun vivrate() {
        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(200)
        }
    }
}