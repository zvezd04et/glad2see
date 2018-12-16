package com.z.glad2see.ui.contact_list.mvp;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.github.tamir7.contacts.Contact;
import com.z.glad2see.R;
import com.z.glad2see.model.DataUtils;
import com.z.glad2see.ui.contact_list.ContactListAdapter;
import com.z.glad2see.ui.edit_contact_view.EditContactActivity;
import com.z.glad2see.ui.note_list.NoteListAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static com.z.glad2see.ui.edit_contact_view.EditContactActivity.REQUEST_CODE;
import static com.z.glad2see.ui.edit_contact_view.EditContactActivity.TO_MAIN_CODE;

public class ContactListActivity extends MvpAppCompatActivity implements ContactListView {

    private RecyclerView recyclerView;

    @InjectPresenter
    ContactListPresenter presenter;

    private ContactListAdapter adapter;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, ContactListActivity.class);
    }


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_list_activity);

        recyclerView = findViewById(R.id.my_recycler_view);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ContactListAdapter.OnItemClickListener clickListener = contactId -> presenter.onItemClicked(contactId);

        adapter = new ContactListAdapter(new ArrayList<>(), this, clickListener);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(ContactListPresenter.TAG, "onCreate: " + presenter);

    }

    @Override
    public void showContactList(final List<Contact> contacts) {
        Log.d(ContactListPresenter.TAG, "showContactList: " + contacts);
        adapter.setData(contacts);
    }

    @Override
    public void openContactEditorActivity(final long contactId) {
        startActivityForResult(EditContactActivity.getIntent(this, contactId), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == TO_MAIN_CODE) finish();
    }
}
