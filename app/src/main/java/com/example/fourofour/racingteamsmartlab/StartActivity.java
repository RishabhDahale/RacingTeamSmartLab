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
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class StartActivity extends AppCompatActivity {
    private String mUsername;

    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

    }

    public void inventoryPage(View view) {
        Intent i = new Intent(this, InventoryActivity.class);
        startActivity(i);
    }





    public void notificationPage (View view) {
        Intent i = new Intent(this, NotificationActivity.class);
        startActivity(i);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onDestroy() {
        if(NotificationActivity.loginStatus()) {
            String name = NotificationActivity.getName();
            String notificationText = NotificationActivity.getText();

            if(NotificationActivity.userName() != name) {
                android.support.v4.app.NotificationCompat.Builder notificationBuilder = new android.support.v4.app.NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.stat_notify_chat)
                        .setDefaults(android.support.v4.app.NotificationCompat.DEFAULT_ALL)
                        .setContentTitle("Racing Team- " + name)
                        .setContentText(notificationText);


                PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                        new Intent(this, NotificationActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

                notificationBuilder.setContentIntent(contentIntent);


                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(1, notificationBuilder.build());
            }
        }
    }


}

