package com.moutamid.pricestige.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.moutamid.pricestige.DashboardActivity;
import com.moutamid.pricestige.EditProfileActivity;
import com.moutamid.pricestige.LoginActivity;
import com.moutamid.pricestige.R;
import com.moutamid.pricestige.SplashActivity;
import com.moutamid.pricestige.constant.Constants;
import com.moutamid.pricestige.databinding.FragmentUserBinding;
import com.moutamid.pricestige.model.UserModel;

public class UserFragment extends Fragment {
    FragmentUserBinding binding;
    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserBinding.inflate(getLayoutInflater(), container, false);

        Constants.initDialog(binding.getRoot().getContext());
        Constants.showDialog();

        binding.logout.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext()).setTitle("Log out").setMessage("Are you sure yo want to logout?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dialog.dismiss();
                        Constants.showDialog();
                        Constants.auth().signOut();
                        new Handler().postDelayed(() -> {
                            Constants.dismissDialog();
                            startActivity(new Intent(requireContext(), SplashActivity.class));
                            requireActivity().finish();
                        }, 2000);
                    }).setNegativeButton("No", (dialog, which) -> dialog.dismiss()).show();
        });

        Constants.databaseReference().child(Constants.USER).child(Constants.auth().getCurrentUser().getUid())
                .get().addOnSuccessListener(dataSnapshot -> {
                    if (dataSnapshot.exists()){
                        UserModel model = dataSnapshot.getValue(UserModel.class);
                        binding.email.setText(model.getEmail());
                        binding.username.setText(model.getUsername());
                        binding.name.setText(model.getName());
                        binding.phone.setText(model.getPhoneNumber());
                    }
                    Constants.dismissDialog();
                }).addOnFailureListener(e -> {
                    Constants.dismissDialog();
                    Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                });

        binding.edit.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), EditProfileActivity.class));
            requireActivity().finish();
        });

        return binding.getRoot();
    }
}