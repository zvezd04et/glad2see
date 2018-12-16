package com.z.glad2see.ui.contact_list;

import com.github.tamir7.contacts.Contact;
import com.z.glad2see.R;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {

    private final List<Contact> contactList;
    private final LayoutInflater inflater;
    private final OnItemClickListener clickListener;

    public ContactListAdapter(@NonNull final List<Contact> contactList, @NonNull final Context context, OnItemClickListener clickListener) {
        this.contactList = contactList;
        inflater = LayoutInflater.from(context);
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        return new ContactViewHolder(inflater.inflate(R.layout.contacts_list_item, viewGroup, false), clickListener);
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
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(long contactId);
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameTextView;
        private final TextView phoneTextView;


        public ContactViewHolder(@NonNull final View itemView, ContactListAdapter.OnItemClickListener clickListener) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.title_text_view);
            phoneTextView = itemView.findViewById(R.id.data_text_view);

            itemView.setOnClickListener(v -> clickListener.onItemClick((contactList.get(getAdapterPosition())).getId()));
        }

        public void bind(@NonNull Contact contactItem) {
            String firstName = getNameOrEmpty(contactItem.getGivenName());
            String lastName = getNameOrEmpty(contactItem.getFamilyName());
            String fullName = firstName + " " + lastName;
            nameTextView.setText(fullName);
            if (!contactItem.getPhoneNumbers().isEmpty()) {
                phoneTextView.setText(contactItem.getPhoneNumbers().get(0).getNumber());
            }
        }

        @NonNull
        private String getNameOrEmpty(final @Nullable String name) {
            if (name != null) {
                return name;
            }
            return "";
        }

    }
}
