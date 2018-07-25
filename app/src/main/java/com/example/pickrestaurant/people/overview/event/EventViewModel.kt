package com.example.pickrestaurant.people.overview.event

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import com.example.pickrestaurant.people.model.Event
import com.example.pickrestaurant.people.repositories.EventRepository
import com.example.pickrestaurant.people.repositories.UserRepository
import javax.inject.Inject

/**
 * Created by efren.lamolda on 13.07.18.
 */
class EventViewModel @Inject constructor(private var userRepo: UserRepository, private var eventRepo: EventRepository) : ViewModel() {

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

    fun postEvent(eventCode: String){
        eventRepo.postEvent(userRepo.getUserToken(), eventCode)
    }
}