package com.lunaticapps.tictactoe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.lunaticapps.tictactoe.databinding.ActivityRulesBinding

class RulesActivity : AppCompatActivity() {
    lateinit var binding: ActivityRulesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rules)
        val keyword = intent.getStringExtra("NAME")
        if (keyword == "policy") {
            binding.webView.loadUrl("file:///android_asset/privacy_policy.html")
        } else {
            binding.webView.loadUrl("file:///android_asset/game_rules.html")
        }
    }
}