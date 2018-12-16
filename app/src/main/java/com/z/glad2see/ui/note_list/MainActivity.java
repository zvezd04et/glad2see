package com.z.glad2see.ui.note_list;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.z.glad2see.R;
import com.z.glad2see.ui.edit_contact_view.EditContactActivity;

import java.util.ArrayList;

public class MainActivity extends MvpAppCompatActivity implements MainActivityView {

    @InjectPresenter
    MainActivityPresenter mainActivityPresenter;

    private RecyclerView recyclerView;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, EditContactActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_list_activity);

        initFab();

        recyclerView = findViewById(R.id.my_recycler_view);
        NoteListAdapter.OnItemClickListener clickListener = contactId -> mainActivityPresenter.onItemClicked(contactId);
        NoteListAdapter adapter = new NoteListAdapter(new ArrayList<>(), this, clickListener);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    void initFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            RxPermissions rxPermissions = new RxPermissions(this);
            rxPermissions.request(Manifest.permission.READ_CONTACTS)
                    .subscribe(isGranted ->
                            {
                                if (isGranted) {
                                    startActivity(MainActivity.getStartIntent(this));
                                }
                            },
                            Throwable::printStackTrace
                    );
        });
    }

    @Override
    public void showNotes() {

    }

    @Override
    public void openContactEditorActivity(long contactId) {
        startActivity(EditContactActivity.getIntent(this, contactId));
    }
}
