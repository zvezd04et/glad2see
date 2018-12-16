package com.z.glad2see.ui.edit_contact_view

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.tamir7.contacts.Contact
import com.z.glad2see.App
import com.z.glad2see.model.Note
@InjectViewState
class EditContactPresenter : MvpPresenter<EditContactView>() {

    private val notesRepository = App.getDatabase().notesDao
    private val contactManager = App.getContactManager()

    private var contact: Contact? = null

    fun saveChanges(noteTxt: String) {
        notesRepository.insert(Note(contact!!.id, noteTxt))
        viewState.navigateToMainScreen()
    }

    fun navigateBack() {
        viewState.navigateBack()
    }

    fun setData(contactId: Long) {
        this.contact = contactManager.getContactById(contactId)[0]
        if (contact == null) {
            viewState.showErrorInfoAndFinish()
        } else {
            viewState.displayDetails(contact!!)
        }
    }

}