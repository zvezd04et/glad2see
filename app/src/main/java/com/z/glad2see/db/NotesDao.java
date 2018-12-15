package com.z.glad2see.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.z.glad2see.model.Note;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM notes")
    List<Note> getAllNotesObservable();

    @Query("SELECT * FROM notes WHERE phone_number = :number")
    Single<Note> getNotesByNumberSingle(String number);

    @Query("SELECT * FROM notes WHERE phone_number = :number")
    Note getNotesByNumber(String number);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Note> notes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);

    @Query("DELETE FROM notes")
    void deleteAll();
}
