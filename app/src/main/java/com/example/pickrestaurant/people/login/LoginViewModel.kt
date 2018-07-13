package com.example.pickrestaurant.people.login

import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.example.pickrestaurant.people.R
import com.example.pickrestaurant.people.base.BaseViewModel
import com.example.pickrestaurant.people.base.MyApi
import com.example.pickrestaurant.people.base.UserLoginPostParameter
import com.example.pickrestaurant.people.model.User
import com.example.pickrestaurant.people.repositories.UserRepository
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

    @Inject
    lateinit var userRepo: UserRepository

    private lateinit var email: String
    private lateinit var password: String

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loginUser(this.email, this.password) }

    private lateinit var subscription: Disposable

    fun loginUser(email: String, password: String ){

        subscription = userRepo.loginUser(email,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{onRetrieveLoginStart()}
                .doOnTerminate{ onRetrieveLoginFinish()}
                .subscribe(
                        { onRetrieveLoginSuccess(it)},
                        { onRetrieveLoginError(it, email, password)}
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
        errorMessage.value = null
    }

    private fun onRetrieveLoginFinish(){
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveLoginSuccess(user: User){

    }

    private fun onRetrieveLoginError(error: Throwable, email: String, password: String){
        errorMessage.value = R.string.post_error
        //Save state of email and password to retry
        this.email = email
        this.password = password
    }

}