package com.bangkit.capstone.nutri_a.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.capstone.nutri_a.model.Auth
import com.bangkit.capstone.nutri_a.utils.UserPreference
import kotlinx.coroutines.launch

class SharedViewModel(private val pref: UserPreference) : ViewModel() {
    fun getUser() : LiveData<Auth> {
        return pref.getUser().asLiveData()
    }

    fun saveUser(auth: Auth) {
        viewModelScope.launch {
            pref.saveUser(auth)
        }
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}