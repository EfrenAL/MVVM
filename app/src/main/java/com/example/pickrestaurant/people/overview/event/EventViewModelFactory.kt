package com.example.pickrestaurant.people.overview.event

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

/**
 * Created by efren.lamolda on 24.07.18.
 */
class EventViewModelFactory @Inject constructor(private val eventViewModel: EventViewModel) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventViewModel::class.java!!)) {
            return eventViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}