package com.example.pickrestaurant.people.overview.event

import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.example.pickrestaurant.people.base.BaseViewModel
import com.example.pickrestaurant.people.model.Event
import com.example.pickrestaurant.people.repositories.EventRepository

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by efren.lamolda on 13.07.18.
 */
class EventViewModel : BaseViewModel() {

    @Inject
    lateinit var eventRepo: EventRepository


    var loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    var events: MutableLiveData<ArrayList<Event>> = MutableLiveData()
    var errorMessage: MutableLiveData<Int> = MutableLiveData()

    init {
        events = eventRepo.data
        loadingVisibility = eventRepo.loadingVisibility
        errorMessage = eventRepo.errorMessage
        getEvent("1")
    }

    private fun getEvent(userToken: String) {
        eventRepo.getEvents(userToken)
    }
}