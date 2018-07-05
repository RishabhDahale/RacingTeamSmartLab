package com.example.fourofour.racingteamsmartlab;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

public class InLabAdapter extends ArrayAdapter<LoginStatus> {
    public InLabAdapter(@NonNull Context context, int resource, List<LoginStatus> objects) {
        super(context, resource, objects);
    }




}
