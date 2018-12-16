package com.z.glad2see.ui.note_list;

import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.z.glad2see.R;
import com.z.glad2see.model.DataUtils;
import com.z.glad2see.ui.contact_list.mvp.ContactListActivity;

public class MainActivity extends MvpAppCompatActivity implements MainActivityView {

    @InjectPresenter
    MainActivityPresenter mainActivityPresenter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_list_activity);

        initFab();

        recyclerView = findViewById(R.id.my_recycler_view);
        NoteListAdapter adapter = new NoteListAdapter(DataUtils.generateNotes(), this);

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
                                    startActivity(ContactListActivity.getStartIntent(this));
                                }
                            },
                            Throwable::printStackTrace
                    );
        });
    }

    @Override
    public void showNotes() {

    }
}
