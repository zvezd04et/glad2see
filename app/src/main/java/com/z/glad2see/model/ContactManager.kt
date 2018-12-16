package com.z.glad2see.model

import com.github.tamir7.contacts.Contact
import com.github.tamir7.contacts.Contacts
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ContactManager {

    fun getContactIdByPhone(number: String): Long {
        val contacts = getContactsByPhone(number).takeIf { it.isNotEmpty() }
        return contacts?.takeIf { it.isNotEmpty() }!![0].id ?: 0
    }

    fun getAllContacts(): Single<List<Contact>> {
        return Single.fromCallable {
            getContacts()
        }.observeOn(Schedulers.newThread())
    }

    fun getContacts(): List<Contact> {
        val list = Contacts.getQuery().find()
        return list
    }

    fun getContactsByPhone(number: String): List<Contact> {
        return Contacts.getQuery()
            .whereEqualTo(Contact.Field.PhoneNumber, number)
            .find()
    }

    fun getContactById(contactId: Long): List<Contact> {
        return Contacts.getQuery()
            .whereEqualTo(Contact.Field.ContactId, contactId)
            .find()
    }
}