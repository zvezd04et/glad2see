package com.z.glad2see.ui.edit_contact_view

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.tamir7.contacts.Contact
import com.z.glad2see.App
import com.z.glad2see.addTo
import com.z.glad2see.db.NotesRepository
import com.z.glad2see.model.Note
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

@InjectViewState
class EditContactPresenter : MvpPresenter<EditContactView>() {

    private val notesRepository = App.getDatabase().notesDao
    private val contactManager = App.getContactManager()
    private val subscriptions: CompositeDisposable = CompositeDisposable()

    private var contact: Contact? = null

    fun saveChanges(noteTxt: String?) {
        Completable.fromCallable {
            notesRepository.insert(Note(contact!!.id, noteTxt ?: ""))
        }
                .subscribeOn(Schedulers.newThread())
                .subscribe()
                .addTo(subscriptions)
        viewState.navigateToMainScreen()
    }

    fun removeItem(contactId: Long) {


        val disposable: Disposable = NotesRepository.deleteNote(contactId)
                .subscribeOn(Schedulers.io())
                .subscribe({}, {})

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

    fun releaseSubscriptions() {
        subscriptions.dispose()
    }

}