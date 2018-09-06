package com.example.pickrestaurant.people.personDetails

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.pickrestaurant.people.model.User
import com.example.pickrestaurant.people.repositories.PeopleRepository
import javax.inject.Inject

class PersonDetailsViewModel @Inject constructor(private var peopleRepo: PeopleRepository) : ViewModel() {

    var loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    var person: MutableLiveData<User> = MutableLiveData()
    var errorMessage: MutableLiveData<Int> = MutableLiveData()

    init {
        loadingVisibility = peopleRepo.loadingVisibility
        errorMessage = peopleRepo.errorMessage
    }

    fun getPersonDetails(peopleId: Int) {
        person = peopleRepo.getPeopleDetails(peopleId)
    }
}