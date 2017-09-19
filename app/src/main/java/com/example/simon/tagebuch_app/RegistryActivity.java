package com.example.simon.tagebuch_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog.Builder;

public class RegistryActivity extends AppCompatActivity {

    private EditText inputName, inputEmail, inputPasswort, inputPasswortWieder;
    private Button abschicken;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);

        inputName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPasswort = (EditText) findViewById(R.id.passwort);
        inputPasswortWieder = (EditText) findViewById(R.id.password_bestätigen);

        db = openOrCreateDatabase("Benutzer", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS benutzer(name VARCHAR,email VARCHAR,passwort VARCHAR, passwort VARCHAR);");


        abschicken = (Button) findViewById(R.id.abschicken);

        abschicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == abschicken) {
                    //checking for blank fields
                    if (inputName.getText().toString().trim().length() == 0 ||
                            inputEmail.getText().toString().trim().length() == 0 ||
                            inputPasswort.getText().toString().trim().length() == 0 ||
                            inputPasswortWieder.getText().toString().trim().length() == 0) {
                        alert("Error", "Please enter all fields");
                        return;
                    }
                    // inserting values to DB.
                    db.execSQL("INSERT INTO benutzer VALUES('" + inputName.getText() + "','" + inputEmail.getText() +
                            "','" + inputPasswort.getText() + "','" + inputPasswortWieder.getText() + "'");
                    alert("Success", "Record added");
                    clearText();
                }

            }
        });
    }


    public void alert(String title,String message)
    {
        Builder builder=new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText()
    {
        inputName.setText("");
        inputEmail.setText("");
        inputPasswort.setText("");
        inputPasswortWieder.setText("");
    }
    }

  /*  private void readDaten() {
        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String passwort = inputPasswort.getText().toString();
        String passwortWieder = inputPasswortWieder.getText().toString();

    } */

