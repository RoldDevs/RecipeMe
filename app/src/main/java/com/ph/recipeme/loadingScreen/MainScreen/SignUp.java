package com.ph.recipeme.loadingScreen.MainScreen;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ph.recipeme.R;

public class SignUp extends AppCompatActivity {

    private

    TextView signinprocess;
    Button signUp;
    EditText fullName, emailAddress, contactNo, password, confirmedPassword;

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

        signinprocess = findViewById(R.id.signInProcess);
        signUp = findViewById(R.id.signUpMe);

        fullName = findViewById(R.id.userName);
        emailAddress = findViewById(R.id.userGmail);
        contactNo = findViewById(R.id.userPhoneNumber);
        password = findViewById(R.id.userPassword);
        confirmedPassword = findViewById(R.id.userConfirmPassword);


        

    }
}