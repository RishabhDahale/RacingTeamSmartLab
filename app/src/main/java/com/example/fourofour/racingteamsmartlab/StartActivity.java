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
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private Button labStatus;

    public static boolean inLab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
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


}

