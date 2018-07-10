package com.example.fourofour.racingteamsmartlab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StartActivity extends AppCompatActivity {
    private static String mUsername;

    private static final int RC_SIGN_IN = 123;
    public static String loginTxt;

    private Button labStatus;
    public static boolean isLoggedIn;

    public static boolean inLab;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    public static long viewedNotiNum=0;
    public static long onFirebaseNoti;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private static ChildEventListener mChildEventListener;


    public static String getUserName() {
        return mUsername;
    }

    public static boolean inLab1;
    public static String name;
    public static String loginTxt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("LabMembers");

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

        inLab = false;

        mMessagesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FriendlyMessage friendlyMessage = dataSnapshot.child(getUserName()).getValue(FriendlyMessage.class);
                loginTxt1 = friendlyMessage.getText();
//                Toast.makeText(getApplicationContext(), loginTxt1, Toast.LENGTH_SHORT).show();
                if (loginTxt1.equals("In lab")) {
                    inLab = true;
                }
                else {
                    inLab = false;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        inLab = !inLab;
        labStatus= findViewById(R.id.labButton);
//        inLab=false;
        TextView labstatus= findViewById(R.id.self_occupancy_status);
//        inLab = !inLab;
        changeText(!inLab);
//        labstatus.setTextColor(getResources().getColor(R.color.Red));
        labStatus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                For double click use this proceeding statement
//                inLab= !inLab;

                changeText(inLab);
                if (inLab) {
                    loginTxt = "Not in lab";
                }
                else {
                    loginTxt = "In lab";
                }
                String name = getUserName();
//                LoginStatus loginStatus = new LoginStatus(getUserName(), loginTxt);
                FriendlyMessage friendlyMessage = new FriendlyMessage(loginTxt, getUserName(), null);
                mMessagesDatabaseReference.child(getUserName()).setValue(friendlyMessage);


//              For single click use this proceeding statement
                inLab= !inLab;


//                mMessagesDatabaseReference.child(getUserName()).child("name").setValue(getUserName());
//                mMessagesDatabaseReference.child(getUserName()).child("status").setValue(loginTxt);

            }
        });

        final TextView labList = (TextView)findViewById(R.id.LabMembersName);


    }

    private void changeText(Boolean inlab) {
        TextView changetext= findViewById(R.id.self_occupancy_status);
        if(!inlab) {
            changetext.setText("In lab");
            changetext.setTextColor(getResources().getColor(R.color.Green));
        }
        else {
            changetext.setText("Not in lab");
            changetext.setTextColor(getResources().getColor(R.color.Red));
        }
    }


    public void InLabList(View view) {
        Intent i = new Intent(this, InLabList.class);
        startActivity(i);
    }


    public void notificationPage (View view) {
        Intent i = new Intent(this, NotificationActivity.class);
        startActivity(i);
    }

    public void machinesPage(View view) {
        Intent i = new Intent(this, Machines.class);
        startActivity(i);
    }
    public void deadlinePage (View view) {
        Intent i = new Intent(this, DeadlineActivity.class);
        startActivity(i);
    }
    public void safetyPage (View view) {
        Intent i = new Intent(this, LabSafetyActivity.class);
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
        mMessagesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FriendlyMessage friendlyMessage = dataSnapshot.child(getUserName()).getValue(FriendlyMessage.class);
                loginTxt1 = friendlyMessage.getText();
//                Toast.makeText(getApplicationContext(), loginTxt1, Toast.LENGTH_SHORT).show();
                if (loginTxt1.equals("In lab")) {
                    inLab = true;
                }
                else {
                    inLab = false;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        changeText(!inLab);
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

    public void changeMyLoginStatus() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
//merging change
