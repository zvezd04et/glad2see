package com.z.glad2see.ui.note_list;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.view.MenuItem;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.z.glad2see.R;
import com.z.glad2see.model.Note;
import com.z.glad2see.ui.contact_list.mvp.ContactListActivity;
import com.z.glad2see.ui.edit_contact_view.EditContactActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainActivity extends MvpAppCompatActivity implements MainActivityView {

    @InjectPresenter
    MainActivityPresenter mainActivityPresenter;

    private RecyclerView recyclerView;
    private NoteListAdapter adapter;

    private CompositeDisposable subscriptions = new CompositeDisposable();

    public static Intent getStartIntent(Context context) {
        return new Intent(context, ContactListActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_list_activity);

        initFab();

        recyclerView = findViewById(R.id.my_recycler_view);
        NoteListAdapter.OnItemClickListener clickListener = contactId -> mainActivityPresenter.onItemClicked(contactId);
        adapter = new NoteListAdapter(new ArrayList<>(), this, clickListener);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) !=
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) !=
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CALL_LOG)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CALL_LOG}, 1);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CALL_LOG}, 1);
            }
        }
    }

    void initFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            RxPermissions rxPermissions = new RxPermissions(this);
            Disposable subscription = Observable.just(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED
            )
                    .flatMap(isGranted -> rxPermissions.request(Manifest.permission.READ_CONTACTS))
                    .subscribe(isGranted ->
                            {
                                if (isGranted) {
                                    startActivity(MainActivity.getStartIntent(this));
                                }
                            },
                            Throwable::printStackTrace
                    );
            subscriptions.add(subscription);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainActivityPresenter.updateNoteList();
    }

    @Override
    public void showNotes(List<Note> notes) {
        adapter.setData(notes);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscriptions.dispose();
    }

    @Override
    public void showState(String state) {
        Toast.makeText(this, state, Toast.LENGTH_LONG).show();
    }

    @Override
    public void openContactEditorActivity(long contactId) {
        Intent intent = EditContactActivity.getIntent(this, contactId);
        intent.putExtra(EditContactActivity.CONTACT_NOTE_HAVE_KEY,true);
        startActivity(intent);
    }
}
