package com.z.glad2see.ui.dialogs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.z.glad2see.App;
import com.z.glad2see.db.NotesDao;
import com.z.glad2see.model.Note;
import com.z.glad2see.utils.SupportUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class NoteActivityPresenter extends MvpPresenter<NoteActivityView> {

    private static final String LOG_TAG = "NoteActivityPresenter";

    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    @NonNull
    private NotesDao notesDao = App.getDatabase().getNotesDao();
    @NonNull
    private String number;

    public NoteActivityPresenter(@NonNull String number) {
        this.number = number;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        long contactId = 0;
        try {
            contactId = App.getContactManager().getContactIdByPhone(number);
        } catch (Exception ex) {
            ex.printStackTrace();
            getViewState().close();
        }

        Disposable disposable = notesDao.getNoteById(contactId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processLoading, this::handleError);
        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SupportUtils.disposeSafely(compositeDisposable);
    }

    private void processLoading(@Nullable Note note) {
        if (note == null) {
            getViewState().close();
        }
        getViewState().showNote(number, note.getTextNote());
    }

    private void handleError(@NonNull Throwable th) {
        Log.e(LOG_TAG, th.getMessage(), th);
        SupportUtils.disposeSafely(compositeDisposable);
    }
}
