package com.example.pickrestaurant.people.overview.profile

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.pickrestaurant.people.model.User
import com.example.pickrestaurant.people.repositories.UserRepository
import javax.inject.Inject

/**
 * Created by efren.lamolda on 26.07.18.
 */
class ProfileViewModel @Inject constructor(private var userRepo: UserRepository): ViewModel() {

    var loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    var loginSuccess: MutableLiveData<Boolean> = MutableLiveData()
    var errorMessage: MutableLiveData<Int> = MutableLiveData()
    var user: LiveData<User>

    init {
        user = userRepo.data
        loadingVisibility = userRepo.loadingVisibility
        errorMessage = userRepo.errorMessage
        loginSuccess = userRepo.success
    }

    fun updateUser(name: String, description: String ){
        userRepo.updateUser(name, description)
    }

    /**
     * Dispose subscription property when the ViewModel is no longer used and will be destroyed
     */
    override fun onCleared() {
        super.onCleared()
        userRepo.subscription.dispose()
    }
}