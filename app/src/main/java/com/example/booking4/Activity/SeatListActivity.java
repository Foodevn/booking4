package com.example.booking4.Activity;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.booking4.Adapter.DateAdapter;
import com.example.booking4.Adapter.SeatListAdapter;
import com.example.booking4.Adapter.TimeAdapter;
import com.example.booking4.Models.Film;
import com.example.booking4.Models.Seat;
import com.example.booking4.databinding.ActivitySeatListBinding;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SeatListActivity extends AppCompatActivity {
    private ActivitySeatListBinding binding;
    private Film film;
    private double price = 0.0;
    private int number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySeatListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        getIntentExtra();
        setVariable();
        initSeatsList();
    }

    private void initSeatsList() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 7);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position % 7 == 3) ? 1 : 1;
            }
        });

        binding.seatRecyclerview.setLayoutManager(gridLayoutManager);

        List<Seat> seatList = new ArrayList<>();
        int numberSeats = 81;

        for (int i = 0; i < numberSeats; i++) {
            String seatName = ""; // Bạn cần triển khai logic tạo tên ghế
            Seat.SeatStatus seatStatus =
                    (i == 2 || i == 20 || i == 33 || i == 41 || i == 50 || i == 72 || i == 73) ?
                            Seat.SeatStatus.UNAVAILABLE :
                            Seat.SeatStatus.AVAILABLE;

            seatList.add(new Seat(seatStatus, seatName));
        }

        SeatListAdapter seatAdapter = new SeatListAdapter(seatList, this, new SeatListAdapter.SelectedSeat() {
            @Override
            public void Return(String selectedName, int num) {
                binding.numberSelectedTxt.setText(num + " Seat Selected");
                DecimalFormat df = new DecimalFormat("#.##");
                price = Double.parseDouble(df.format(num * film.getPrice()));
                number = num;
                binding.priceTxt.setText("$" + price);
            }
        });

        binding.seatRecyclerview.setAdapter(seatAdapter);
        binding.seatRecyclerview.setNestedScrollingEnabled(false);

        binding.TimeRecyclerview.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false
        ));
        binding.TimeRecyclerview.setAdapter(new TimeAdapter(generateTimeSlots()));

        binding.dateRecyclerview.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false
        ));
        binding.dateRecyclerview.setAdapter(new DateAdapter(generateDates()));
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(v -> finish());
    }

    private void getIntentExtra() {
        film = getIntent().getParcelableExtra("film");
    }

    private List<String> generateTimeSlots() {
        List<String> timeSlots = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

        for (int i = 0; i < 24; i += 2) {
            LocalTime time = LocalTime.of(i, 0);
            timeSlots.add(time.format(formatter));
        }
        return timeSlots;
    }

    private List<String> generateDates() {
        List<String> dates = new ArrayList<>();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE/dd/MMM");

        for (int i = 0; i < 7; i++) {
            dates.add(today.plusDays(i).format(formatter));
        }

        return dates;
    }
}
