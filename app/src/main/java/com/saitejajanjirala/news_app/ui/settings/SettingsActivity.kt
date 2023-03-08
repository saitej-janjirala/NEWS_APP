package com.saitejajanjirala.news_app.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saitejajanjirala.news_app.R
import com.saitejajanjirala.news_app.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

    }
}