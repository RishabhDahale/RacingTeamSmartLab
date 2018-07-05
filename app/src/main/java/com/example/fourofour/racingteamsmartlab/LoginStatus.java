package com.example.fourofour.racingteamsmartlab;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class LoginStatus extends FriendlyMessage implements DatabaseReference.CompletionListener {
    private String name;
    private String status;

    public LoginStatus() {

    }

    public LoginStatus(String name, String status){
        this.name = name;
        this.status = status;
    }

    public String getName() { return name; }

    public String getStatus() { return status; }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

    }
}
