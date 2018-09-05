package com.example.pickrestaurant.people.repositories

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.services.s3.AmazonS3Client
import com.example.pickrestaurant.people.R
import com.example.pickrestaurant.people.base.MyApi
import com.example.pickrestaurant.people.base.UserLoginPostParameter
import com.example.pickrestaurant.people.base.UserSignUpPostParameter
import com.example.pickrestaurant.people.base.UserUpdatePutParameter
import com.example.pickrestaurant.people.model.User
import com.example.pickrestaurant.people.utils.md5
import com.example.pickrestaurant.people.utils.toFile
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by efren.lamolda on 13.07.18.
 */

@Singleton
class UserRepository @Inject constructor(private val myApi: MyApi, private val context: Context) {

    lateinit var subscription: Disposable

    var data: MutableLiveData<User> = MutableLiveData()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val loginSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val updateSuccess: MutableLiveData<Boolean> = MutableLiveData()

    private lateinit var authToken: String

    fun signUpUser(name: String, email: String, password: String) {

        if (name.isNullOrBlank() || email.isNullOrBlank() || password.isNullOrBlank()){
            onRetrieveError()
            return
        }

        subscription = myApi.signUpUser(UserSignUpPostParameter(name, email, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveStart() }
                .doOnTerminate { onRetrieveFinish() }
                .subscribe(
                        { onRetrieveSuccess(it) },
                        { onRetrieveError() }
                )
    }

    fun loginUser(email: String, password: String) {

        if (email.isNullOrBlank() || password.isNullOrBlank()){
            onRetrieveError()
            return
        }

        subscription = myApi.loginUser(UserLoginPostParameter(email, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveStart() }
                .doOnTerminate { onRetrieveFinish() }
                .subscribe(
                        { onRetrieveSuccess(it) },
                        { onRetrieveError() }
                )
    }

    private fun onRetrieveError() {
        loginSuccess.value = false
        errorMessage.value = R.string.post_error
    }

    private fun onRetrieveFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveSuccess(it: Response<User>?) {
        if (it!!.code() != 200) {
            onRetrieveError()
            return
        }
        loginSuccess.value = true
        saveDataResponse(it)
    }


    private fun onRetrieveUpdateSuccess(it: Response<User>?) {
        if (it!!.code() != 200) {
            onRetrieveError()
            return
        }

        updateSuccess.value = true
        saveDataResponse(it)
    }

    private fun saveDataResponse(it: Response<User>) {
        data.value = it!!.body()

        if (it.headers().get("Auth") != null)
            authToken = it.headers().get("Auth").toString()
    }


    fun getUserToken(): String {
        return authToken
    }

    fun updateUser(name: String, description: String, picture: String) {
        subscription = myApi.updateUser(getUserToken(), UserUpdatePutParameter(name, description, picture))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveStart() }
                .doOnTerminate { onRetrieveFinish() }
                .subscribe(
                        { onRetrieveUpdateSuccess(it) },
                        {
                            updateSuccess.value = false
                            errorMessage.value = R.string.post_error
                        }
                )
    }

    fun setImage(picture: Bitmap) {
        data.value!!.picture = picture
        var fileName = data.value!!.email.md5() + ".png"
        uploadWithTransferUtility(fileName, picture.toFile(context, fileName))
        data.postValue(data.value)
    }

    private fun uploadWithTransferUtility(remote: String, local: File) {
        val transferUtility = TransferUtility.builder()
                .context(context)
                .awsConfiguration(AWSMobileClient.getInstance().configuration)
                .s3Client(AmazonS3Client(AWSMobileClient.getInstance().credentialsProvider))
                .build()

        val uploadObserver = transferUtility.upload(remote, local)

        uploadObserver.setTransferListener(object : TransferListener {
            override fun onStateChanged(id: Int, state: TransferState) {
                if (state == TransferState.COMPLETED) {

                    var desc = if (data.value!!.description!=null) data.value!!.description  else ""
                    updateUser(data.value!!.name, desc , remote)
                }
            }

            override fun onProgressChanged(id: Int, current: Long, total: Long) {
                val done = (((current.toDouble() / total) * 100.0).toInt())
                Log.d("AWS-Tag", "ID: $id, percent done = $done")
            }

            override fun onError(id: Int, ex: Exception) {
                // Handle errors
            }
        })

        // If you prefer to poll for the data, instead of attaching a
        // listener, check for the state and progress in the observer.
        if (uploadObserver.state == TransferState.COMPLETED) {
            // Handle a completed upload.
        }
    }
}