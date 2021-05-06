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

    @Update
    suspend fun updateContact(updateDetails: Contact)

    @Query("DELETE from Contact where id=:contactId")
    suspend fun deleteContact(contactId: String)
}