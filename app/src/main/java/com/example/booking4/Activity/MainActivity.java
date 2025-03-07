package com.example.booking4.Activity;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import com.example.booking4.DataBase.SQLite;
import com.example.booking4.Fragment.CartFragment;
import com.example.booking4.Fragment.ExplorerFragment;
import com.example.booking4.Fragment.LoginFragment;
import com.example.booking4.Fragment.ProfileFragment;

import com.example.booking4.Fragment.SearchFragment;
import com.example.booking4.Fragment.SignupFragment;
import com.example.booking4.GlobalData;
import com.example.booking4.Models.Setting;
import com.example.booking4.Models.User;
import com.example.booking4.R;
import com.example.booking4.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getUserFromFirebase();
        // Thiết lập Fragment mặc định là Home
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, new ExplorerFragment())
                .commit();

        binding.chipnav.setOnItemSelectedListener(itemId -> {
            Fragment selectedFragment = null;
            if (itemId == R.id.item_explorer) {
                selectedFragment = new ExplorerFragment();
            } else if (itemId == R.id.item_search) {
                selectedFragment = new SearchFragment();
            } else if (itemId == R.id.item_cart) {
                selectedFragment = new CartFragment();
            } else if (itemId == R.id.item_profile) {
                selectedFragment = ClickItemProfile();
            }
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, selectedFragment)
                        .commit();
            }
        });

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
    private Fragment ClickItemProfile() {
        // Kiểm tra xem người dùng đã đăng nhập chưa
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            return new LoginFragment();
        }
        return new ProfileFragment();
    }
}
