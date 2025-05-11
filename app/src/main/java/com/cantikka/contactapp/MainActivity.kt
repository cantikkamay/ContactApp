package com.cantikka.contactapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cantikka.contactapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val contactViewModel: ContactViewModel by viewModels()
    private lateinit var contactAdapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        contactViewModel.contactDatabase = ContactDatabase.getInstance(this)

        contactAdapter = ContactAdapter()
        binding.rvContacts.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = contactAdapter
        }

        contactViewModel.contacts.observe(this) { contacts ->
            contactAdapter.submitList(contacts)
        }

        binding.btnAddContact.setOnClickListener {
            val intent = Intent(this, AddContactActivity::class.java)
            startActivity(intent)
        }

        contactViewModel.getContacts()
    }

    override fun onResume() {
        super.onResume()
        contactViewModel.getContacts()
    }
}
