package com.example.pickrestaurant.people.personDetails

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject


class PersonDetailsViewModelFactory @Inject constructor(private val viewModel: PersonDetailsViewModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonDetailsViewModel::class.java!!)) {
            return viewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
