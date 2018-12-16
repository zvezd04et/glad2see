package com.z.glad2see.ui.note_list;

import com.github.tamir7.contacts.Contact;
import com.z.glad2see.R;
import com.z.glad2see.model.Note;
import com.z.glad2see.ui.contact_list.ContactListAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    private final List<Note> noteList;
    private final LayoutInflater inflater;
    private final OnItemClickListener clickListener;

    public NoteListAdapter(@NonNull final List<Note> noteList, @NonNull final Context context, OnItemClickListener clickListener) {
        this.noteList = noteList;
        inflater = LayoutInflater.from(context);
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        return new NoteViewHolder(inflater.inflate(R.layout.notes_list_item, viewGroup, false), clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final NoteViewHolder noteViewHolder, final int position) {
        noteViewHolder.bind(noteList.get(position));
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void setData(@NonNull List<Note> noteList) {
        this.noteList.addAll(noteList);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(long contactId);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private final TextView idTextView;
        private final TextView noteTextView;



        public NoteViewHolder(@NonNull final View itemView, NoteListAdapter.OnItemClickListener clickListener) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.title_text_view);
            noteTextView = itemView.findViewById(R.id.notes_text_view);

            itemView.setOnClickListener(v -> clickListener.onItemClick((noteList.get(getAdapterPosition())).getContactId()));
        }

        public void bind(@NonNull Note noteItem) {
            idTextView.setText(noteItem.getContactId() + "");
            noteTextView.setText(noteItem.getTextNote());
        }
    }
}
