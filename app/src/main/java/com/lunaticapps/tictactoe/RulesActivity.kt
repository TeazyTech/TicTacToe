package com.lunaticapps.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.lunaticapps.tictactoe.databinding.ActivityRulesBinding;

import java.util.Objects;

public class RulesActivity extends AppCompatActivity {

        ActivityRulesBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rules);

        String keyword = getIntent().getStringExtra("NAME");
        if(Objects.equals(keyword, "policy")){
            binding.webView.loadUrl("file:///android_asset/privacy_policy.html");
        }else{
            binding.webView.loadUrl("file:///android_asset/game_rules.html");
        }

    }
}