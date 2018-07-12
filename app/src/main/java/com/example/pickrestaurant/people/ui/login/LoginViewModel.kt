package com.example.pickrestaurant.people.ui.login

import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.example.pickrestaurant.people.base.BaseViewModel
import com.example.pickrestaurant.people.base.MyApi
import com.example.pickrestaurant.people.base.UserLoginPostParameter
import com.example.pickrestaurant.people.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by efren on 12/07/2018.
 */
class LoginViewModel: BaseViewModel() {

    @Inject
    lateinit var myApi: MyApi

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()


    private lateinit var subscription: Disposable

    fun loginUser(email: String, password: String ){

        subscription = myApi.loginUser(UserLoginPostParameter(email, password) )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{onRetrieveLoginStart()}
                .doOnTerminate{ onRetrieveLoginFinish()}
                .subscribe(
                        { onRetrieveLoginSuccess(it)},
                        { onRetrieveLoginError(it)}
                )


    }

    /**
     * Dispose subscription property when the ViewModel is no longer used and will be destroyed
     */
    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun onRetrieveLoginStart(){
        loadingVisibility.value = View.VISIBLE
    }

    private fun onRetrieveLoginFinish(){
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveLoginSuccess(user: User){

    }

    private fun onRetrieveLoginError(error: Throwable){

    }

}