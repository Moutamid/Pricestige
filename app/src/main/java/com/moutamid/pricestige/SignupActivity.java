package com.moutamid.pricestige;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import com.moutamid.pricestige.constant.Constants;
import com.moutamid.pricestige.databinding.ActivitySignupBinding;
import com.moutamid.pricestige.model.UserModel;

public class SignupActivity extends AppCompatActivity {
    ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Constants.initDialog(this);

        binding.back.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, MainActivity.class));
            finish();
        });

        binding.login.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            finish();
        });

        binding.signup.setOnClickListener(v -> {
            if (valid()) {
                Constants.showDialog();
                Constants.auth().createUserWithEmailAndPassword(
                        binding.etEmailLogin.getEditText().getText().toString().trim(),
                        binding.etPasswordLogin.getEditText().getText().toString()
                ).addOnSuccessListener(authResult -> {
                    UserModel userModel = new UserModel(
                            Constants.auth().getCurrentUser().getUid(),
                            binding.etName.getEditText().getText().toString(),
                            binding.etUsername.getEditText().getText().toString(),
                            binding.etEmailLogin.getEditText().getText().toString(),
                            binding.etPasswordLogin.getEditText().getText().toString(),
                            binding.etPhone.getEditText().getText().toString()
                    );
                    Constants.databaseReference().child(Constants.USER).child(Constants.auth().getCurrentUser().getUid())
                            .setValue(userModel).addOnSuccessListener(unused -> {
                                Constants.dismissDialog();
                                startActivity(new Intent(SignupActivity.this, DashboardActivity.class));
                                finish();
                            }).addOnFailureListener(e -> {
                                Constants.dismissDialog();
                                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                }).addOnFailureListener(e -> {
                    Constants.dismissDialog();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });

    }

    private boolean valid() {

        if (binding.etUsername.getEditText().getText().toString().isEmpty()) {
            binding.etUsername.getEditText().setError("Username should not be empty");
            return false;
        }
        if (binding.etName.getEditText().getText().toString().isEmpty()) {
            binding.etName.getEditText().setError("Name should not be empty");
            return false;
        }
        if (binding.etPhone.getEditText().getText().toString().isEmpty()) {
            binding.etPhone.getEditText().setError("Phone Number should not be empty");
            return false;
        }
        if (binding.etEmailLogin.getEditText().getText().toString().isEmpty()) {
            binding.etEmailLogin.getEditText().setError("Email should not be empty");
            return false;
        }
        if (binding.etPasswordLogin.getEditText().getText().toString().isEmpty()) {
            binding.etPasswordLogin.getEditText().setError("Password should not be empty");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmailLogin.getEditText().getText().toString()).matches()) {
            binding.etEmailLogin.getEditText().setError("Email is not valid");
            return false;
        }

        return true;
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(SignupActivity.this, MainActivity.class));
        finish();
    }
}