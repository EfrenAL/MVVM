package com.example.pickrestaurant.people.overview.people

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

/**
 * Created by efren.lamolda on 25.07.18.
 */
class PeopleViewModelFactory @Inject constructor(private val peopleViewModel: PeopleViewModel) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PeopleViewModel::class.java!!)) {
            return peopleViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}