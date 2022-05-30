package com.bangkit.capstone.nutri_a.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.capstone.nutri_a.model.User
import com.bangkit.capstone.nutri_a.utils.UserPreference
import kotlinx.coroutines.launch

class SharedViewModel(private val pref: UserPreference) : ViewModel() {
    fun getUser() : LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun saveUser(user: User) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}