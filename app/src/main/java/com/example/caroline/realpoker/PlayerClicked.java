package com.example.caroline.realpoker;

import android.content.DialogInterface;
import android.util.Log;


/**
 * Created by maylisw on 11/13/17.
 */

public class PlayerClicked implements DialogInterface.OnClickListener {
    private int item;
    private boolean hasBeenClicked= false;
    public static final String TAG = "tag you're it";
    @Override
    public void onClick(DialogInterface dialog, int which) {
        Log.d(TAG, "onClick: "+which);
        item = which;
        hasBeenClicked = true;
        HelpMe help = new HelpMe(item);
        //dialog.dismiss();
    }

    public int getItem() {
        return item;
    }

    public boolean hasBeenClicked() {
        return hasBeenClicked;
    }
}
