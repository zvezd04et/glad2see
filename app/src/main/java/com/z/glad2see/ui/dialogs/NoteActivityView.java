package com.z.glad2see.ui.dialogs;

import com.arellomobile.mvp.MvpView;

public interface NoteActivityView extends MvpView {

    void showNote(String number, String note);

    void close();
}
