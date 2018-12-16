package com.z.glad2see.ui.edit_contact_view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.github.tamir7.contacts.Contact
import com.z.glad2see.App
import com.z.glad2see.R
import com.z.glad2see.model.Note
import com.z.glad2see.ui.contact_list.mvp.ContactListPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.details_note_activity.*

class EditContactActivity : MvpAppCompatActivity(), EditContactView {

    @InjectPresenter
    lateinit var presenter: EditContactPresenter

    @ProvidePresenter
    internal fun providePresenter() = EditContactPresenter()

    private var isHave = false
    private var contactId: Long = 0

    override fun showErrorInfoAndFinish() {
        Toast.makeText(this, R.string.no_contact_info, Toast.LENGTH_LONG).show()
        finish()
    }

    override fun displayDetails(contact: Contact) {
        val firstName = contact.givenName ?: ""
        val lastName = contact.familyName ?: ""
        full_name_text_view.text = "$firstName $lastName"
        phone_text_view.text = "${contact.phoneNumbers[0].number}"
    }

    override fun navigateToMainScreen() {
        setResult(TO_MAIN_CODE)
        finish()
    }

    override fun navigateBack() {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(ContactListPresenter.TAG, "onCreate")
        setContentView(R.layout.details_note_activity)


        contactId = intent.getLongExtra(CONTACT_ID_KEY, -1)
        isHave = intent.getBooleanExtra(CONTACT_NOTE_HAVE_KEY, false)
        presenter.setData(contactId)

        initToolbar()
        val dispose = App.getDatabase().notesDao.getNoteById(contactId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ note ->
                    if (note != null) {
                        note_edit_txt.setText(note.textNote)
                    }
                }, {})
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (!isHave) {
            menuInflater.inflate(R.menu.details_note_activity, menu)
        } else {
            menuInflater.inflate(R.menu.remove_menu, menu)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_item_save -> presenter.saveChanges(note_edit_txt?.text.toString())
            android.R.id.home -> onBackPressed()
            R.id.action_remove -> presenter.removeItem(contactId)
        }
        return true
    }

    override fun onPause() {
        super.onPause()
        presenter.releaseSubscriptions()
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
        const val CONTACT_NOTE_HAVE_KEY = "IS_ALREADY_HAVE"
        const val TO_MAIN_CODE = 1
        const val REQUEST_CODE = 2

        @JvmStatic
        fun getIntent(context: Context, contactId: Long): Intent {
            return Intent(context, EditContactActivity::class.java)
                    .putExtra(CONTACT_ID_KEY, contactId)
        }
    }
}