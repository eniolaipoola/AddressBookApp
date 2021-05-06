package com.eniola.addressbookapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eniola.addressbookapp.repository.ContactRepository
import com.eniola.addressbookapp.repository.data.Contact
import com.eniola.addressbookapp.utility.ResultWrapper
import com.eniola.addressbookapp.utility.runIO
import com.eniola.addressbookapp.utility.safeDatabaseCall
import kotlinx.coroutines.Job
import javax.inject.Inject

/**
 * Copyright (c) 2021 Eniola Ipoola
 * All rights reserved
 * Created on 06-May-2021
 */

class ContactViewModel @Inject constructor(
    private val repository: ContactRepository
) : ViewModel() {

    val state = MutableLiveData<ViewState>()
    private val job = Job()

    fun cancelJob() {
        job.cancel()
    }

    fun createNewContact(firstName: String, lastName: String, email: String, phoneNumber: String){
        state.postValue(ViewState.LOADING(true))
        if(validateContactFields(firstName, phoneNumber)){
            runIO {
                when(val newContact = safeDatabaseCall {
                    val currentTimestamp = System.currentTimeMillis()
                    repository.insertNewContact(
                        Contact(firstName,lastName,email,phoneNumber,
                            currentTimestamp.toString(), currentTimestamp.toString())
                    )
                }) {

                    is ResultWrapper.Success -> {
                        state.postValue(ViewState.LOADING(false))
                        state.postValue(ViewState.SUCCESS("Contact Successfully Created"))
                    }

                    is ResultWrapper.Error -> {
                        state.postValue(ViewState.LOADING(false))
                        state.postValue(ViewState.ERROR(newContact.errorMessage))

                    }
                }
            }
        }
    }

    private fun validateContactFields(
        firstName: String, phoneNumber: String): Boolean{
        return when {
            firstName.isNullOrEmpty() -> {
                state.value = ViewState.ERROR("First name cannot be blank")
                false
            }

            phoneNumber.isEmpty() -> {
                state.value = ViewState.ERROR("Phone number cannot be blank")
                false
            }

            else -> true
        }
    }

    fun fetchAllContacts(){
        state.postValue(ViewState.LOADING(true))
        runIO {
            when(val allContacts = safeDatabaseCall {
                repository.fetchAllContacts()
            }) {
                is ResultWrapper.Success -> {
                    state.postValue(ViewState.LOADING(false))
                    state.postValue(ViewState.FETCHCONTACT(allContacts.value))
                }

                is ResultWrapper.Error -> {
                    state.postValue(ViewState.LOADING(false))
                    state.postValue(ViewState.ERROR(allContacts.errorMessage))
                }
            }
        }
    }

    fun editContact(contactId: String) {

    }

}

sealed class ViewState {
    data class SUCCESS(val message: String): ViewState()
    data class LOADING(val loading: Boolean = false) : ViewState()
    data class ERROR(val errorMessage: String?): ViewState()
    data class FETCHCONTACT(val allContact: List<Contact>): ViewState()
}