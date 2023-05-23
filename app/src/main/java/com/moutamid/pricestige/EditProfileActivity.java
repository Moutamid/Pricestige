package com.moutamid.pricestige;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.util.DynamiteApi;
import com.moutamid.pricestige.constant.Constants;
import com.moutamid.pricestige.databinding.ActivityEditProfileBinding;
import com.moutamid.pricestige.model.UserModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {
    ActivityEditProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Constants.initDialog(binding.getRoot().getContext());
        Constants.showDialog();

        binding.back.setOnClickListener(v -> {
            startActivity(new Intent(EditProfileActivity.this, DashboardActivity.class));
            finish();
        });

        Constants.databaseReference().child(Constants.USER).child(Constants.auth().getCurrentUser().getUid())
                .get().addOnSuccessListener(dataSnapshot -> {
                    if (dataSnapshot.exists()){
                        UserModel model = dataSnapshot.getValue(UserModel.class);
                        binding.username.setText(model.getUsername());
                        binding.name.setText(model.getName());
                        binding.phone.setText(model.getPhoneNumber());
                    }
                    Constants.dismissDialog();
                }).addOnFailureListener(e -> {
                    Constants.dismissDialog();
                    Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });

        binding.update.setOnClickListener(v -> {
            Constants.showDialog();
            Map<String, Object> map = new HashMap<>();
            map.put("name", binding.name.getText().toString());
            map.put("phoneNumber", binding.phone.getText().toString());

            Constants.databaseReference().child(Constants.USER).child(Constants.auth().getCurrentUser().getUid())
                    .updateChildren(map).addOnSuccessListener(unused -> {
                        Constants.dismissDialog();
                        Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(e -> {
                        Constants.dismissDialog();
                        Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    });

        });


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(EditProfileActivity.this, DashboardActivity.class));
        finish();
    }
}