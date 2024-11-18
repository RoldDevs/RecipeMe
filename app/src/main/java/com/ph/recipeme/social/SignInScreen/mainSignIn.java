package com.ph.recipeme.social.SignInScreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.ph.recipeme.social.MainScreen.SignUp;
import com.ph.recipeme.social.navigationFragments.HomeFragment;

public class mainSignIn extends AppCompatActivity {

    private static final String TAG = "SignInActivity";
    private FirebaseAuth mAuth;

    private EditText emailField, passwordField;

    private Button signInButton;
    TextView signUpGoBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        emailField = findViewById(R.id.userGmail);
        passwordField = findViewById(R.id.userPassword);
        signInButton = findViewById(R.id.loginProcess);

        signUpGoBack = findViewById(R.id.signUpProcess);

        signUpGoBack.setOnClickListener(view -> {
            Intent intent = new Intent(mainSignIn.this, SignUp.class);
            startActivity(intent);
            finish();
        });

        // Handle Sign-In
        signInButton.setOnClickListener(v -> signInUser());
    }
    private void signInUser() {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(mainSignIn.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, navigate to Main Screen
                        FirebaseUser user = mAuth.getCurrentUser();
                        Log.d(TAG, "signInWithEmail:success");
                        Intent intent = new Intent(mainSignIn.this, userdisplay.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(mainSignIn.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}