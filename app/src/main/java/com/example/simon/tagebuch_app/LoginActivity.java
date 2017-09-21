package com.example.simon.tagebuch_app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.simon.tagebuch_app.databases.RegistryDB;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 * datenbank muss nach login noch geschlossen werden da ondestroy in registreyactivty noch gebracuht wird!!!!
 */
public class LoginActivity extends AppCompatActivity{

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Button skipToRegistryActivity;
    private Button skipToReiseMain;
    private Button signInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        createSkipButtons();
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

        signInButton = (Button) findViewById(R.id.email_sign_in_button);
        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                    RegistryDB db = new RegistryDB(LoginActivity.this);
                            db.open();
                            db.checkLogInInfo(mPasswordView.getText().toString());
                    attemptLogin();
            }
        });

    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (isPasswordValid(mPasswordView.getText().toString())){
            Intent intent = new Intent(LoginActivity.this, ReiseMainActivity.class);
            startActivity(intent);
        }

    }

    private boolean isPasswordValid(String password) {
        Boolean validPassword = false;
        if(validPassword){
            return true;
        }
        return false;
    }



    private void createSkipButtons() {
        skipToRegistryActivity = (Button) findViewById(R.id.skipToRegistryActivity);
        skipToRegistryActivity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistryActivity.class);
                startActivity(intent);
            }
        });


        skipToReiseMain = (Button) findViewById(R.id.skipToReiseMain);
        skipToReiseMain.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent1 = new Intent(LoginActivity.this, ReiseMainActivity.class);
                startActivity(intent1);
            }
        });
    }
}

