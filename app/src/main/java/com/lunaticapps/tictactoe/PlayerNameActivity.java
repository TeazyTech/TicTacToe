package com.lunaticapps.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;

import com.lunaticapps.tictactoe.databinding.ActivityPlayerNameBinding;

public class PlayerNameActivity extends AppCompatActivity {

    ActivityPlayerNameBinding binding;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_player_name);

        binding.enterBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(binding.player1.getText()) || TextUtils.isEmpty(binding.player2.getText())) {
                    binding.msgTXT.setVisibility(View.VISIBLE);
                    vivrate();


                    binding.msgTXT.setText("Enter both player's name");
                } else {
                    String player1Name = binding.player1.getText().toString();
                    String player2Name = binding.player2.getText().toString();
                    Intent intent = new Intent(PlayerNameActivity.this, MainActivity.class);
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


}