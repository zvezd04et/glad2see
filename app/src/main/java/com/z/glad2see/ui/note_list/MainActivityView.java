package com.z.glad2see.ui.note_list;

import com.arellomobile.mvp.MvpView;
import com.z.glad2see.model.Note;

import java.util.List;

public interface MainActivityView extends MvpView {

    void showNotes(List<Note> notes);

    void showState(String state);

    void openContactEditorActivity(long contactId);

}
