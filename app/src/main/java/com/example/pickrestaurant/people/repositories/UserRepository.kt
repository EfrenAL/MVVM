package com.example.pickrestaurant.people.repositories

import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.example.pickrestaurant.people.R
import com.example.pickrestaurant.people.base.MyApi
import com.example.pickrestaurant.people.base.UserLoginPostParameter
import com.example.pickrestaurant.people.base.UserSignupPostParameter
import com.example.pickrestaurant.people.model.User
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Singleton

/**
 * Created by efren.lamolda on 13.07.18.
 */

@Singleton
class UserRepository(private val myApi: MyApi) {



    lateinit var subscription: Disposable

    var data: MutableLiveData<User> = MutableLiveData()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val loginSuccess: MutableLiveData<Boolean> = MutableLiveData()
    private lateinit var email: String
    private lateinit var password: String


    fun signupUser(name: String, email: String, password: String): Observable<User>{
        return myApi.createUser(UserSignupPostParameter(name,email,password))
    }

    fun loginUser(email:String, password: String) {

        subscription = myApi.loginUser(UserLoginPostParameter(email,password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{onRetrieveLoginStart()}
                .doOnTerminate{ onRetrieveLoginFinish()}
                .subscribe(
                        { onRetrieveLoginSuccess(it)},
                        { onRetrieveLoginError(it, email, password)}
                )
    }

    private fun onRetrieveLoginError(it: Throwable?, email: String, password: String) {
        loginSuccess.value = false
        errorMessage.value = R.string.post_error
        //Save state of email and password to retry
        this.email = email
        this.password = password
    }

    private fun onRetrieveLoginFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveLoginStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveLoginSuccess(it: Response<User>?) {
        data.value = it!!.body()
        data.value!!.authToken = it.headers().get("Auth")?: ""
        loginSuccess.value = true
    }

}