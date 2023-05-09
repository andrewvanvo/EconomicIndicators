package com.example.economics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public static final int RC_SIGN_IN = 1;
    // creating an auth listener for our Firebase auth
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    // auth options
    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.AnonymousBuilder().build()
            //new AuthUI.IdpConfig.GoogleBuilder().build()
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // if the user is already authenticated then we will
                    // redirect
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // this method is called when user is not authenticated previously.
                    startActivityForResult(
                            // get auth ui instance
                            AuthUI.getInstance()
                                    // create our sign in intent
                                    .createSignInIntentBuilder()
                                    // diff devices check
                                    .setIsSmartLockEnabled(false)
                                    // login options set above
                                    .setAvailableProviders(providers)
                                    .setLogo(R.drawable.ic_baseline_insert_chart_outlined_24)

                                    //build the login screen
                                    .build(), RC_SIGN_IN
                    );
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        // we are calling listener method on app resume.
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //remove auth listener method on stop.
        mAuth.removeAuthStateListener(mAuthStateListener);
    }

}
