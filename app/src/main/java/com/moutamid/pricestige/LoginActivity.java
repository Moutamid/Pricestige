package com.moutamid.pricestige;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import com.moutamid.pricestige.constant.Constants;
import com.moutamid.pricestige.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Constants.initDialog(this);

        binding.back.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        });

        binding.signup.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            finish();
        });

        binding.login.setOnClickListener(v -> {
            if (valid()) {
                Constants.showDialog();
                Constants.auth().signInWithEmailAndPassword(
                        binding.etEmailLogin.getEditText().getText().toString().trim(),
                        binding.etPasswordLogin.getEditText().getText().toString()
                ).addOnSuccessListener(authResult -> {
                    Constants.dismissDialog();
                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                    finish();
                }).addOnFailureListener(e -> {
                    Constants.dismissDialog();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });

    }

    private boolean valid() {

        if (binding.etEmailLogin.getEditText().getText().toString().isEmpty()){
            binding.etEmailLogin.getEditText().setError("Email should not be empty");
            return false;
        } if (binding.etPasswordLogin.getEditText().getText().toString().isEmpty()){
            binding.etPasswordLogin.getEditText().setError("Password should not be empty");
            return false;
        } if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmailLogin.getEditText().getText().toString()).matches()){
            binding.etEmailLogin.getEditText().setError("Email is not valid");
            return false;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

}