package com.z.glad2see.ui.note_list

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.tamir7.contacts.Contact
import com.z.glad2see.App
import com.z.glad2see.addTo
import com.z.glad2see.disposeSafely
import com.z.glad2see.model.Note
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

@InjectViewState
class MainActivityPresenter : MvpPresenter<MainActivityView>() {

    private val subscriptions = CompositeDisposable()
    private var notesList = emptyList<NoteItemUi>()
    private val contactManager = App.getContactManager()

    fun onItemClicked(contactId: Long) {
        viewState.openContactEditorActivity(contactId)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        updateNoteList()
    }

    fun updateNoteList() {
        getAllNotes()
            .flatMap { notes ->
                getContactsById(notes.map { it.contactId })
                    .flatMap { contacts ->
                        Single.fromCallable {
                            createNoteItems(contacts, notes)
                        }
                    }
            }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { itemList ->
                    processResults(itemList)
                },
                ::handleError
            )
            .addTo(subscriptions)
    }

    private fun getAllNotes(): Single<List<Note>> {
        return Single.fromCallable {
            App.getDatabase().notesDao.allNotes
        }
            .observeOn(Schedulers.newThread())
    }

    private fun getContactsById(ids: List<Long>): Single<List<Contact>> {
        return Single.fromCallable {
            val list = ArrayList<Contact>()
            ids.forEach { id ->
                list.add(contactManager.getContactById(id)[0])
            }
            list
        }
    }

    private fun createNoteItems(contacts: List<Contact>, notes: List<Note>): List<NoteItemUi> {
        return contacts.map { contact ->
            val note = notes.find { contact.id == it.contactId }
            val firstName = contact.givenName ?: ""
            val lastName = contact.familyName ?: ""
            NoteItemUi(contact.id, firstName, lastName, note?.textNote ?: "")
        }
    }

    private fun processResults(uiItems: List<NoteItemUi>) {
        notesList = uiItems
        viewState.showNotes(notesList)
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.disposeSafely()
    }

    private fun handleError(throwable: Throwable) {
        viewState.showState(throwable.message)
    }
}