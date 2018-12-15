package com.z.glad2see.ui.edit_contact_view

import com.arellomobile.mvp.MvpView
import com.github.tamir7.contacts.Contact

interface EditContactView : MvpView {

    fun navigateToMainScreen()

    fun displayDetails(contact: Contact)

    fun showErrorInfoAndFinish()

    fun navigateBack()
}