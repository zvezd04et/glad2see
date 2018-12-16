package com.z.glad2see.ui.dialogs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.z.glad2see.App;
import com.z.glad2see.R;
import com.z.glad2see.db.NotesDao;
import com.z.glad2see.model.Note;
import com.z.glad2see.utils.SupportUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NoteActivity extends AppCompatActivity {

    private static final String LOG_TAG = "NoteActivity";
    private static final String EXTRA_NUMBER = "EXTRA_NUMBER";

    @NonNull
    protected final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private NotesDao notesDao = App.getDatabase().getNotesDao();

    @NonNull
    private TextView contactTv;
    @NonNull
    private TextView noteTv;

    public static void start(@NonNull Context context, String number) {
        final Intent intent = new Intent(context.getApplicationContext(), NoteActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRA_NUMBER, number);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        String number = getIntent().getStringExtra(EXTRA_NUMBER);
        setTitle(number);

        long contactId = 0;
        try {
            contactId = App.getContactManager().getContactIdByPhone(number);
        } catch (Exception ex) {
            ex.printStackTrace();
            finish();
        }

        Disposable disposable = notesDao.getNoteById(contactId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processLoading, this::handleError);
        compositeDisposable.add(disposable);

        contactTv = findViewById(R.id.tv_contact);
        noteTv = findViewById(R.id.tv_note);

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SupportUtils.disposeSafely(compositeDisposable);
        finish();
    }

    protected void processLoading(@Nullable Note note) {

        if (note == null) {
            finish();
        }

        noteTv.setText(note.getTextNote());


    }

    protected void handleError(@NonNull Throwable th) {
        Log.e(LOG_TAG, th.getMessage(), th);
        SupportUtils.disposeSafely(compositeDisposable);
    }
}
