package com.z.glad2see.call;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.z.glad2see.ui.dialogs.NoteActivity;

import java.util.Date;

public class CallReceiver extends PhonecallReceiver {

    private static final String LOG_TAG = "PhonecallReceiver";

    @Override
    protected void onIncomingCallStarted(Context ctx, String number, Date start) {
        NoteActivity.start(ctx, number);
    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start) {
        Log.d(LOG_TAG, "onOutgoingCallStarted");
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
        showToast(ctx, "bye-bye: " + number);
        Log.d(LOG_TAG, "onIncomingCallEnded");
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
        Log.d(LOG_TAG, "onOutgoingCallEnded");
    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start) {
        Log.d(LOG_TAG, "onMissedCall");
    }

    private void showToast(Context ctx, String text) {
        try {
            Toast.makeText(ctx, text, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}