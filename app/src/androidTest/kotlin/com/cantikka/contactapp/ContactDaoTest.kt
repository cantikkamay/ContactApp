package com.cantikka.contactapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cantikka.contactapp.Contact
import com.cantikka.contactapp.ContactDao
import com.cantikka.contactapp.ContactDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import java.io.IOException
import org.junit.Assert.assertEquals

@RunWith(AndroidJUnit4::class)
class ContactDaoTest {

    private lateinit var contactDao: ContactDao
    private lateinit var contactDatabase: ContactDatabase

    private val contact1 = Contact(1, "Cantikka", "0899999999", "cantikka@mail.com")
    private val contact2 = Contact(2, "Aristianti", "0888888888", "aristianti@mail.com")

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        contactDatabase = Room.inMemoryDatabaseBuilder(context, ContactDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        contactDao = contactDatabase.contactDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        contactDatabase.close()
    }

    private suspend fun addOneContactToDb() {
        contactDao.insert(contact1)
    }

    private suspend fun addTwoContactsToDb() {
        contactDao.insert(contact1)
        contactDao.insert(contact2)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllContacts_returnsAllContactsFromDB() = runBlocking {
        addTwoContactsToDb()
        val allContacts = contactDao.getAll().first()
        assertEquals(allContacts[0], contact1)
        assertEquals(allContacts[1], contact2)
    }
}
