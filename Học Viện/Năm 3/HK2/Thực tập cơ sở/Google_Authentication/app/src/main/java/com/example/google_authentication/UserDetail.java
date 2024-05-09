package com.example.google_authentication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserDetail extends AppCompatActivity {

    TextView fname, lname, email, usrname, passwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Hook Text View Objects:
        fname = findViewById(R.id.firstnameUD);
        lname = findViewById(R.id.lastnameUD);
        email = findViewById(R.id.emailUD);
        usrname = findViewById(R.id.usernameUD);
        passwd = findViewById(R.id.passwordUD);

        // Get intent extra values
        String firstname = getIntent().getStringExtra("firstname");
        String lastname = getIntent().getStringExtra("lastname");
        String email2 = getIntent().getStringExtra("email");
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        int image = getIntent().getIntExtra("image", R.drawable.cat1);

        //Set text view
        fname.setText(firstname);
        lname.setText(lastname);
        email.setText(email2);
        usrname.setText(username);
        passwd.setText(password);


    }
}