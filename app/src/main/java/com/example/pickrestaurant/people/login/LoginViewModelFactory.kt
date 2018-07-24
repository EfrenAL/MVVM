package com.example.pickrestaurant.people.login

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

/**
 * Created by efren.lamolda on 24.07.18.
 */
class LoginViewModelFactory @Inject constructor(private val loginViewModel: LoginViewModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java!!)) {
            return loginViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
