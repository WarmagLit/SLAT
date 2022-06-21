package com.tsu.slat.presentation.screens.client_menu.ui.choose_role

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.tsu.slat.data.entity.Role
import com.tsu.slat.databinding.ActivityChooseRoleBinding
import com.tsu.slat.presentation.screens.client_menu.ClientMenuActivity
import com.tsu.slat.presentation.screens.sign_up.SignUpViewModel

class ChooseRoleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChooseRoleBinding

    private val viewModel by viewModels<ChooseRoleViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseRoleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
        initObservers()
    }

    private fun initListeners() {
        binding.btnChooseClient.setOnClickListener {
            viewModel.chooseRole(Role.CLIENT)
            val intent = Intent(this, ClientMenuActivity::class.java)
            startActivity(intent)
        }

        binding.btnChooseTrainer.setOnClickListener {
            viewModel.chooseRole(Role.TRAINER)
            val intent = Intent(this, ClientMenuActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initObservers() {
        viewModel.intentToMenu.observe(this) {
            if (it) {
                val intent = Intent(this, ClientMenuActivity::class.java)
                startActivity(intent)
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