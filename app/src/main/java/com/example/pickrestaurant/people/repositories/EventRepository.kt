package com.example.pickrestaurant.people.repositories

import com.example.pickrestaurant.people.base.MyApi
import com.example.pickrestaurant.people.model.Event
import io.reactivex.Observable
import javax.inject.Singleton


/**
 * Created by efren.lamolda on 13.07.18.
 */

@Singleton
class EventRepository(private val myApi: MyApi) {


    private lateinit var eventList: ArrayList<Event>

    fun getEvents(userToken: String): Observable<ArrayList<Event>> {
        return myApi.getEvents(userToken)
    }
}