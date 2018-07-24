package com.example.pickrestaurant.people.injection.module

import com.example.pickrestaurant.people.login.LoginActivity
import com.example.pickrestaurant.people.overview.OverviewActivity
import com.example.pickrestaurant.people.signup.SignUpActivity
import com.example.pickrestaurant.people.signup.SignUpActivity_MembersInjector
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by efren.lamolda on 24.07.18.
 */
@Module
abstract class BuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    abstract fun contributeOverviewActivity(): OverviewActivity

    @ContributesAndroidInjector
    abstract fun contributeSignUpActivity(): SignUpActivity
}