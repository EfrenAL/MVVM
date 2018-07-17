package com.example.pickrestaurant.people.injection.component

import com.example.pickrestaurant.people.injection.module.NetworkModule
import com.example.pickrestaurant.people.login.LoginViewModel
import com.example.pickrestaurant.people.overview.event.EventViewModel
import com.example.pickrestaurant.people.signup.SignUpViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Component providing inject() methods for view model.
 * Created by efren on 12/07/2018.
 */

@Singleton
@Component(modules = arrayOf(NetworkModule::class))
interface ViewModelInjector {

    /**
     * Inject dependencies into LoginViewModel
     * @param loginViewModel loginViewModel in which to inject the dependencies
     */
    fun inject(loginViewModel: LoginViewModel)

    /**
     * Inject dependencies into signUpViewModel
     * @param signUpViewModel SignUpViewModel in which to inject the dependencies
     */
    fun inject(signUpViewModel: SignUpViewModel)

    /**
     * Inject dependencies into signupViewModel
     * @param signupViewModel SignUpViewModel in which to inject the dependencies
     */
    fun inject(eventViewModel: EventViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }

}