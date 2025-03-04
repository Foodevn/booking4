package com.example.booking4.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.booking4.DataBase.SQLite;
import com.example.booking4.Models.Setting;
import com.example.booking4.databinding.ActivityIntroBinding;

import java.util.Locale;


public class IntroActivity extends AppCompatActivity {
    private ActivityIntroBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();

        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.startBtn.setOnClickListener(view -> {
            startActivity(new Intent(IntroActivity.this, MainActivity.class));
            finish();
        });
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }

    private void loadLocale() {
        SQLite sqLite=new SQLite(this);
        Setting setting=sqLite.GetSetting();
        String lang = setting.getLanguages();

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources res = getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.setLocale(locale);
        res.updateConfiguration(config, res.getDisplayMetrics());

    }


}
