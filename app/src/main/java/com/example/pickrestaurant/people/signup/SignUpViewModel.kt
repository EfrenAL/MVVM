package com.example.pickrestaurant.people.signup

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.view.View
import com.example.pickrestaurant.people.repositories.UserRepository
import javax.inject.Inject

/**
 * Created by efren.lamolda on 12.07.18.
 */
class SignUpViewModel @Inject constructor(private var userRepo: UserRepository): ViewModel() {

    private lateinit var email: String
    private lateinit var password: String
    private lateinit var name: String

    var loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    var errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { signUpUser(this.name, this.email, this.password) }
    var signUpSuccess: MutableLiveData<Boolean> = MutableLiveData()

    init {
        loadingVisibility = userRepo.loadingVisibility
        errorMessage = userRepo.errorMessage
        signUpSuccess = userRepo.loginSuccess
    }

    fun signUpUser(name:String, email: String, password: String ){
        userRepo.signUpUser(name,email,password)
    }
}