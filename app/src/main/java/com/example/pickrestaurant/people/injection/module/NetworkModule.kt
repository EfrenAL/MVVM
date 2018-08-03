package com.example.pickrestaurant.people.injection.module

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.example.pickrestaurant.people.base.MyApi
import com.example.pickrestaurant.people.login.LoginViewModelFactory
import com.example.pickrestaurant.people.overview.event.EventViewModelFactory
import com.example.pickrestaurant.people.overview.people.PeopleViewModelFactory
import com.example.pickrestaurant.people.overview.profile.ProfileViewModelFactory
import com.example.pickrestaurant.people.repositories.EventRepository
import com.example.pickrestaurant.people.repositories.UserRepository
import com.example.pickrestaurant.people.signup.SignUpViewModelFactory
import com.example.pickrestaurant.people.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created by efren on 12/07/2018.
 */

@Module
@Suppress("unused")
object NetworkModule {


    /**
     * Provides the MyApo service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the Post service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun providePostApi(retrofit: Retrofit): MyApi {
        return retrofit.create(MyApi::class.java)
    }

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
    }

    /**
     * Provides user repo.
     * @return user repository
     */
    @Provides
    @JvmStatic
    @Singleton
    internal fun provideUserRepository(myApi: MyApi, context: Context): UserRepository {
        return UserRepository(myApi, context)
    }

    /**
     * Provides event repo.
     * @return event repository
     */
    @Provides
    @JvmStatic
    @Singleton
    internal fun provideEventRepository(myApi: MyApi): EventRepository {
        return EventRepository(myApi)
    }

    @Provides
    fun provideLoginViewModelFactory(factory: LoginViewModelFactory): ViewModelProvider.Factory = factory

    @Provides
    fun provideEventViewModelFactory(factory: EventViewModelFactory): ViewModelProvider.Factory = factory

    @Provides
    fun provideSignUpViewModelFactory(factory: SignUpViewModelFactory): ViewModelProvider.Factory = factory

    @Provides
    fun providePeopleViewModelFactory(factory: PeopleViewModelFactory): ViewModelProvider.Factory = factory

    @Provides
    fun provideProfileViewModelFactory(factory: ProfileViewModelFactory): ViewModelProvider.Factory = factory
}