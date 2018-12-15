package com.z.glad2see.ui.contact_list;

import com.github.tamir7.contacts.Contact;
import com.z.glad2see.R;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactViewHolder> {

    private final List<Contact> contactList;
    private final LayoutInflater inflater;

    public ContactListAdapter(@NonNull final List<Contact> contactList, @NonNull final Context context) {
        this.contactList = contactList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        return new ContactViewHolder(inflater.inflate(R.layout.notes_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ContactViewHolder noteViewHolder, final int position) {
        noteViewHolder.bind(contactList.get(position));
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public void setData(@NonNull List<Contact> contactList) {
        this.contactList.addAll(contactList);
    }
}
