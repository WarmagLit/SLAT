package com.tsu.slat.presentation.screens.client_menu.ui.choose_role

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tsu.slat.data.entity.Role
import com.tsu.slat.presentation.screens.sign_in.SignInRepository

class ChooseRoleViewModel: ViewModel() {

    private val repository = SignInRepository()

    private val _intentToMenu = MutableLiveData<Boolean>()
    val intentToMenu: LiveData<Boolean>
        get() = _intentToMenu

    fun checkUserAlreadyAuthorized(toast: (stringId: Int) -> Unit) {
        val currentUser = repository.getCurrentUser()
        if (currentUser != null) {
            //toast.invoke(R.string.authorized)
            _intentToMenu.value = true
        } else {
            //toast.invoke(R.string.user_is_not_authorized)
        }
    }

    fun chooseRole(role: Role) {
        val userUid = repository.getCurrentUserId()
        repository.setUserRole(userUid, role)
    }
}