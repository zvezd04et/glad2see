package com.z.glad2see.ui.note_list;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.z.glad2see.R;
import com.z.glad2see.model.Note;
import com.z.glad2see.ui.contact_list.mvp.ContactListActivity;
import com.z.glad2see.ui.edit_contact_view.EditContactActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MvpAppCompatActivity implements MainActivityView {

    @InjectPresenter
    MainActivityPresenter mainActivityPresenter;

    private RecyclerView recyclerView;
    private NoteListAdapter adapter;

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
    protected void onResume() {
        super.onResume();
        mainActivityPresenter.updateNoteList();
    }

    @Override
    public void showNotes(List<Note> notes) {
        adapter.setData(notes);
    }

    @Override
    public void showState(String state) {
        Toast.makeText(this, state, Toast.LENGTH_LONG).show();
    }

    @Override
    public void openContactEditorActivity(long contactId) {
        startActivity(EditContactActivity.getIntent(this, contactId));
    }
}
