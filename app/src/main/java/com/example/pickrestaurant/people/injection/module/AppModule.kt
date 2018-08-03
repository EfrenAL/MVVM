package com.example.pickrestaurant.people.injection.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by efren.lamolda on 24.07.18.
 */
@Module
class AppModule(val app: Application, val context:Context) {
    @Provides
    @Singleton
    fun provideApplication(): Application = app

    @Provides
    @Singleton
    fun provideContext(): Context = context
}