package com.eniola.addressbookapp.repository

import androidx.room.*
import com.eniola.addressbookapp.repository.data.Contact

/**
 * Copyright (c) 2021 Eniola Ipoola
 * All rights reserved
 * Created on 05-May-2021
 */

@Dao
interface ContactDao {

    @Query("select * from Contact")
    suspend fun fetchAllContacts(): List<Contact>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewContact(contactDetails: Contact)

    @Query("UPDATE Contact set firstName=:firstName, lastName=:lastName, email =:email, phoneNumber=:phoneNumber, updatedAt=:updatedAt where id =:contact_id")
    suspend fun updateContact(contact_id: Long, firstName: String, lastName: String, email: String,
                              phoneNumber: String, updatedAt: String)

    @Query("DELETE from Contact where id=:contactId")
    suspend fun deleteContact(contactId: String)
}