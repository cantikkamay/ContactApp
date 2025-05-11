package com.cantikka.contactapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.cantikka.contactapp.databinding.ActivityAddContactBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddContactBinding
    private val viewModel: ContactViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.contactDatabase = ContactDatabase.getInstance(this)

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val phone = binding.etPhone.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()

            if (name.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty()) {
                val newContact = Contact(name = name, phone = phone, email = email)
                GlobalScope.launch(Dispatchers.IO) {
                    viewModel.insertContact(newContact)
                    runOnUiThread {
                        Toast.makeText(this@AddContactActivity, "Contact added", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
