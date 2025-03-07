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


import java.util.Locale;
import com.example.booking4.DataBase.SQLite;
import com.example.booking4.GlobalData;
import com.example.booking4.Models.Setting;
import com.example.booking4.Models.User;
import com.example.booking4.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        getUserFromFirebase();
        binding.btnSwaplanguage.setOnClickListener(view -> {
            if (Locale.getDefault().getLanguage().equals("en")) {
                changeLanguage("vi");
            } else {
                changeLanguage("en");
            }
                });
        binding.btnLogout.setOnClickListener(view ->
        {
            FirebaseAuth.getInstance().signOut();

            Intent intent = requireActivity().getIntent();
            requireActivity().finish();
            startActivity(intent);
        });
        binding.evUserEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        binding.evUserName.setText(GlobalData.userGlobal.getName());

    }
    private void changeLanguage(String lang) {
        SQLite sqLite=new SQLite(getContext());
        Setting setting=sqLite.GetSetting();
        setting.setLanguages(lang);

        sqLite.UpdateSetting(setting);

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources res = getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.setLocale(locale);
        res.updateConfiguration(config, res.getDisplayMetrics());

        Intent intent = requireActivity().getIntent();
        requireActivity().finish();
        startActivity(intent);
    }
    private void getUserFromFirebase() {
        FirebaseUser userCurrent= FirebaseAuth.getInstance().getCurrentUser();
        if (userCurrent != null) {
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users").child(userCurrent.getUid());
            usersRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult().exists()) {
                    User user= task.getResult().getValue(User.class);
                    GlobalData.userGlobal = user;
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Hủy binding để tránh memory leak
        binding = null;
    }
}