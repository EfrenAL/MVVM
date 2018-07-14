package com.example.pickrestaurant.people.overview.event

import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.example.pickrestaurant.people.base.BaseViewModel
import com.example.pickrestaurant.people.model.Event
import com.example.pickrestaurant.people.repositories.EventRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by efren.lamolda on 13.07.18.
 */
class EventViewModel: BaseViewModel() {

    private lateinit var subscription: Disposable

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val events: MutableLiveData<ArrayList<Event>> = MutableLiveData()

    init {
        loadEvent("1")
    }


    fun loadEvent(userId: String){

        var events: EventRepository = EventRepository()
        subscription = events.getEvents("1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{onRetrieveLoginStart()}
                .doOnTerminate{ onRetrieveLoginFinish()}
                .subscribe(
                        { onRetrieveLoginSuccess(it)},
                        { onRetrieveLoginError()}
                )
    }

    private fun onRetrieveLoginStart(){
        loadingVisibility.value = View.VISIBLE
    }

    private fun onRetrieveLoginFinish(){
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveLoginSuccess(eventsList: ArrayList<Event>) {
        events.value = eventsList
    }

    private fun onRetrieveLoginError(){}



}