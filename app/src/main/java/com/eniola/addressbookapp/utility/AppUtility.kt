package com.eniola.addressbookapp.utility

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

/**
 * Copyright (c) 2021 Eniola Ipoola
 * All rights reserved
 * Created on 06-May-2021
 */

fun ViewModel.runIO(function: suspend CoroutineScope.() -> Unit){
    viewModelScope.launch (Dispatchers.IO) {
        function()
    }
}

suspend fun <T> safeDatabaseCall(dispatcher: CoroutineDispatcher = Dispatchers.IO,
                            databaseCall: suspend () -> T) : ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(databaseCall.invoke())
        } catch (throwable: Throwable){
            throwable.printStackTrace()
            ResultWrapper.Error(null, throwable.message)
        }
    }

}

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class Error(
        val errorCode: Int? = null,
        val errorMessage: String? = null): ResultWrapper<Nothing>()
    data class Loading<out T>(val value: T): ResultWrapper<T>()

}


fun View.hide(onlyInvisible: Boolean = false) {
    this.visibility = if(onlyInvisible) View.VISIBLE else View.GONE
}

fun View.show(){
    this.visibility = View.VISIBLE
}

fun Context.toast(message: String?, length: Int = Toast.LENGTH_LONG ) {
    Toast.makeText(this, message, length).show()
}