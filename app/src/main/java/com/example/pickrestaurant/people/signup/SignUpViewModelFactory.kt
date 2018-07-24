package com.example.pickrestaurant.people.signup

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

/**
 * Created by efren.lamolda on 24.07.18.
 */
class SignUpViewModelFactory  @Inject constructor(private val signUpViewModel: SignUpViewModel) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java!!)) {
            return signUpViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}