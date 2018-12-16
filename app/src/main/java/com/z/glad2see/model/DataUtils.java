package com.z.glad2see.model;

import java.util.ArrayList;
import java.util.List;

public class DataUtils {

    public static List<Note> generateNotes() {
        List<Note> notes = new ArrayList<>();
        notes.add(new Note(1, "Call at 10"));
        notes.add(new Note(2, "Test Room"));
        notes.add(new Note(3, "Don't answer"));
        return notes;
    }

}
