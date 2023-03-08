package com.example.itraveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity {

    private EditText editUsername;
    private EditText editPassword;
    private EditText Password_confirm;
    private Button signupBtn;

    private final String CREDENTIAL_SHARED_PREF = "share_pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editUsername = findViewById(R.id.ed_User);
        editPassword = findViewById(R.id.ed_Pass);
        Password_confirm = findViewById(R.id.confirm_Pass);
        signupBtn = findViewById(R.id.button_new_account);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strUsername = editUsername.getText().toString();
                String strPassword = editPassword.getText().toString();
                String strConfirm = Password_confirm.getText().toString();

                if (strPassword != null && strConfirm != null && strPassword.equalsIgnoreCase(strConfirm)) {
                    SharedPreferences credential = getSharedPreferences(CREDENTIAL_SHARED_PREF, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = credential.edit();
                    editor.putString("Password", strPassword);
                    editor.putString("Username", strUsername);
                    editor.commit();

                    Register.this.finish();
                }
            }
        });

    }
}