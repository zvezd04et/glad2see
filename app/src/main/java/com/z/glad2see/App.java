package com.z.glad2see;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.z.glad2see.db.AppDatabase;
import com.z.glad2see.db.NotesRepository;
import com.z.glad2see.model.DataUtils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class App extends Application {

    private static final String LOG_TAG = "App";

    @NonNull
    protected final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @NonNull
    private static AppDatabase database;

    @NonNull
    public static AppDatabase getDatabase() {
        return database;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        database = AppDatabase.getInstance(this);

        final Disposable disposable = NotesRepository.saveData(DataUtils.generateNotes())
                .subscribeOn(Schedulers.io())
                .subscribe(() -> Log.d(LOG_TAG, "Success"));
        compositeDisposable.add(disposable);
    }

}
