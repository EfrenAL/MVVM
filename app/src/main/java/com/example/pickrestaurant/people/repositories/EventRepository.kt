package com.example.pickrestaurant.people.repositories

import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.example.pickrestaurant.people.R
import com.example.pickrestaurant.people.base.EventsResponse
import com.example.pickrestaurant.people.base.MyApi
import com.example.pickrestaurant.people.model.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton


/**
 * Created by efren.lamolda on 13.07.18.
 */

@Singleton
class EventRepository(private val myApi: MyApi) {


    var data: MutableLiveData<List<Event>> = MutableLiveData()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val success: MutableLiveData<Boolean> = MutableLiveData()

    lateinit var subscription: Disposable


    fun getEvents(userToken: String) {

        subscription = myApi.getEvents(userToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{onRetrieveStart()}
                .doOnTerminate{ onRetrieveFinish()}
                .subscribe(
                        { onRetrieveSuccess(it)},
                        { onRetrieveError(it)}
                )
    }

    private fun onRetrieveError(it: Throwable?) {
        success.value = false
        errorMessage.value = R.string.post_error
    }

    private fun onRetrieveFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveSuccess(events: EventsResponse) {
        data.value = events!!.events
        success.value = true
    }
}