package com.z.glad2see.ui.contact_list.mvp;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.github.tamir7.contacts.Contact;

import java.util.List;

public interface ContactListView extends MvpView {

    @StateStrategyType(SingleStateStrategy.class)
    void showContactList(List<Contact> contacts);
}
