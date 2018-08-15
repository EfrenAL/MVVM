package com.example.pickrestaurant.people.overview.profile

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.graphics.Bitmap
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
    var updateSuccess: MutableLiveData<Boolean> = MutableLiveData()

    init {
        user = userRepo.data
        loadingVisibility = userRepo.loadingVisibility
        errorMessage = userRepo.errorMessage
        updateSuccess = userRepo.updateSuccess
    }

    fun updateUser(name: String, description: String, picture: String ){
        userRepo.updateUser(name, description, picture)
    }

    /**
     * Dispose subscription property when the ViewModel is no longer used and will be destroyed
     */
    override fun onCleared() {
        super.onCleared()
        userRepo.subscription.dispose()
    }

    fun setImage(picture: Bitmap){
        userRepo.setImage(picture)
    }
}