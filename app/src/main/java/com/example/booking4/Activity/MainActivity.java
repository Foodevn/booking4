package com.example.booking4.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.booking4.Adapter.FilmListAdapter;
import com.example.booking4.Adapter.SliderAdapter;
import com.example.booking4.Models.Film;
import com.example.booking4.Models.SliderItems;
import com.example.booking4.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseDatabase database;
    private final Handler sliderHandler = new Handler();
    private final Runnable sliderRunnable = () -> binding.viewPager2.setCurrentItem(binding.viewPager2.getCurrentItem() + 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        initTopMoving();
        initBanner();
        initUpComming();
    }



    private void initTopMoving() {
        DatabaseReference myRef = database.getReference("Items");

        binding.progressBarTopMovies.setVisibility(View.VISIBLE);

        ArrayList<Film> items = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        Film film = issue.getValue(Film.class);
                        if (film != null) {
                            items.add(film);
                        }
                    }
                    if (!items.isEmpty()) {
                        binding.recyclerViewTopMovies.setLayoutManager(
                                new LinearLayoutManager(
                                        MainActivity.this,
                                        LinearLayoutManager.HORIZONTAL,
                                        false
                                )
                        );

                        binding.recyclerViewTopMovies.setAdapter(new FilmListAdapter(items));
                    }

                    binding.progressBarTopMovies.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Failed to read data: " + error.getMessage());
            }
        });
    }



    private void initUpComming() {
        DatabaseReference myRef = database.getReference("Upcomming");

        binding.progressBarUpcomming.setVisibility(View.VISIBLE);

        ArrayList<Film> items = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        Film film = issue.getValue(Film.class);
                        if (film != null) {
                            items.add(film);
                        }
                    }
                    if (!items.isEmpty()) {
                        binding.recyclerViewUpcomming.setLayoutManager(
                                new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false)
                        );
                        binding.recyclerViewUpcomming.setAdapter(new FilmListAdapter(items));
                    }
                    binding.progressBarUpcomming.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to load data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initBanner() {
        DatabaseReference myRef = database.getReference("Banners");

        binding.progressBarSlider.setVisibility(View.VISIBLE);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<SliderItems> lists = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    SliderItems list = childSnapshot.getValue(SliderItems.class);
                    if (list != null) {
                        lists.add(list);
                    }
                }
                binding.progressBarSlider.setVisibility(View.GONE);
                banners(lists);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to load banners: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void banners(List<SliderItems> lists) {
        binding.viewPager2.setAdapter(new SliderAdapter(lists, binding.viewPager2));
        binding.viewPager2.setClipToPadding(false);
        binding.viewPager2.setClipChildren(false);
        binding.viewPager2.setOffscreenPageLimit(3);
        binding.viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });

        binding.viewPager2.setPageTransformer(compositePageTransformer);
        binding.viewPager2.setCurrentItem(1);

        binding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                sliderHandler.removeCallbacks(sliderRunnable);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 2000);
    }
}
