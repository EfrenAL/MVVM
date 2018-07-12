package com.example.pickrestaurant.people.signup

import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.example.pickrestaurant.people.base.BaseViewModel
import com.example.pickrestaurant.people.base.MyApi
import com.example.pickrestaurant.people.base.UserSignupPostParameter
import com.example.pickrestaurant.people.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by efren.lamolda on 12.07.18.
 */
class SignupViewModel: BaseViewModel() {

    @Inject
    lateinit var myApi: MyApi

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()


    private lateinit var subscription: Disposable

    fun signupUser(name:String, email: String, password: String ){

        subscription = myApi.createUser(UserSignupPostParameter(name, email, password) )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{onRetrieveSingupStart()}
                .doOnTerminate{ onRetrieveSignupFinish()}
                .subscribe(
                        { onRetrieveSignupSuccess(it)},
                        { onRetrieveSignupError(it)}
                )


    }

    private fun onRetrieveSignupFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveSingupStart() {
        loadingVisibility.value = View.VISIBLE
    }

    private fun onRetrieveSignupError(throwable: Throwable?) {

    }

    private fun onRetrieveSignupSuccess(user: User?) {

    }



}