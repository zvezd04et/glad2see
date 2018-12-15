package com.z.glad2see.ui.edit_contact_view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.github.tamir7.contacts.Contact
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.details_note_activity.*

import com.z.glad2see.R
import kotlinx.android.synthetic.main.notes_list_item.*

class EditContactActivity : MvpAppCompatActivity(), EditContactView {

    @InjectPresenter
    lateinit var presenter: EditContactPresenter

    override fun showErrorInfoAndFinish() {
        Toast.makeText(this, R.string.no_contact_info, Toast.LENGTH_LONG).show()
        finish()
    }

    override fun displayDetails(contact: Contact) {
        full_name_text_view.text = "${contact.givenName} ${contact.familyName}"
        phone_text_view.text = "${contact.phoneNumbers.takeIf { it.isNotEmpty() }!![0]}"
    }

    override fun navigateToMainScreen() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun navigateBack() {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_note_activity)

        val contactId = intent.getStringExtra(CONTACT_ID_KEY)
        presenter.setData(contactId.toLong())

        initToolbar()
        initViews()
    }

    private fun initViews() {
        save_btn.setOnClickListener {
            presenter.saveChanges(notes_text_view.text.toString())
        }
    }

    private fun initToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        presenter.navigateBack()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val CONTACT_ID_KEY = "CONTACT_ID_KEY"

        fun getIntent(context: Context, contactId: Long): Intent {
            return Intent(context, EditContactActivity::class.java)
                .putExtra(CONTACT_ID_KEY, contactId)
        }
    }
}