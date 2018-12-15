package com.z.glad2see.ui.contact_list.mvp;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.z.glad2see.App;

import android.util.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ContactListPresenter extends MvpPresenter<ContactListView> {

    public static final String TAG = "contact_list_debug";

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        Log.d(TAG, "onFirstViewAttach: ");
        compositeDisposable.add(
                App.getContactManager().getAllContacts()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                contacts -> {
                                    getViewState().showContactList(contacts);
                                    Log.d(TAG, "onFirstViewAttach: " + contacts.size());
                                },
                                throwable -> Log.d(TAG, "contact loading error", throwable)
                        )
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
