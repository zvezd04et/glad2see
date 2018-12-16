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
    List<Note> getAllNotes();

    @Query("SELECT * FROM notes WHERE contact_id = :id")
    Note getNoteById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Note> notes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);

    @Query("DELETE FROM notes")
    void deleteAll();
}
