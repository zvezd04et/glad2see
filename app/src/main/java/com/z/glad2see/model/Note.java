package com.z.glad2see.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "notes")
public class Note implements Serializable {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "phone_number")
    private String number;
    @ColumnInfo(name = "contact")
    private String contact;
    @ColumnInfo(name = "contact_id")
    private int contactId;
    @ColumnInfo(name = "text_note")
    private String textNote;

    public Note(int id, String number, String contact, int contactId, String textNote) {
        this.id = id;
        this.number = number;
        this.contact = contact;
        this.contactId = contactId;
        this.textNote = textNote;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getTextNote() {
        return textNote;
    }

    public void setTextNote(String textNote) {
        this.textNote = textNote;
    }
}
