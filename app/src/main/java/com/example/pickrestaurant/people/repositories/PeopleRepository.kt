package com.example.pickrestaurant.people.repositories

import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.example.pickrestaurant.people.R
import com.example.pickrestaurant.people.base.MyApi
import com.example.pickrestaurant.people.base.PeopleResponse
import com.example.pickrestaurant.people.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by efren.lamolda on 25.07.18.
 */
@Singleton
class PeopleRepository @Inject constructor(private val myApi: MyApi) {

    lateinit var subscription: Disposable

    var data: MutableLiveData<List<User>> = MutableLiveData()
    var loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    var errorMessage: MutableLiveData<Int> = MutableLiveData()


    fun getPeople(userToken: String, eventId: String) {
        subscription = myApi.getPeople(userToken, eventId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{onRetrieveStart()}
                .doOnTerminate{ onRetrieveFinish()}
                .subscribe(
                        { onRetrieveSuccess(it)},
                        { onRetrieveError(it)}
                )

    }

    private fun onRetrieveError(it: Throwable?) {
        errorMessage.value = R.string.post_error
    }

    private fun onRetrieveFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveSuccess(peopleResponse: Response<PeopleResponse>?) {
        data.value = peopleResponse!!.body()!!.users
    }

}