package com.example.pickrestaurant.people.repositories

import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.example.pickrestaurant.people.R
import com.example.pickrestaurant.people.base.MyApi
import com.example.pickrestaurant.people.base.UserLoginPostParameter
import com.example.pickrestaurant.people.base.UserSignUpPostParameter
import com.example.pickrestaurant.people.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by efren.lamolda on 13.07.18.
 */

@Singleton
class UserRepository @Inject constructor(private val myApi: MyApi) {

    lateinit var subscription: Disposable

    var data: MutableLiveData<User> = MutableLiveData()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val success: MutableLiveData<Boolean> = MutableLiveData()

    private lateinit var email: String
    private lateinit var name: String
    private lateinit var password: String

    fun signUpUser(name: String, email: String, password: String){

        subscription = myApi.signUpUser(UserSignUpPostParameter(name, email, password ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{onRetrieveStart()}
                .doOnTerminate{ onRetrieveFinish()}
                .subscribe(
                        { onRetrieveSuccess(it)},
                        { onRetrieveError(it, name, email, password)}
                )
    }

    fun loginUser(email:String, password: String) {

        subscription = myApi.loginUser(UserLoginPostParameter(email,password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{onRetrieveStart()}
                .doOnTerminate{ onRetrieveFinish()}
                .subscribe(
                        { onRetrieveSuccess(it)},
                        { onRetrieveError(it,"" , email, password)}
                )
    }

    private fun onRetrieveError(it: Throwable?, name: String, email: String, password: String) {
        success.value = false
        errorMessage.value = R.string.post_error
        //Save state of email and password to retry
        this.email = email
        this.password = password
        this.name = name
    }

    private fun onRetrieveFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveSuccess(it: Response<User>?) {
        data.value = it!!.body()
        data.value!!.authToken = it.headers().get("Auth")?: ""
        success.value = true
    }

    fun getUserToken(): String{
        return data.value!!.authToken
    }

}