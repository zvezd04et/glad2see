package com.z.glad2see.ui.NoteList;

import com.z.glad2see.R;
import com.z.glad2see.model.DataUtils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class NoteListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_list_activity);

        recyclerView = findViewById(R.id.my_recycler_view);
        NoteListAdapter adapter = new NoteListAdapter(DataUtils.generateNotes(), this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
