package com.ph.recipeme.social.ForgotPasswordFunction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ph.recipeme.R;
import com.ph.recipeme.social.MainPage.userdisplay;


public class TermsAndAgreement extends AppCompatActivity {
    Button agreeButton;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_termandagreement);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        agreeButton = findViewById(R.id.agreeButton);
        checkBox = findViewById(R.id.checkbox_agree);

        // Set an OnCheckedChangeListener to update the button state when the checkbox is toggled
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                agreeButton.setEnabled(true);
            } else {
                agreeButton.setEnabled(false);
            }
        });

        agreeButton.setOnClickListener(view -> {
            Intent intent = new Intent(TermsAndAgreement.this, userdisplay.class);
            startActivity(intent);
            finish();
        });

    }
}