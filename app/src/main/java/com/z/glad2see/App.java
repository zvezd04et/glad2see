package com.z.glad2see;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.z.glad2see.db.AppDatabase;
import com.z.glad2see.db.NotesRepository;
import com.z.glad2see.model.DataUtils;

import java.io.IOException;
import java.net.SocketException;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.plugins.RxJavaPlugins;
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

        setRxErrorHandler();
        database = AppDatabase.getInstance(this);

//        final Disposable disposable = NotesRepository.saveData(DataUtils.generateNotes())
//                .subscribeOn(Schedulers.io())
//                .subscribe(() -> Log.d(LOG_TAG, "Success"));
//        compositeDisposable.add(disposable);
    }

    private void setRxErrorHandler() {
        RxJavaPlugins.setErrorHandler(e -> {
            if (e instanceof UndeliverableException) {
                e.getCause();
            }
            if ((e instanceof SocketException) || (e instanceof IOException)) {
                Log.d(LOG_TAG, "Irrelevant network problem or API that throws on cancellation", e);
                return;
            }
            if (e instanceof InterruptedException) {
                Log.d(LOG_TAG, "Some blocking code was interrupted by a dispose call", e);
                return;
            }
            if ((e instanceof NullPointerException) || (e instanceof IllegalArgumentException)) {
                Log.d(LOG_TAG, "That's likely a bug in the application", e);
                return;
            }
            if (e instanceof IllegalStateException) {
                Log.d(LOG_TAG, "That's a bug in RxJava or in a custom operator", e);
                return;
            }
            Log.d(LOG_TAG, "Undeliverable exception received, not sure what to do", e);
        });
    }
}
