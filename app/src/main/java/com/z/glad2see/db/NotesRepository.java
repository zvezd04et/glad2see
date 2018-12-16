package com.z.glad2see.db;

import com.z.glad2see.App;
import com.z.glad2see.model.Note;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;

public class NotesRepository {

    private static NotesDao notesDao = App.getDatabase().getNotesDao();

    public static Completable saveData(final List<Note> notes) {
        return Completable.fromCallable((Callable<Void>) () -> {
            notesDao.deleteAll();
            notesDao.insertAll(notes);
            return null;
        });
    }
}
