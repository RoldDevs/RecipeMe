package com.ph.recipeme.social.userProfile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ph.recipeme.R;
import com.ph.recipeme.social.MainPage.userdisplay;
import com.ph.recipeme.social.SignInScreen.mainSignIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class myProfile extends AppCompatActivity {
    private TextView favoriteFoodTextInter;
    private ChipGroup chipGroupFood;
    private FirebaseFirestore db;
    private String userId;
    private DrawerLayout drawerLayout;
    private TextView userNameRetrieve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_profile);

        // Apply window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userNameRetrieve = findViewById(R.id.profileUser);
        drawerLayout = findViewById(R.id.main);
        ImageButton btnMenu = findViewById(R.id.btnMenu);
        favoriteFoodTextInter = findViewById(R.id.favoriteFoodTextInter);
        chipGroupFood = findViewById(R.id.chipGroupFood);
        db = FirebaseFirestore.getInstance();
        userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            fetchUserData(userId);
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }

        btnMenu.setOnClickListener(v -> {
            if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.openDrawer(GravityCompat.START);
            } else {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        MenuItem navHome = navigationView.getMenu().findItem(R.id.nav_home);
        MenuItem navLogout = navigationView.getMenu().findItem(R.id.nav_logout);

        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == navHome.getItemId()) {
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(myProfile.this, userdisplay.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            }

            if (item.getItemId() == navLogout.getItemId()) {
                drawerLayout.closeDrawer(GravityCompat.START);
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(myProfile.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(myProfile.this, mainSignIn.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            }
            return false;
        });

        setupChipClickListeners();
    }

    private void fetchUserData(String userId) {
        DocumentReference userRef = db.collection("users").document(userId);
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null && document.exists()) {
                    String username = document.getString("userName");
                    if (username != null) {
                        userNameRetrieve.setText(username);
                    }

                    Object favoriteFoodsObj = document.get("favoriteFoods");
                    List<String> favoriteFoods = new ArrayList<>();

                    if (favoriteFoodsObj instanceof List) {
                        // Correct type, use it directly
                        favoriteFoods = (List<String>) favoriteFoodsObj;
                    } else if (favoriteFoodsObj instanceof String) {
                        // Convert string to list (assuming comma-separated values)
                        String favoriteFoodsString = (String) favoriteFoodsObj;
                        favoriteFoods = new ArrayList<>(List.of(favoriteFoodsString.split(", ")));

                        // Fix and update Firestore data
                        fixFavoriteFoodsFormat(userRef, favoriteFoods);
                    }

                    restoreSelectedChips(favoriteFoods);
                }
            }
        });
    }

    private void fixFavoriteFoodsFormat(DocumentReference userRef, List<String> favoriteFoods) {
        Map<String, Object> updatedData = new HashMap<>();
        updatedData.put("favoriteFoods", favoriteFoods);

        userRef.update(updatedData).addOnSuccessListener(aVoid ->
                Toast.makeText(this, "Fixed favorite foods format", Toast.LENGTH_SHORT).show()
        );
    }

    private void restoreSelectedChips(List<String> favoriteFoods) {
        for (int i = 0; i < chipGroupFood.getChildCount(); i++) {
            View child = chipGroupFood.getChildAt(i);
            if (child instanceof Chip) {
                Chip chip = (Chip) child;
                if (favoriteFoods.contains(chip.getText().toString())) {
                    chip.setChecked(true);
                }
            }
        }
    }

    private void setupChipClickListeners() {
        for (int i = 0; i < chipGroupFood.getChildCount(); i++) {
            View child = chipGroupFood.getChildAt(i);
            if (child instanceof Chip) {
                Chip chip = (Chip) child;
                chip.setOnCheckedChangeListener((buttonView, isChecked) -> saveSelectedChips());
            }
        }
    }

    private void saveSelectedChips() {
        List<String> selectedFoods = new ArrayList<>();
        for (int i = 0; i < chipGroupFood.getChildCount(); i++) {
            View child = chipGroupFood.getChildAt(i);
            if (child instanceof Chip) {
                Chip chip = (Chip) child;
                if (chip.isChecked()) {
                    selectedFoods.add(chip.getText().toString());
                }
            }
        }

        Map<String, Object> userData = new HashMap<>();
        userData.put("favoriteFoods", selectedFoods);

        db.collection("users").document(userId)
                .update(userData)
                .addOnSuccessListener(aVoid -> {
                    // Data successfully updated
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to save preferences", Toast.LENGTH_SHORT).show());
    }
}
