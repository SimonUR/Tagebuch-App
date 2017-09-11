package com.example.simon.tagebuch_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistryActivity extends AppCompatActivity {

    private EditText inputName, inputEmail, inputPasswort, inputPasswortWieder;
    private Button abschicken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);

        inputName = (EditText)findViewById(R.id.name);
        inputEmail = (EditText)findViewById(R.id.email);
        inputPasswort = (EditText)findViewById(R.id.passwort);
        inputPasswortWieder = (EditText)findViewById(R.id.password_bestätigen);

        abschicken = (Button)findViewById(R.id.abschicken);

        abschicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
