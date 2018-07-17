package com.example.pickrestaurant.people.signup

import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.example.pickrestaurant.people.R
import com.example.pickrestaurant.people.base.BaseViewModel
import com.example.pickrestaurant.people.base.MyApi
import com.example.pickrestaurant.people.model.User
import com.example.pickrestaurant.people.repositories.UserRepository
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

    @Inject
    lateinit var userRepo: UserRepository

    private lateinit var email: String
    private lateinit var password: String
    private lateinit var name: String

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { signupUser(this.name, this.email, this.password) }

    private lateinit var subscription: Disposable

    fun signupUser(name:String, email: String, password: String ){

        subscription = userRepo.signupUser(name, email, password )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{onRetrieveSingupStart()}
                .doOnTerminate{ onRetrieveSignupFinish()}
                .subscribe(
                        { onRetrieveSignupSuccess(it)},
                        { onRetrieveSignupError(it, name, email, password)}
                )


    }

    private fun onRetrieveSignupFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveSingupStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveSignupError(throwable: Throwable?, name: String, email: String, password: String) {
        errorMessage.value = R.string.post_error
        //Save state of email and password to retry
        this.email = email
        this.password = password
        this.name = name
    }

    private fun onRetrieveSignupSuccess(user: User?) {

    }
}