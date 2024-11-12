package com.ph.recipeme.loadingScreen.MainScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ph.recipeme.R;
import com.ph.recipeme.loadingScreen.SignInScreen.mainSignIn;

public class SignUp extends AppCompatActivity {
    private String userFullname, userEmailaddress, userContactnumber, userPassword, userConfirmedPassword;
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

        //Getting Value from this fields
        userFullname = String.valueOf(fullName.getText());
        userEmailaddress = String.valueOf(emailAddress.getText());
        userContactnumber = String.valueOf(contactNo.getText());
        userPassword = String.valueOf(password.getText());
        userConfirmedPassword = String.valueOf(confirmedPassword.getText());

        //checking empty fields * this is not VALID * all fields must be filled correctly
        /*   TODO
        if (userFullname.isBlank()) {

        } else if (userFullname.isEmpty()) {

        }
        if (userEmailaddress.isEmpty() || userEmailaddress.isBlank()) {

        }*/


        //Going back to sign-in page
        signinprocess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, mainSignIn.class);
                startActivity(intent);
                finish();
            }
        });

    }
}