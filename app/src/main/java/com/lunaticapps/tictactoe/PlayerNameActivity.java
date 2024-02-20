package com.lunaticapps.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.lunaticapps.tictactoe.databinding.ActivityPlayerNameBinding;

import java.util.Objects;

public class PlayerNameActivity extends AppCompatActivity {

    ActivityPlayerNameBinding binding;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_player_name);

        binding.enterBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(binding.player1.getText()) || TextUtils.isEmpty(binding.player2.getText())) {
                    binding.msgTXT.setVisibility(View.VISIBLE);
                    vivrate();


                    binding.msgTXT.setText("Enter both player's name");
                } else {
                    haptic();
                    String player1Name = binding.player1.getText().toString();
                    String player2Name = binding.player2.getText().toString();
                    String game = getIntent().getStringExtra("GAME");
                    Intent intent;
                    if(Objects.equals(game, "3x3")){
                        intent = new Intent(PlayerNameActivity.this, MainActivity.class);
                    } else if (Objects.equals(game, "4x4")) {
                        intent = new Intent(PlayerNameActivity.this, MainActivity4x4.class);
                    } else{
                        intent = new Intent(PlayerNameActivity.this, MainActivity5x5.class);
                    }
                    intent.putExtra("PLAYER1", player1Name);
                    intent.putExtra("PLAYER2", player2Name);
                    startActivity(intent);

                }

            }
        });
    }

    private void vivrate() {
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(200);
        }
    }

     private void haptic() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            VibratorManager vibratorManager = getSystemService(VibratorManager.class);
            vibrator = vibratorManager.getDefaultVibrator();
        } else {
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        }

        if (vibrator != null && vibrator.hasVibrator()) {
            VibrationEffect vibrationEffect = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                vibrationEffect = VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK);
            }
            vibrator.vibrate(vibrationEffect);
        }
    }


}