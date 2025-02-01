package com.ph.recipeme.social.MainScreen;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.firebase.firestore.FirebaseFirestore;

import com.ph.recipeme.R;
import com.ph.recipeme.social.ForgotPasswordFunction.TermsAndAgreement;
import com.ph.recipeme.social.SignInScreen.mainSignIn;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    // Declare Firebase instances
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String userFullname, userUsername, userEmailaddress, userContactnumber, userPassword, userConfirmedPassword;
    TextView signinprocess;
    Button signUp;
    EditText fullName, userName, emailAddress, contactNo, password, confirmedPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        signinprocess = findViewById(R.id.signInProcess);
        signUp = findViewById(R.id.signUpMe);

        fullName = findViewById(R.id.userName);
        userName = findViewById(R.id.userUsername);
        emailAddress = findViewById(R.id.userGmail);
        contactNo = findViewById(R.id.userPhoneNumber);
        password = findViewById(R.id.userPassword);
        confirmedPassword = findViewById(R.id.userConfirmPassword);

        // Enable the sign-up button when the layout loads
        signUp.setEnabled(true);

        // Set up a listener on each EditText to validate fields when text is changed
        fullName.addTextChangedListener(new TextWatcherAdapter(this::validateFields));
        userName.addTextChangedListener(new TextWatcherAdapter(this::validateFields));
        emailAddress.addTextChangedListener(new TextWatcherAdapter(this::validateFields));
        contactNo.addTextChangedListener(new TextWatcherAdapter(this::validateFields));
        password.addTextChangedListener(new TextWatcherAdapter(this::validateFields));
        confirmedPassword.addTextChangedListener(new TextWatcherAdapter(this::validateFields));

        // Going back to sign-in page
        signinprocess.setOnClickListener(view -> {
            Intent intent = new Intent(SignUp.this, mainSignIn.class);
            startActivity(intent);
            finish();
        });

        signUp.setOnClickListener(view -> {
            if (isValid()) {
                // Attempt to create a user
                mAuth.createUserWithEmailAndPassword(userEmailaddress, userPassword)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Get the current user
                                FirebaseUser user = mAuth.getCurrentUser();

                                // Create a user map for Firestore
                                Map<String, Object> userData = new HashMap<>();
                                userData.put("fullName", userFullname);
                                userData.put("userName", userUsername);
                                userData.put("email", userEmailaddress);
                                userData.put("contactNumber", userContactnumber);
                                userData.put("favoriteFoods", "FOOD PROFILE");

                                // Save user data in Firestore
                                db.collection("users").document(user.getUid())
                                        .set(userData)
                                        .addOnSuccessListener(aVoid -> {
                                            showToast("Sign-up successful! Welcome.");
                                            // Navigate to the next screen
                                            Intent intent = new Intent(SignUp.this, TermsAndAgreement.class);
                                            startActivity(intent);
                                            finish();
                                        })
                                        .addOnFailureListener(e -> {
                                            showToast("Failed to store user data: " + e.getMessage());
                                        });
                            } else {
                                showToast("Authentication failed: " + task.getException().getMessage());
                            }
                        });
            }
        });
    }

    // Method to validate if all fields are filled in
    private void validateFields() {
        userFullname = fullName.getText().toString().trim();
        userUsername = userName.getText().toString().trim();
        userEmailaddress = emailAddress.getText().toString().trim();
        userContactnumber = contactNo.getText().toString().trim();
        userPassword = password.getText().toString().trim();
        userConfirmedPassword = confirmedPassword.getText().toString().trim();

        // Sign-up button enabled only after the text fields are validated
        signUp.setEnabled(!TextUtils.isEmpty(userFullname) &&
                !TextUtils.isEmpty(userUsername) &&
                !TextUtils.isEmpty(userEmailaddress) &&
                !TextUtils.isEmpty(userContactnumber) &&
                !TextUtils.isEmpty(userPassword) &&
                !TextUtils.isEmpty(userConfirmedPassword));
    }

    // Method to check if the form is valid (password match and no empty fields)
    private boolean isValid() {
        if (TextUtils.isEmpty(userFullname) || TextUtils.isEmpty(userUsername) ||TextUtils.isEmpty(userEmailaddress) ||
                TextUtils.isEmpty(userContactnumber) || TextUtils.isEmpty(userPassword) ||
                TextUtils.isEmpty(userConfirmedPassword)) {
            showToast("Please fill out all fields.");
            return false;
        }

        int charCount = userFullname.length();
        if (charCount < 8) {
            showToast("Full name should more than 8 characters");
            return false;
        }

        int charUsernameCount = userUsername.length();
        if (charUsernameCount > 6) {
            showToast("User name should less than 6 characters");
            return false;
        }

        if (!userEmailaddress.contains("@")) {
            showToast("Not a valid Email Address");
            return false;
        }

        if (!userContactnumber.startsWith("+")) {
            showToast("Please specify your Country Code");
            return false;
        }


        if (!isValidPassword(userPassword)) {
            showToast("Your password doesn't meet the security requirements.");
            return false;
        }

        if (!userPassword.equals(userConfirmedPassword)) {
            showToast("Passwords do not match.");
            return false;
        }

        // If everything is valid, return true
        return true;
    }

    public boolean isValidPassword(String password) {
        // Regular expression pattern
        String regex = "^(?=.*[0-9].*[0-9])(?=.*[!@#$%^&*(),.?\":{}|<>]).{5,}$";

        return password.matches(regex);
    }

    // Utility method to show a Toast message
    private void showToast(String message) {
        Toast.makeText(SignUp.this, message, Toast.LENGTH_SHORT).show();
    }

    // Utility class for simple TextWatcher to avoid code duplication
    private static class TextWatcherAdapter implements TextWatcher {
        private final Runnable callback;

        public TextWatcherAdapter(Runnable callback) {
            this.callback = callback;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            callback.run();
        }
    }


}
