package com.example.pickrestaurant.people.overview.people

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.pickrestaurant.people.model.User
import com.example.pickrestaurant.people.repositories.PeopleRepository
import com.example.pickrestaurant.people.repositories.UserRepository
import javax.inject.Inject

/**
 * Created by efren.lamolda on 25.07.18.
 */
class PeopleViewModel @Inject constructor(private var userRepo: UserRepository, private var peopleRepo: PeopleRepository): ViewModel() {

    var loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    var people: MutableLiveData<List<User>> = MutableLiveData()
    var errorMessage: MutableLiveData<Int> = MutableLiveData()

    init {
        people = peopleRepo.data
        loadingVisibility = peopleRepo.loadingVisibility
        errorMessage = peopleRepo.errorMessage
    }

    fun getPeople(eventId: String) {
        peopleRepo.getPeople(userRepo.getUserToken(), eventId)
    }
}