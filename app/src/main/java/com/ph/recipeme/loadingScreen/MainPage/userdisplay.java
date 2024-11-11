package com.ph.recipeme.loadingScreen.MainPage;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.ph.recipeme.R;
import com.ph.recipeme.loadingScreen.navigationFragments.CommunityFragment;
import com.ph.recipeme.loadingScreen.navigationFragments.FavoritesFragment;
import com.ph.recipeme.loadingScreen.navigationFragments.ForumFragment;
import com.ph.recipeme.loadingScreen.navigationFragments.HomeFragment;

public class userdisplay extends AppCompatActivity {

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
