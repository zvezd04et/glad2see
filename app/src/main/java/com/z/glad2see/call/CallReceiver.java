package com.z.glad2see.call;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.z.glad2see.App;
import com.z.glad2see.model.Note;
import com.z.glad2see.db.NotesDao;
import com.z.glad2see.utils.SupportUtils;

import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CallReceiver extends PhonecallReceiver {

    private static final String LOG_TAG = "PhonecallReceiver";

    @NonNull
    protected final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private NotesDao notesDao = App.getDatabase().getNotesDao();

    private Context ctx;

    @Override
    protected void onIncomingCallStarted(Context ctx, String number, Date start) {

        this.ctx = ctx;
        Disposable disposable = App.getContactManager().getContactIdByPhone(number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processLoading,
                        this::handleError);
        compositeDisposable.add(disposable);

    }

    protected void processLoading(@Nullable Long contactId) {
        if (contactId == null) {
            return;
        }

        //TODO Error prone
        Note note = notesDao.getNoteById(contactId).get(0);
        String textNotes = note.getTextNote();
        showToast(ctx, "note: " + textNotes);
        Log.d(LOG_TAG, "onIncomingCallStarted");
        SupportUtils.disposeSafely(compositeDisposable);
    }

    protected void handleError(@NonNull Throwable th) {
        Log.e(LOG_TAG, th.getMessage(), th);
        SupportUtils.disposeSafely(compositeDisposable);
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