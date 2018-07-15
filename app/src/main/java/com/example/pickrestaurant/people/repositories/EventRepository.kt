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
        list.add(Event(2,"Metal gear","Music festival 1", "testpic1"))
        list.add(Event(3,"Zadin Rock","Music festival 2", "testpic2"))
        list.add(Event(4,"Tomorrowland","Music festival 2", "testpic2"))
        list.add(Event(5,"Sonar","Music festival 2", "testpic2"))
        list.add(Event(6,"Sonardo","Music festival 2", "testpic2"))
        list.add(Event(7,"Lolo","Music festival 2", "testpic2"))
        list.add(Event(8,"Manolo","Music festival 2", "testpic2"))
        Handler().postDelayed({}, 1000)
        return Observable.fromArray(list)
    }
}