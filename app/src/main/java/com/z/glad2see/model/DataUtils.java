package com.z.glad2see.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DataUtils {

    public static List<Note> generateNotes() {
        List<Note> notes = new ArrayList<>();
        notes.add(new Note(1, "+79254073140", "Anton", 1, "Call at 10"));
        notes.add(new Note(2, "+79165766299", "Gasha", 2, "Test Room"));
        notes.add(new Note(3, "+79208344485", "Incognito", 3, "Don't answer"));
        return notes;
    }

}
