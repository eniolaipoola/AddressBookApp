package com.eniola.addressbookapp.repository

import com.eniola.addressbookapp.repository.data.Contact
import javax.inject.Inject


class ContactRepository @Inject constructor(
    private val database: AppRoomDatabase
) {

    suspend fun fetchAllContacts(): List<Contact>{
        return database.contactDao().fetchAllContacts()
    }

    suspend fun updateContact(contact_id: Long, firstName: String, lastName: String, email: String,
                              phoneNumber: String, updatedAt: String){
        return database.contactDao().updateContact(contact_id, firstName, lastName, email,
            phoneNumber, updatedAt)
    }

    suspend fun deleteContact(contactId: String) {
        return database.contactDao().deleteContact(contactId)
    }

    suspend fun insertNewContact(contact: Contact) {
        return database.contactDao().insertNewContact(contact)
    }
}