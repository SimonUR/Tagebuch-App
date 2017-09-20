package com.example.simon.tagebuch_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.simon.tagebuch_app.databases.RegistryDB;

public class RegistryActivity extends AppCompatActivity {

    private EditText inputName, inputEmail, inputPasswort, inputPasswortWieder;
    private Button abschicken;
    private RegistryDB myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);

        myDb = new RegistryDB(this);
        inputName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPasswort = (EditText) findViewById(R.id.passwort);
        inputPasswortWieder = (EditText) findViewById(R.id.passwort_bestaetigen);


        abschicken = (Button) findViewById(R.id.abschicken);

        abschicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readDaten();
            }

        });

    }

    private  String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private void readDaten() {
        String name = inputName.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String passwort = inputPasswort.getText().toString().trim();
        String passwortWieder = inputPasswortWieder.getText().toString().trim();
        Boolean result = myDb.insertData(name,email,passwort);
        if ( result == true &&
                name.trim().length() != 0 &&
                email.trim().length() != 0 &&
                passwort.trim().length() != 0 &&
                passwortWieder.trim().length() != 0 &&
                passwort.equals(passwortWieder) &&
                passwort.trim().length() >= 6 &&
                email.matches(emailPattern))
        {
            Toast.makeText(this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
            clearText();

        }else {
            Toast.makeText(this, "Data Inserted Failed! ",Toast.LENGTH_SHORT).show();
        }
    }

    public void clearText()
    {
        inputName.setText("");
        inputEmail.setText("");
        inputPasswort.setText("");
        inputPasswortWieder.setText("");
    }
    }


