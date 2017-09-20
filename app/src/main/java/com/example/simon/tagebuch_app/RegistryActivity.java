package com.example.simon.tagebuch_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog.Builder;
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
        inputPasswortWieder = (EditText) findViewById(R.id.password_bestätigen);


        abschicken = (Button) findViewById(R.id.abschicken);

        abschicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readDaten();
            }

        });

    }
    private void readDaten() {
        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String passwort = inputPasswort.getText().toString();
        String passwortWieder = inputPasswortWieder.getText().toString();
        Boolean result = myDb.insertData(name,email,passwort);
        if (result == true && passwort == passwortWieder )
        {
            Toast.makeText(this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
            clearText();
        }else {
            Toast.makeText(this, "Data Inserted Failed!",Toast.LENGTH_SHORT).show();
        }
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


