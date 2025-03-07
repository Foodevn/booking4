package com.example.booking4.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.booking4.Models.User;
import com.example.booking4.R;
import com.example.booking4.databinding.FragmentSignupBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignupFragment extends Fragment {


    private FragmentSignupBinding binding;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignupBinding.inflate(inflater, container, false);
        mAuth = FirebaseAuth.getInstance();
        run();
        return binding.getRoot();
    }

    private void run() {
        binding.tvBackToLogin.setOnClickListener(view -> {
            LoginFragment loginFragment = new LoginFragment();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, loginFragment)
                    .addToBackStack(null)
                    .commit();

        });
        binding.btnSignup.setOnClickListener(view -> {
            registerUser();
        });
    }

    private void registerUser() {
        String email = binding.etSignupEmail.getText().toString().trim();
        String password = binding.etSignupPassword.getText().toString().trim();
        String name = binding.etSignupName.getText().toString().trim();
        String phone = binding.etSignupPhone.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        saveUserInfo(user.getUid(), name, phone, email);
                    } else {
                        Toast.makeText(getContext(), "Đăng ký thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveUserInfo(String userId, String name, String phone, String email) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");

        User user = new User();

        user.setId(userId);
        user.setName(name);
        user.setPhone(phone);
        user.setEmail(email);
        user.setAvatar("null");

        usersRef.child(userId).setValue(user)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    //load lại activity Main
                    Intent intent = requireActivity().getIntent();
                    requireActivity().finish();
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Lỗi khi lưu thông tin: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}