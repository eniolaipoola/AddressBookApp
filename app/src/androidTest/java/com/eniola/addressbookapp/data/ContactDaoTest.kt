package com.eniola.addressbookapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.eniola.addressbookapp.repository.AppRoomDatabase
import com.eniola.addressbookapp.repository.data.Contact
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Copyright (c) 2021 Eniola Ipoola
 * All rights reserved
 * Created on 06-May-2021
 */

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ContactDaoTest {

    private lateinit var database: AppRoomDatabase

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        // Using an in-memory database so that the information stored here disappears when the
        // process is killed.
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            AppRoomDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertContact() = runBlocking {
        val contact = Contact("Eniola", "Ipoola",
            "eniolaipoola@gmail.com", "07063738144", "", "")
        database.contactDao().insertNewContact(contact)

        // WHEN - Get the contact by id from the database.
        val loadedContact =  database.contactDao().fetchContactByPhoneNumber(contact.phoneNumber)

        // THEN - The loaded data contains the expected values.
        assertThat(loadedContact as Contact, notNullValue())
        assertThat(loadedContact.firstName, `is`(contact.firstName))
        assertThat(loadedContact.lastName, `is`(contact.lastName))
        assertThat(loadedContact.email, `is`(contact.email))
        assertThat(loadedContact.phoneNumber, `is`(contact.phoneNumber))
    }

/*
    @Test
    fun updateContactTest() = runBlocking {
        val contact = Contact("Eniola", "Ipoola",
            "eniolaipoola@gmail.com", "07063738144", "", "")
        database.contactDao().insertNewContact(contact)

        //update contact
        val updatedContact = database.contactDao().updateContact(contact.id,
            "Taiwo", "Ipoola", "eniolaipo@gmail.com",
            "07063738144",  "07063738143")

        //new Updated value
        val newContactValue = database.contactDao().fetchContactByPhoneNumber("07063738144")

        assertThat(contact.id, `is`(newContactValue.id))
        assertThat(contact.phoneNumber, `is`(newContactValue.phoneNumber))

    }
*/


}