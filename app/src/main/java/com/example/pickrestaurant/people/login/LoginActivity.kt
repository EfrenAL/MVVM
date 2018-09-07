package com.example.pickrestaurant.people.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import com.example.pickrestaurant.people.R
import com.example.pickrestaurant.people.overview.OverviewActivity
import com.example.pickrestaurant.people.signup.SignUpActivity
import com.facebook.*
import com.facebook.login.LoginResult
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import java.util.*
import javax.inject.Inject


/**
 * Created by efren on 12/07/2018.
 */
class LoginActivity : AppCompatActivity() {


    @Inject
    lateinit var loginViewModelFactory: LoginViewModelFactory
    private lateinit var viewModel: LoginViewModel

    private var errorSnackbar: Snackbar? = null
    private lateinit var callbackManager: CallbackManager
    private var mAccessToken: AccessToken? = null


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        AndroidInjection.inject(this)

        callbackManager = CallbackManager.Factory.create()

        initUi()

        viewModel = ViewModelProviders.of(this, loginViewModelFactory).get(LoginViewModel::class.java)

        viewModel.loadingVisibility.observe(this, Observer { progressBar.visibility = it!! })

        viewModel.errorMessage.observe(this, Observer { showError(it) })

        viewModel.loginSuccess.observe(this, Observer { if (it == true) startActivity(Intent(this, OverviewActivity::class.java)) })
    }


    private fun showError(errorMessage: Int?) {
        if (errorMessage != null) {
            errorSnackbar = Snackbar.make(coordinatorLayout, errorMessage, Snackbar.LENGTH_SHORT)
            //errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
            errorSnackbar?.show()
        } else {
            errorSnackbar?.dismiss()
        }
    }

    private fun initUi() {
        btn_login.setOnClickListener {
            (this?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(this.currentFocus.windowToken, 0)
            viewModel.loginUser(input_email.text.toString(), input_password.text.toString())
        }

        link_signup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        btn_fb.setReadPermissions(Arrays.asList("email", "public_profile"))
        btn_fb.authType = "rerequest" //Check what is it

        btn_fb.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                println("FB: SUCCESS!!")
                mAccessToken = loginResult.accessToken
                getUserProfile(loginResult.accessToken)
            }

            override fun onCancel() {
                println("FB: CANCELED!!")
                setResult(RESULT_CANCELED)
                finish()
            }

            override fun onError(exception: FacebookException) {
                println("FB: ERROR!!")
            }
        })
    }

    fun getUserProfile(currentAccessToken: AccessToken) {
        println("FB: GET PROFILE!!")
        println("FB: Token:" + currentAccessToken.token)

        var request: GraphRequest = GraphRequest.newMeRequest(currentAccessToken) { obj: JSONObject, response: GraphResponse -> printValues(response, obj)}

        val parameters = Bundle()
        parameters.putString("fields", "id,name,email,picture,birthday")
        request.parameters = parameters
        request.executeAsync()

        println("FB: successful" + request.accessToken)
    }

    private fun printValues(graphResponse: GraphResponse, obj: JSONObject) {
        println("FB: " + obj.toString())
        println("FB: " + obj.getString("email"))
    }

}