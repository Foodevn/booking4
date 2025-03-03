package com.example.booking4.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;
import com.example.booking4.databinding.FragmentProfileBinding;



public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentProfileBinding.inflate(inflater,container,false);

        run();
        return binding.getRoot();
    }

    private void run() {
//        binding.btnEnglish.setOnClickListener(view -> {
//            changeLanguage("en");
//        });
//        binding.btnVietNamese.setOnClickListener(view -> {
//            changeLanguage("vi");
//        });

    }

//    private void changeLanguage(String en) {
//        Locale locale = new Locale(en);
//        Locale.setDefault(locale);
//        Resources res = getResources();
//        Configuration config = new Configuration(res.getConfiguration());
//        config.setLocale(locale);
//        res.updateConfiguration(config, res.getDisplayMetrics());
//
//        // Lưu ngôn ngữ vào SharedPreferences
//        SharedPreferences.Editor editor = requireActivity().getSharedPreferences("Settings", requireActivity().MODE_PRIVATE).edit();
//        editor.putString("My_Lang", en);
//        editor.apply();
//
//        // Cập nhật lại toàn bộ giao diện
//        requireActivity().recreate();
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Hủy binding để tránh memory leak
        binding = null;
    }
}