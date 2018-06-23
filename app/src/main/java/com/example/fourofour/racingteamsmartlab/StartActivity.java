package com.example.fourofour.racingteamsmartlab;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.services.sheets.v4.model.Color;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class StartActivity extends AppCompatActivity {
    private String mUsername;

    private static final int RC_SIGN_IN = 123;

    private Button labStatus;
    public static boolean isLoggedIn;

    public static boolean inLab;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mFirebaseAuth = FirebaseAuth.getInstance();

        final List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.GoogleBuilder().build());

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user!= null) {
                    onSignedInInitialize(user.getDisplayName());
                    isLoggedIn = true;
                } else {
                    onSignedOutCleanUp();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder().setIsSmartLockEnabled(false)
                                    .setAvailableProviders(providers)
                                    .build(),
                            RC_SIGN_IN);
                    isLoggedIn = false;
                }
            }
        };

        Button signOut = (Button)findViewById(R.id.signOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLoggedIn = false;
                AuthUI.getInstance()
                        .signOut(getApplicationContext())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                // ...
                            }
                        });
            }
        });



        labStatus= findViewById(R.id.labButton);
        inLab=false;
        TextView labstatus= findViewById(R.id.self_occupancy_status);
        labstatus.setText("Status- not in lab");
        labstatus.setTextColor(getResources().getColor(R.color.Red));
        labStatus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                changeText(inLab);
                inLab= !inLab;
            }
        });
    }

    private void changeText(Boolean inlab) {
        TextView changetext= findViewById(R.id.self_occupancy_status);
        if(!inlab) {
            changetext.setText("Status- in lab");
            changetext.setTextColor(getResources().getColor(R.color.Green));
        }
        else {
            changetext.setText("Status- not in lab");
            changetext.setTextColor(getResources().getColor(R.color.Red));
        }
    }


    public void inventoryPage(View view) {
        Intent i = new Intent(this, InventoryActivity.class);
        startActivity(i);
    }





    public void notificationPage (View view) {
        Intent i = new Intent(this, NotificationActivity.class);
        startActivity(i);
    }



    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    private void onSignedInInitialize(String username) {
        mUsername = username;
    }

    private void onSignedOutCleanUp () {
        mUsername = "ANONYMOUS";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN) {
            if(resultCode == RESULT_OK) {
                Toast.makeText(this, "Signed In", Toast.LENGTH_LONG).show();
            }
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Sign In Cancelled", Toast.LENGTH_LONG).show();
                finish();
            }
        }

    }



}

