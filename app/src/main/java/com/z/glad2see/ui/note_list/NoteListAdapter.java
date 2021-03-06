package com.z.glad2see.ui.note_list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.z.glad2see.R;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    private final List<NoteItemUi> noteList;
    private final LayoutInflater inflater;
    private final OnItemClickListener clickListener;

    public NoteListAdapter(@NonNull final List<NoteItemUi> noteList, @NonNull final Context context, OnItemClickListener clickListener) {
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

    public void setData(@NonNull List<NoteItemUi> noteList) {
        this.noteList.clear();
        this.noteList.addAll(noteList);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(long contactId);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameTextView;
        private final TextView noteTextView;


        public NoteViewHolder(@NonNull final View itemView, NoteListAdapter.OnItemClickListener clickListener) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_text_view);
            noteTextView = itemView.findViewById(R.id.notes_text_view);

            itemView.setOnClickListener(v -> clickListener.onItemClick((noteList.get(getAdapterPosition())).getContactId()));
        }

        public void bind(@NonNull NoteItemUi noteItem) {
            nameTextView.setText(noteItem.getFirstName() + " " + noteItem.getFamilyName());
            noteTextView.setText(noteItem.getNote());
        }
    }
}
