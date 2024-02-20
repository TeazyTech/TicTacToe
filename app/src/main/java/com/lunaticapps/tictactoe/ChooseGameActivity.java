package com.lunaticapps.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.lunaticapps.tictactoe.databinding.ActivityChooseGameBinding;

public class ChooseGameActivity extends AppCompatActivity {

    ActivityChooseGameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_game);
        Intent intent = new Intent(ChooseGameActivity.this, PlayerNameActivity.class);
        binding.imageButton3x3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("GAME", "3x3");
                startActivity(intent);
            }
        });


        binding.imageButton4x4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("GAME", "4x4");
                startActivity(intent);
            }
        });
        binding.imageButton5x5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("GAME", "5x5");
                startActivity(intent);
            }
        });
    }
}