package com.example.pickrestaurant.people.injection.component

import com.example.pickrestaurant.people.injection.module.NetworkModule
import com.example.pickrestaurant.people.login.LoginViewModel
import com.example.pickrestaurant.people.signup.SignupViewModel
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
     * Inject dependencies into signupViewModel
     * @param signupViewModel SignupViewModel in which to inject the dependencies
     */
    fun inject(signupViewModel: SignupViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }

}