package com.z.glad2see.model;

public class Note {

    private int id;
    private String number;
    private String contact;
    private int contactId;
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
