package com.example.pickrestaurant.people.repositories

import android.os.Handler
import com.example.pickrestaurant.people.model.Event
import io.reactivex.Observable


/**
 * Created by efren.lamolda on 13.07.18.
 */
class EventRepository {

    fun getEvents(userId: String): Observable<ArrayList<Event>> {

        var list: ArrayList<Event> = ArrayList()
        list.add(Event(1,"Lollapalooza","Music festival", "testpic"))
        list.add(Event(2,"Lollapalooza 2","Music festival 1", "testpic1"))
        list.add(Event(3,"Lollapalooza 3","Music festival 2", "testpic2"))
        Handler().postDelayed({}, 1000)
        return Observable.fromArray(list)
    }
}