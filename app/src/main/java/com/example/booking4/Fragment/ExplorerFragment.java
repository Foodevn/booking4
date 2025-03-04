package com.example.booking4.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.booking4.Adapter.FilmListAdapter;
import com.example.booking4.Adapter.SliderAdapter;
import com.example.booking4.Models.Film;
import com.example.booking4.Models.SliderItem;
import com.example.booking4.databinding.FragmentTestBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ExplorerFragment extends Fragment {
    private FragmentTestBinding binding;
    private FirebaseDatabase database;
    private final Handler sliderHandler = new Handler(Looper.getMainLooper());
    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            binding.viewPager2.setCurrentItem(binding.viewPager2.getCurrentItem() + 1, true);
            sliderHandler.postDelayed(this, 3000); // Sử dụng handler.postDelayed() thay vì this
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentTestBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance();

        initTopMoving();
        initBanner();
        initUpComming();

        return binding.getRoot();

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
                                        getContext(),
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
                                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
                        );
                        binding.recyclerViewUpcomming.setAdapter(new FilmListAdapter(items));
                    }
                    binding.progressBarUpcomming.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initBanner() {
        DatabaseReference myRef = database.getReference("Banners");

        binding.progressBarSlider.setVisibility(View.VISIBLE);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<SliderItem> lists = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    SliderItem item = childSnapshot.getValue(SliderItem.class);
                    if (item != null) {
                        lists.add(item);
                    }
                }
                binding.progressBarSlider.setVisibility(View.GONE);
                banners(lists);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load banners: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void banners(List<SliderItem> lists) {
        binding.viewPager2.setAdapter(new SliderAdapter(lists));
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

        binding.circleIndicator3.createIndicators(lists.size(), 0);

        binding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                int realPosition = (position - 1 + lists.size()) % lists.size();
                binding.circleIndicator3.animatePageSelected(realPosition);
                sliderHandler.removeCallbacks(sliderRunnable);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == binding.viewPager2.SCROLL_STATE_IDLE) {
                    int curr = binding.viewPager2.getCurrentItem();
                    int lastReal = binding.viewPager2.getAdapter().getItemCount() - 2;
                    if (curr == 0) {
                        binding.viewPager2.setCurrentItem(lastReal, false);
                    } else if (curr > lastReal) {
                        binding.viewPager2.setCurrentItem(1, false);
                    }
                }

                if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
                    // Người dùng đang kéo: dừng auto-scroll
                    sliderHandler.removeCallbacks(sliderRunnable);
                } else if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    // Khi cuộn dừng lại, khởi động lại auto-scroll sau 3000ms
                    sliderHandler.postDelayed(sliderRunnable, 3000);
                }
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 3000); // Bắt đầu tự động chạy ViewPager khi Fragment hiển thị
    }

    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable); // Dừng Runnable khi Fragment không hiển thị
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Hủy binding để tránh memory leak
        binding = null;
    }

}