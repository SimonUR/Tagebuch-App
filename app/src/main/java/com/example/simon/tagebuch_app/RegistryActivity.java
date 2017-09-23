package com.example.simon.tagebuch_app;

import android.content.Intent;
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
    private final String errorMessage = "Daten wurden nicht gespeichert!";
    private final String successMessage = "Daten wurden gespeichert.";
    public static RegistryDB myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);

        initDatabase();

        inputName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPasswort = (EditText) findViewById(R.id.passwort);
        inputPasswortWieder = (EditText) findViewById(R.id.passwort_bestätigen);

        abschicken = (Button) findViewById(R.id.abschicken);

        abschicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(readDaten()){
                    Intent intent = new Intent(RegistryActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onDestroy(){
        myDb.close();
        super.onDestroy();
    }
    private void initDatabase() {
        myDb = new RegistryDB(this);
        myDb.open();
    }

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private boolean readDaten() {
        String name = inputName.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String passwort = inputPasswort.getText().toString().trim();
        String passwortWieder = inputPasswortWieder.getText().toString().trim();
        if (name.trim().length() != 0 &&
                email.trim().length() != 0 &&
                passwort.trim().length() != 0 &&
                passwortWieder.trim().length() != 0 &&
                passwort.equals(passwortWieder) &&
                passwort.trim().length() >= 6 &&
                email.matches(emailPattern))
        {
            myDb.insertUserInDb(name,email,passwort);
            Toast.makeText(this, successMessage, Toast.LENGTH_SHORT).show();
            clearText();
            return true;

        }else {
            Toast.makeText(this, errorMessage,Toast.LENGTH_SHORT).show();
            return false;
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


