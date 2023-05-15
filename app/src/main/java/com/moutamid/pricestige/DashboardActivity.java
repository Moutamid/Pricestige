package com.moutamid.pricestige;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.moutamid.pricestige.constant.Constants;
import com.moutamid.pricestige.databinding.ActivityDashboardBinding;
import com.moutamid.pricestige.fragment.BookmarkFragment;
import com.moutamid.pricestige.fragment.HomeFragment;
import com.moutamid.pricestige.fragment.UserFragment;

public class DashboardActivity extends AppCompatActivity {
    ActivityDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Constants.checkApp(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new HomeFragment()).commit();
        binding.bottomNav.setSelectedItemId(R.id.nav_home);

        binding.bottomNav.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, new HomeFragment()).commit();
            } else if (itemId == R.id.nav_bookmark) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, new BookmarkFragment()).commit();
            } else if (itemId == R.id.nav_user) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, new UserFragment()).commit();
            }
            return true;
        });

    }
}