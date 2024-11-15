package com.ph.recipeme.social.MainScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ph.recipeme.R;
import com.ph.recipeme.social.SignInScreen.mainSignIn;

public class SignIn extends AppCompatActivity {

    Button signIn;
    TextView signUpProcess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signIn), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        signUpProcess = findViewById(R.id.signUpProcess);
        signUpProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == signUpProcess) {
                    Intent intent = new Intent(SignIn.this, SignUp.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


        signIn = findViewById(R.id.signInButton);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == signIn) {
                    Intent intent = new Intent(SignIn.this, mainSignIn.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}