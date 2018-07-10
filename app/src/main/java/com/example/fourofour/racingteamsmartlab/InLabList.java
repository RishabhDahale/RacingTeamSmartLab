package com.example.fourofour.racingteamsmartlab;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.fourofour.racingteamsmartlab.StartActivity.getUserName;

public class InLabList extends AppCompatActivity {

    private static MessageAdapter mMessageAdapter;
    private ListView mMessageListView;
    private static final int RC_SIGN_IN = 123;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private static ChildEventListener mChildEventListener;
    private static ValueEventListener mValueEventListener;

    public static boolean inLab;
    public static String name;
    public static String loginTxt;
//changes for branch
    public String[] LabList;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    public String finalLabList = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_lab_list);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("LabMembers");
        mFirebaseAuth = FirebaseAuth.getInstance();
//        final List<FriendlyMessage> friendlyMessages = new ArrayList<>();
//        mMessageAdapter = new MessageAdapter(this, R.layout.item_message, friendlyMessages);
//        mMessageListView.setAdapter(mMessageAdapter);

        final List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.GoogleBuilder().build());

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user!= null) {
                    onSignedInInitialize(user.getDisplayName());
                } else {
                    onSignedOutCleanUp();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder().setIsSmartLockEnabled(false)
                                    .setAvailableProviders(providers)
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };


//        Query query = FirebaseDatabase.getInstance().getReference().child("LabMembers").limitToLast(100);
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot child: dataSnapshot.getChildren()) {
//                    inLab = (boolean) child.child("status").getValue();
//                    name = child.child("name").getValue().toString();
//                    if(inLab) {
//                        FriendlyMessage friendlyMessage = new FriendlyMessage(name, "", null);
//                        mMessageAdapter.add(friendlyMessage);
//
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        final ArrayList <String> LabMembers = new ArrayList<String>(100);

        final TextView labList = (TextView)findViewById(R.id.LabMembersName);

//        Query query = FirebaseDatabase.getInstance().getReference().child("LabMembers");
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot child: dataSnapshot.getChildren()) {
////                    inLab = (boolean) child.child("status").getValue();
////                    name = child.child("name").getValue().toString();
//
//                    LoginStatus loginStatus = child.getValue(LoginStatus.class);
//                    inLab = loginStatus.getStatus();
//                    name = loginStatus.getName();
////                    Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();
//                    if(inLab == true) {
//                        labList.setText(labList.getText().toString() + "\n" + name);
//                        Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();
//                    }
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });



//        labList.setText((CharSequence) LabMembers);


        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                for(DataSnapshot child: dataSnapshot.getChildren()) {
//                    loginTxt = child.child("status").getValue().toString();
//                    name = child.child("name").getValue().toString();
////                    LoginStatus loginStatus = child.getValue(LoginStatus.class);
////                    inLab = loginStatus.getStatus();
////                    name = loginStatus.getName();
////                    if(loginTxt.equals("In lab")) {
//                      finalLabList = finalLabList + "\n" + name;
////                    }
//                }
                FriendlyMessage friendlyMessage = dataSnapshot.getValue(FriendlyMessage.class);
                Toast.makeText(getApplicationContext(), friendlyMessage.getText(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                for(DataSnapshot child: dataSnapshot.getChildren()) {
//                    loginTxt = child.child("status").getValue().toString();
//                    name = child.child("name").getValue().toString();
////                    LoginStatus loginStatus = child.getValue(LoginStatus.class);
////                    inLab = loginStatus.getStatus();
////                    name = loginStatus.getName();
////                    if(loginTxt.equals("In lab")) {
////                        labList.setText(labList.getText().toString() + "\n" + name);
////                    }
////                    if(loginTxt.equals("In lab")) {
//                        finalLabList = finalLabList + "\n" + name;
////                    }
//                }


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {            }
        };
//        mMessagesDatabaseReference.addChildEventListener(mChildEventListener);

//        mValueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot child: dataSnapshot.getChildren()) {
//                    loginTxt = child.child("status").getValue().toString();
//                    name = child.child("name").getValue().toString();
////                    LoginStatus loginStatus = child.getValue(LoginStatus.class);
////                    inLab = loginStatus.getStatus();
////                    name = loginStatus.getName();
////                    if(loginTxt.equals("In lab")) {
////                        labList.setText(labList.getText().toString() + "\n" + name);
////                    }
////                    if(loginTxt.equals("In lab")) {
//                        finalLabList = finalLabList + "\n" + name;
////                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        };

//        mMessagesDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot child: dataSnapshot.getChildren()) {
//                    loginTxt = child.child("status").getValue().toString();
//                    name = child.child("name").getValue().toString();
////                    LoginStatus loginStatus = child.getValue(LoginStatus.class);
////                    inLab = loginStatus.getStatus();
////                    name = loginStatus.getName();
////                    if(loginTxt.equals("In lab")) {
////                        labList.setText(labList.getText().toString() + "\n" + name);
////                    }
////                    if(loginTxt.equals("In lab")) {
//                    finalLabList = finalLabList + "\n" + name;
////                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        labList.setText(finalLabList);


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

    private void onSignedInInitialize(String username) { }

    private void onSignedOutCleanUp () { }


}
