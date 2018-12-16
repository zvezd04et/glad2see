package com.z.glad2see.ui.note_list;

import com.z.glad2see.R;
import com.z.glad2see.model.Note;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    private final TextView idTextView;
    private final TextView noteTextView;



    public NoteViewHolder(@NonNull final View itemView, NoteListAdapter.OnItemClickListener clickListener) {
        super(itemView);
        idTextView = itemView.findViewById(R.id.title_text_view);
        noteTextView = itemView.findViewById(R.id.notes_text_view);

       // itemView.setOnClickListener(v -> clickListener.onItemClick((.get(getAdapterPosition())).getId()));
    }

    public void bind(@NonNull Note noteItem) {
        idTextView.setText(noteItem.getContactId() + "");
        noteTextView.setText(noteItem.getTextNote());
    }
}
