package com.example.pickrestaurant.people.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.example.pickrestaurant.people.base.BaseViewModel
import com.example.pickrestaurant.people.model.User
import com.example.pickrestaurant.people.repositories.UserRepository
import javax.inject.Inject

/**
 * Created by efren on 12/07/2018.
 */
class LoginViewModel: BaseViewModel() {


    @Inject
    lateinit var userRepo: UserRepository

    private lateinit var email: String
    private lateinit var password: String

    var loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    var loginSuccess: MutableLiveData<Boolean> = MutableLiveData()
    var errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loginUser(this.email, this.password) }

    var user: LiveData<User>

    init {
        user = userRepo.data
        loadingVisibility = userRepo.loadingVisibility
        errorMessage = userRepo.errorMessage
        loginSuccess = userRepo.success
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
}