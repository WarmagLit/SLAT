package com.tsu.slat.presentation.screens.sign_up

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.tsu.slat.R
import com.tsu.slat.databinding.ActivitySignInBinding
import com.tsu.slat.databinding.ActivitySignUpBinding
import com.tsu.slat.presentation.screens.chat.ChatActivity
import com.tsu.slat.presentation.screens.client_menu.ClientMenuActivity
import com.tsu.slat.presentation.screens.sign_in.SignInViewModel

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
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

        binding.btnStart.setOnClickListener {

            viewModel.signUp(
                email = binding.editEmail.text.toString(),
                password = binding.editPass.text.toString(),
                nickname = binding.editName.text.toString()
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