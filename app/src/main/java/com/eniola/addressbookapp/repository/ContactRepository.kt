package com.eniola.addressbookapp.repository

import com.eniola.addressbookapp.repository.data.Contact
import javax.inject.Inject


class ContactRepository @Inject constructor(
    private val database: AppRoomDatabase
) {

    suspend fun fetchAllContacts(): List<Contact>{
        return database.contactDao().fetchAllContacts()
    }

    suspend fun editContact(contactDetails: Contact){
        return database.contactDao().updateContact(contactDetails)
    }

    suspend fun deleteContact(contactId: String) {
        return database.contactDao().deleteContact(contactId)
    }

    suspend fun insertNewContact(contact: Contact) {
        return database.contactDao().insertNewContact(contact)
    }
}