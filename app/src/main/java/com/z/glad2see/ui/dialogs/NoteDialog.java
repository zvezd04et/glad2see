package com.z.glad2see.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

public class NoteDialog extends DialogFragment implements DialogInterface.OnClickListener {

    private String contact;
    private String noteText;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
                .setTitle("Title!").setPositiveButton("Ok", this)
                .setMessage("Заметочка");
        return adb.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
}
