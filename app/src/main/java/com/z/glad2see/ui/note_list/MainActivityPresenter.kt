package com.z.glad2see.ui.note_list

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.z.glad2see.App
import com.z.glad2see.addTo
import com.z.glad2see.disposeSafely
import com.z.glad2see.model.Note
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

@InjectViewState
class MainActivityPresenter : MvpPresenter<MainActivityView>() {

    private val subscriptions = CompositeDisposable()
    private var notesList = emptyList<Note>()


    fun onItemClicked(contactId: Long) {
        viewState.openContactEditorActivity(contactId)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        updateNoteList()
    }

    fun updateNoteList() {
        Observable.fromCallable {
            App.getDatabase().notesDao.allNotes
        }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { notes ->
                    processResults(notes)
                },
                ::handleError
            )
            .addTo(subscriptions)
    }

    private fun processResults(notes: List<Note>) {
        notesList = notes
        viewState.showNotes(notesList)
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.disposeSafely()
    }

    private fun handleError(throwable: Throwable) {
        viewState.showState(throwable.message)
    }


    companion object {
        val TAG = "contact_list_debug"
    }
}