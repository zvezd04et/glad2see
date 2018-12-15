package com.z.glad2see.ui.contact_list;

import com.github.tamir7.contacts.Contact;
import com.z.glad2see.R;
import com.z.glad2see.model.Note;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ContactViewHolder extends RecyclerView.ViewHolder {

    private final TextView nameTextView;
    private final TextView phoneTextView;



    public ContactViewHolder(@NonNull final View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.title_text_view);
        phoneTextView = itemView.findViewById(R.id.data_text_view);

        itemView.setOnClickListener(v -> {
            //TODO click
        });
    }

    public void bind(@NonNull Contact contactItem) {
        String fullName = contactItem.getGivenName() + " " + contactItem.getFamilyName();
        nameTextView.setText(fullName);
        phoneTextView.setText(contactItem.getPhoneNumbers().get(0).getNumber());
    }
}
