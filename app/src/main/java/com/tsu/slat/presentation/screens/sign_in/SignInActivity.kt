package com.tsu.slat.presentation.screens.sign_in

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tsu.slat.presentation.screens.chat.ChatActivity
import com.tsu.slat.databinding.ActivitySignInBinding
import com.tsu.slat.presentation.di.SignInViewModel

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
                val intent = Intent(this, ChatActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setOnClickListeners() {

        binding.signInSignUpButton.setOnClickListener {

            viewModel.signUp(
                email = binding.signInEmailEditText.text.toString(),
                password = binding.signInPasswordEditText.text.toString(),
                nickname = binding.signInNicknameEditText.text.toString()
            ) { resId ->
                Toast.makeText(this, resources.getString(resId), Toast.LENGTH_SHORT).show()
            }
        }


        binding.signInLoginButton.setOnClickListener {
            viewModel.login(
                email = binding.signInEmailEditText.text.toString(),
                password = binding.signInPasswordEditText.text.toString()
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