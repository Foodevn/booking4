package com.example.booking4.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.booking4.R;
import com.example.booking4.databinding.FragmentLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {


    private FragmentLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        run();
        return binding.getRoot();
    }

    private void run() {
        binding.tvSignup.setOnClickListener(view -> {
            SignupFragment signupFragment = new SignupFragment();
            FragmentTransaction transaction = requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.nav_host_fragment , signupFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        });
        binding.btnLogin.setOnClickListener(view -> {
            loginUser();
        });
    }
    private void loginUser() {
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(getActivity(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                       //load lai trang
                        LoadProfileFragment();

                    } else {
                        Toast.makeText(getActivity(), "Đăng nhập thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void LoadProfileFragment() {
        FragmentTransaction transaction = requireActivity()
                .getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.nav_host_fragment , new ProfileFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}