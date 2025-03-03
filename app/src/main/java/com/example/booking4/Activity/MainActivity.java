package com.example.booking4.Activity;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.booking4.Fragment.BlankFragment;
import com.example.booking4.Fragment.CartFragment;
import com.example.booking4.Fragment.ExplorerFragment;
import com.example.booking4.Fragment.ProfileFragment;
import com.example.booking4.Fragment.SearchFragment;
import com.example.booking4.Models.Film;
import com.example.booking4.R;
import com.example.booking4.databinding.ActivityMainBinding;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    ArrayList<Film> listFlim = new ArrayList<>();


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



        // Thiết lập Fragment mặc định là Home
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, new ExplorerFragment())
                .commit();

        binding.chipnav.setOnItemSelectedListener(itemId -> {
            Fragment selectedFragment = null;
            if (itemId ==  R.id.item_explorer) {
                selectedFragment = new ExplorerFragment();
            } else if (itemId == R.id.item_search) {
                selectedFragment = new SearchFragment();
            }else if (itemId == R.id.item_cart) {
                selectedFragment = new CartFragment();
            }else if (itemId == R.id.item_profile) {
                selectedFragment = new ProfileFragment();
            }
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, selectedFragment)
                        .commit();
            }
        });

    }

}
