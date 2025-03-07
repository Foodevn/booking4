package com.example.booking4.Activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.booking4.DataBase.SQLite;
import com.example.booking4.Models.Setting;
import com.example.booking4.Models.User;
import com.example.booking4.databinding.Test1Binding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.atomic.AtomicReference;

public class test extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Test1Binding binding = Test1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //sql
        SQLite sqLite = new SQLite(this);
        Setting setting = sqLite.GetSetting();

        //firebase
        FirebaseUser userCurrent= FirebaseAuth.getInstance().getCurrentUser();
        if (userCurrent != null) {
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users").child(userCurrent.getUid());
            usersRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult().exists()) {
                    User user= task.getResult().getValue(User.class);
                    binding.textView.setText(user.getId()+"\n"+user.getEmail()+"\n"+user.getName()+"\n"+user.getPhone()
                            +"\n"+user.getAvatar());
                    String name = task.getResult().child("name").getValue(String.class);
                    String phone = task.getResult().child("phone").getValue(String.class);
                    Log.d("USER_INFO", "Tên: " + name + ", SĐT: " + phone);
                }
            });
        }
//        binding.textView.setText(setting.getLanguages() + "\n" + setting.getEmail() + "\n" + setting.getPassword());

    }
}