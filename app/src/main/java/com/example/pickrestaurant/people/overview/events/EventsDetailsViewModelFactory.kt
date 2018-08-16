package com.example.pickrestaurant.people.overview.events

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

/**
 * Created by efren.lamolda on 15.08.18.
 */
class EventsDetailsViewModelFactory @Inject constructor(private val eventsDetailsViewModel: EventsDetailsViewModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventsDetailsViewModel::class.java!!)) {
            return eventsDetailsViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}