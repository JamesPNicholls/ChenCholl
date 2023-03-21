package com.example.itraveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editUsername;
    private EditText editPassword;
    private Button loginBtn;
    private Button signupBtn;

    private final String CREDENTIAL_SHARED_PREF = "share_pref";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUsername = findViewById(R.id.ed_User);
        editPassword = findViewById(R.id.ed_Pass);
        loginBtn = findViewById(R.id.button_login);
        signupBtn = findViewById(R.id.button_new_account);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);

            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences credential = getSharedPreferences(CREDENTIAL_SHARED_PREF, Context.MODE_PRIVATE);
                String strUsername = credential.getString("Username",null);
                String strPassword = credential.getString("Password",null);

                String user_enter = editUsername.getText().toString();
                String password_enter = editPassword.getText().toString();

                if (strUsername != null && user_enter != null && strUsername.equalsIgnoreCase(user_enter)) {
                    if (strPassword != null && password_enter != null && strPassword.equalsIgnoreCase(password_enter)) {
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        // create an Intent with the current and target Activities specified (explicit intent)
                        Intent intent = new Intent(MainActivity.this, Hotels.class);
                        // put the user input data in the intent
                        //intent.putExtra("sent_from_act1", message);

                        // start the intent
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}