package com.ph.recipeme.social;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ph.recipeme.R;
import com.ph.recipeme.social.MainPage.userdisplay;
import com.ph.recipeme.social.MainScreen.SignIn;
import com.ph.recipeme.social.security.EmulatorCheckUtil;

public class MainActivity extends AppCompatActivity {
    private static final long SPLASH_DELAY = 2000; // 2 second delay

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Emulator check
        if (EmulatorCheckUtil.isLikelyEmulator(this)) {
            Toast.makeText(this, "This app cannot run on emulators", Toast.LENGTH_LONG).show();
            finish(); // Close the app if it’s running on an emulator
        } else {
            startUp();
        }
    }

    protected void startUp() {
        new Handler().postDelayed(() -> {
            // Check if the user is logged in
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = auth.getCurrentUser();

            Intent intent;
            if (currentUser != null) {
                // User is logged in, redirect to home screen
                intent = new Intent(MainActivity.this, userdisplay.class);
            } else {
                // User is not logged in, redirect to sign-in screen
                intent = new Intent(MainActivity.this, SignIn.class);
            }

            startActivity(intent);
            finish(); // Close the splash activity so it won't be shown when pressing back
        }, SPLASH_DELAY);
    }
}
