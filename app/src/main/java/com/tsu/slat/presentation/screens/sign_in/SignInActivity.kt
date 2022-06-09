package com.tsu.slat.presentation.screens.sign_in

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tsu.slat.presentation.screens.chat.ChatActivity
import com.tsu.slat.databinding.ActivitySignInBinding
import com.tsu.slat.presentation.screens.client_menu.ClientMenuActivity

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClickListeners()
        setObservers()
    }

    private fun setObservers() {
        viewModel.intentToMenu.observe(this) {
            if (it) {
                val intent = Intent(this, ClientMenuActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setOnClickListeners() {

        binding.btnLogIn.setOnClickListener {
            viewModel.login(
                email = binding.editEmail.text.toString(),
                password = binding.editPass.text.toString()
            ) { resId ->
                Toast.makeText(this, resources.getString(resId), Toast.LENGTH_SHORT).show()
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        viewModel.checkUserAlreadyAuthorized { resId ->
            Toast.makeText(this, resources.getString(resId), Toast.LENGTH_SHORT).show()
        }
    }
}