package com.example.simon.tagebuch_app;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.simon.tagebuch_app.databases.RegistryDB;
import com.example.simon.tagebuch_app.image.PictureActivity;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{

    // db class to check if user exist by checking email and password with db.
    private RegistryDB db;
    private final String userError = "Benutzer existiert nicht!";
    private final String logInMessage = "LogIn erfolgreich!";
    private final String intentIdKey = "ID";


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Button signInButton;
    private Button registryButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        setupUserUI();
        setupSignInButton();
        setupRegistryButton();

        Button button = (Button) findViewById(R.id.skipToImage);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, PictureActivity.class);
                startActivity(intent);
            }
        });


    }

    private void setupRegistryButton() {
        registryButton = (Button) findViewById(R.id.skipToRegistryActivity);
        registryButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupUserUI() {
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    return true;
                }
                return false;
            }
        });
    }

    private void setupSignInButton() {
        signInButton = (Button) findViewById(R.id.email_sign_in_button);
        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new RegistryDB(LoginActivity.this);
                db.open();
                if(db.checkLogInInfo(mEmailView.getText().toString(), mPasswordView.getText().toString())){
                    Intent intent = new Intent(LoginActivity.this, ReiseMainActivity.class);
                    int userID = db.getUserID(mEmailView.getText().toString(), mPasswordView.getText().toString());
                    intent.putExtra(intentIdKey, userID);
                    Toast.makeText(LoginActivity.this, logInMessage, Toast.LENGTH_SHORT).show();
                    mPasswordView.setText("");
                    mEmailView.setText("");
                    db.close();
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this, userError, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

}

