package com.example.pickrestaurant.people.base

import android.arch.lifecycle.ViewModel
import com.example.pickrestaurant.people.injection.component.DaggerViewModelInjector
import com.example.pickrestaurant.people.injection.component.ViewModelInjector
import com.example.pickrestaurant.people.injection.module.NetworkModule
import com.example.pickrestaurant.people.login.LoginViewModel
import com.example.pickrestaurant.people.overview.event.EventViewModel
import com.example.pickrestaurant.people.signup.SignUpViewModel

/**
 * Created by efren on 12/07/2018.
 */
abstract class BaseViewModel: ViewModel() {

    private val injector: ViewModelInjector = DaggerViewModelInjector
            .builder()
            .networkModule(NetworkModule)
            .build()

    init {
        inject()
    }

    private fun inject(){
        when (this) {
            is LoginViewModel -> injector.inject(this)
            is SignUpViewModel -> injector.inject(this)
            is EventViewModel -> injector.inject(this)
        }
    }

}