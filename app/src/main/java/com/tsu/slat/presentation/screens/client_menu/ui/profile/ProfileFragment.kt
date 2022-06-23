package com.tsu.slat.presentation.screens.client_menu.ui.profile

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tsu.slat.databinding.FragmentProfileBinding
import com.tsu.slat.presentation.screens.calendar.CalendarActivity
import com.tsu.slat.presentation.screens.calendar.CalendarEventsAdapter
import com.tsu.slat.presentation.screens.client_menu.ui.choose_role.ChooseRoleViewModel
import com.tsu.slat.presentation.screens.sign_in.SignInActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<ProfileViewModel>()

    private val signOutDialog by lazy {
        AlertDialog.Builder(requireContext())
            .setMessage("Are you sure you want to log out?")
            .setNegativeButton("No", null)
            .setPositiveButton("Yes") { _, _ ->
                Firebase.auth.signOut()
                val intent = Intent(requireContext(), SignInActivity::class.java)
                startActivity(intent)
            }
            .show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnCalendar.setOnClickListener {
            val intent = Intent(requireContext(), CalendarActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignOut.setOnClickListener {
            signOutDialog.show()
        }

        initObservers()

        return root
    }

    private fun initObservers() {
        viewModel.profileName.observe(viewLifecycleOwner) {
            binding.textViewName.text = it
        }
        viewModel.profileImageUrl.observe(viewLifecycleOwner) {
            loadImageWithUri(binding.roundedImageProfile, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun loadImageWithUri(imageView: ImageView, imageUri: String){
        Glide.with(imageView.context).load(Uri.parse(imageUri)).diskCacheStrategy(
            DiskCacheStrategy.NONE).skipMemoryCache(true).into(imageView)
    }
}