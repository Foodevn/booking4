package com.example.booking4.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.booking4.Models.Film;
import com.example.booking4.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class test extends AppCompatActivity {

    TextView tv_test;
    ArrayList<Film> listFlim = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tv_test = findViewById(R.id.tv_test);

        // Khởi tạo Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Items");

        // Lấy dữ liệu từ Firebase
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    listFlim.clear();
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        Film film = issue.getValue(Film.class);
                        if (film != null) {
                            listFlim.add(film);
                        }
                    }
                    for (Film banner : listFlim) {
                        tv_test.append("\n" + banner.getTitle());
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                tv_test.append("\n lỗi lấy dữ liệu");
            }
        });

    }
}