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
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

/**
 * Created by efren on 12/07/2018.
 */
class LoginActivity : AppCompatActivity() {



    @Inject
    lateinit var loginViewModelFactory: LoginViewModelFactory
    private lateinit var viewModel: LoginViewModel

    private var errorSnackbar: Snackbar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        AndroidInjection.inject(this)

        initUi()

        viewModel = ViewModelProviders.of(this, loginViewModelFactory).get(LoginViewModel::class.java)

        viewModel.loadingVisibility.observe(this, Observer { progressBar.visibility = it!! })

        viewModel.errorMessage.observe(this, Observer { showError(it) })

        viewModel.loginSuccess.observe(this, Observer { if (it==true) startActivity(Intent(this, OverviewActivity::class.java)) })
    }

    private fun showError(errorMessage: Int?) {
        if (errorMessage!=null){
            errorSnackbar = Snackbar.make( coordinatorLayout, errorMessage, Snackbar.LENGTH_SHORT)
            //errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
            errorSnackbar?.show()
        } else {
            errorSnackbar?.dismiss()
        }
    }

    private fun initUi() {
        btn_login.setOnClickListener({
            (this?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(this.currentFocus.windowToken, 0)
            viewModel.loginUser(input_email.text.toString(), input_password.text.toString())
        })

        link_signup.setOnClickListener({
            startActivity(Intent(this, SignUpActivity::class.java))
        })
    }

}