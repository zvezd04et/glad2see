package com.z.glad2see.ui.edit_contact_view

import com.arellomobile.mvp.MvpPresenter
import com.github.tamir7.contacts.Contact
import com.z.glad2see.App
import com.z.glad2see.model.Note

class EditContactPresenter : MvpPresenter<EditContactView>() {

    private val notesRepository = App.getDatabase()

    private lateinit var contact: Contact

    fun finishWithError() {
        viewState.showErrorInfoAndFinish()
    }

    fun saveChanges(noteTxt: String) {
        notesRepository.notesDao.insert(Note(contact.id, noteTxt))
        viewState.navigateToMainScreen()
    }

    fun navigateBack() {
        viewState.navigateBack()
    }

    fun setData(contact: Contact) {
        this.contact = contact
        viewState.displayDetails(contact)
    }

}