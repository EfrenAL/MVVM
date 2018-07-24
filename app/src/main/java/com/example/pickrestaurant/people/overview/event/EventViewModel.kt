package com.example.pickrestaurant.people.overview.event

import android.arch.lifecycle.MutableLiveData
import com.example.pickrestaurant.people.base.BaseViewModel
import com.example.pickrestaurant.people.model.Event
import com.example.pickrestaurant.people.repositories.EventRepository
import com.example.pickrestaurant.people.repositories.UserRepository
import javax.inject.Inject

/**
 * Created by efren.lamolda on 13.07.18.
 */
class EventViewModel : BaseViewModel() {

    @Inject
    lateinit var userRepo: UserRepository

    @Inject
    lateinit var eventRepo: EventRepository



    var loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    var events: MutableLiveData<List<Event>> = MutableLiveData()
    var errorMessage: MutableLiveData<Int> = MutableLiveData()

    init {
        events = eventRepo.data
        loadingVisibility = eventRepo.loadingVisibility
        errorMessage = eventRepo.errorMessage
        getEvent(userRepo.getUserToken()) //User repository is instantiated again is not a real singleton!!
    }

    private fun getEvent(userToken: String) {
        eventRepo.getEvents(userToken)
    }
}