package com.ph.recipeme.social.userProfile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ph.recipeme.R;
import com.ph.recipeme.social.SignInScreen.mainSignIn;

public class myProfile extends AppCompatActivity {

    private TextView userNameRetrieve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_profile);

        // Apply window insets for system UI (status bar, navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userNameRetrieve = findViewById(R.id.userNameRetrieve);

        // Get current user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid(); // Get the current user's UID
            fetchUserName(userId); // Fetch username from Firestore
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }

        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut(); // Sign out from Firebase
            Toast.makeText(myProfile.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(myProfile.this, mainSignIn.class); // Redirect to main or login screen
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); // Clear activity stack
            startActivity(intent);
            finish();
        });
    }

    @SuppressLint("SetTextI18n")
    private void fetchUserName(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Replace "users" with your Firestore collection name
        db.collection("users").document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            // Retrieve the "username" field from Firestore
                            String username = document.getString("fullName");
                            if (username != null) {
                                userNameRetrieve.setText(username); // Set username in TextView
                            } else {
                                userNameRetrieve.setText("NotFound");
                            }
                        } else {
                            //DO NOTHING
                        }
                    } else {
                        //DO NOTHING
                    }
                });
    }
}
