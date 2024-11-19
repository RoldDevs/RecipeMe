package com.ph.recipeme.social.MainPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.ph.recipeme.R;
import com.ph.recipeme.social.navigationFragments.CommunityFragment;
import com.ph.recipeme.social.navigationFragments.FavoritesFragment;
import com.ph.recipeme.social.navigationFragments.ForumFragment;
import com.ph.recipeme.social.navigationFragments.HomeFragment;
import com.ph.recipeme.social.userProfile.myProfile;

public class userdisplay extends AppCompatActivity {

    private FloatingActionButton userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_userdisplay);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.maindisplay), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.homepage);

        bottomNavigationView.setOnItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.homefragmentlayout, new HomeFragment()).commit();

        userProfile = findViewById(R.id.floatingButton);
        userProfile.setOnClickListener(view -> {
            Intent intent = new Intent(userdisplay.this, myProfile.class);
            startActivity(intent);
        });
    }
    private final NavigationBarView.OnItemSelectedListener navListener = item -> {
        int itemId = item.getItemId();
        Fragment selectedFragment = null;

        if (itemId == R.id.homepage) {
            selectedFragment = new HomeFragment();
        } else if (itemId == R.id.favorites) {
            selectedFragment = new FavoritesFragment();
        } else if (itemId == R.id.community) {
            selectedFragment = new CommunityFragment();
        } else if (itemId == R.id.forumpage) {
            selectedFragment = new ForumFragment();
        } else {
            selectedFragment = new HomeFragment();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.homefragmentlayout, selectedFragment).commit();
        return true;
    };
}
