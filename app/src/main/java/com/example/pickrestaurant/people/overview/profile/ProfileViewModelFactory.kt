package com.example.pickrestaurant.people.overview.profile

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

/**
 * Created by efren.lamolda on 25.07.18.
 */
class ProfileViewModelFactory @Inject constructor(private val profileViewModel: ProfileViewModel) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java!!)) {
            return profileViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}