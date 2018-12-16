package com.z.glad2see.ui.dialogs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.z.glad2see.R;

public class NoteActivity extends MvpAppCompatActivity implements NoteActivityView {

    private static final String LOG_TAG = "NoteActivity";
    private static final String EXTRA_NUMBER = "EXTRA_NUMBER";

    @NonNull
    private TextView noteTv;

    @InjectPresenter
    public NoteActivityPresenter presenter;

    @ProvidePresenter
    public NoteActivityPresenter providePresenter() {
        String number = getIntent().getStringExtra(EXTRA_NUMBER);
        return new NoteActivityPresenter(number);
    }

    public static void start(@NonNull Context context, String number) {
        final Intent intent = new Intent(context.getApplicationContext(), NoteActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRA_NUMBER, number);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        noteTv = findViewById(R.id.tv_note);

    }



    @Override
    protected void onPause() {
        super.onPause();
        //finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void showNote(String number, String note) {
        setTitle(number);
        noteTv.setText(note);
    }

    @Override
    public void close() {
        finish();
    }
}
