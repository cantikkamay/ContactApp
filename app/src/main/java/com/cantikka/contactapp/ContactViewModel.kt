package com.cantikka.contactapp

import androidx.lifecycle.*
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ContactViewModel : ViewModel() {
    lateinit var contactDatabase: ContactDatabase
    val contacts: MutableLiveData<List<Contact>> by lazy { MutableLiveData<List<Contact>>() }

    fun getContacts() {
        viewModelScope.launch {
            contactDatabase.contactDao().getAll().collect {
                contacts.value = it
            }
        }
    }

    fun insertContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            contactDatabase.contactDao().insert(contact)
        }
    }

    fun getRandomNumber() = viewModelScope.async {
        (1..1000).random()
    }
}
