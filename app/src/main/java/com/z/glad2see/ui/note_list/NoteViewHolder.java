package com.z.glad2see.ui.note_list;

import com.z.glad2see.R;
import com.z.glad2see.model.Note;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    private final TextView nameTextView;
    private final TextView phoneTextView;
    private final TextView noteTextView;



    public NoteViewHolder(@NonNull final View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.title_text_view);
        phoneTextView = itemView.findViewById(R.id.data_text_view);
        noteTextView = itemView.findViewById(R.id.notes_text_view);

        itemView.setOnClickListener(v -> {
            //TODO click
        });
    }

    public void bind(@NonNull Note noteItem) {
        nameTextView.setText(noteItem.getContact());
        phoneTextView.setText(noteItem.getNumber());
        noteTextView.setText(noteItem.getTextNote());
    }
}
