package com.z.glad2see.call;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.z.glad2see.App;
import com.z.glad2see.R;
import com.z.glad2see.model.Note;
import com.z.glad2see.db.NotesDao;
import com.z.glad2see.ui.dialogs.NoteActivity;
import com.z.glad2see.ui.dialogs.NoteDialog;
import com.z.glad2see.utils.SupportUtils;

import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.WINDOW_SERVICE;

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