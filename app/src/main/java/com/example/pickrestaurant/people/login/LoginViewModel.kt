package com.example.pickrestaurant.people.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.view.View
import com.example.pickrestaurant.people.model.User
import com.example.pickrestaurant.people.repositories.UserRepository
import com.facebook.FacebookCallback
import com.facebook.login.LoginResult
import javax.inject.Inject

/**
 * Created by efren on 12/07/2018.
 */
class LoginViewModel @Inject constructor(private var userRepo: UserRepository): ViewModel() {

    var loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    var loginSuccess: MutableLiveData<Boolean> = MutableLiveData()
    var errorMessage: MutableLiveData<Int> = MutableLiveData()
    var user: LiveData<User> = userRepo.data

    init {
        loadingVisibility = userRepo.loadingVisibility
        errorMessage = userRepo.errorMessage
        loginSuccess = userRepo.loginSuccess
    }

    fun loginUser(email: String, password: String ){
        userRepo.loginUser(email,password)
    }

    /**
     * Dispose subscription property when the ViewModel is no longer used and will be destroyed
     */
    override fun onCleared() {
        super.onCleared()
        userRepo.subscription.dispose()
    }

    fun loginSignUpUserFacebook(): FacebookCallback<LoginResult>? {
        return userRepo.getUserTokenFacebook()
    }
}