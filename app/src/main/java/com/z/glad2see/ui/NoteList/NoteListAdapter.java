package com.z.glad2see.ui.NoteList;

import com.z.glad2see.R;
import com.z.glad2see.model.Note;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private final List<Note> noteList;
    private final LayoutInflater inflater;

    public NoteListAdapter(@NonNull final List<Note> noteList, @NonNull final Context context) {
        this.noteList = noteList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        return new NoteViewHolder(inflater.inflate(R.layout.notes_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final NoteViewHolder noteViewHolder, final int position) {
        noteViewHolder.bind(noteList.get(position));
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }
}
