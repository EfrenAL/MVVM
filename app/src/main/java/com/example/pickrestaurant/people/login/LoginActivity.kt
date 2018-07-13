package com.example.pickrestaurant.people.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.example.pickrestaurant.people.R
import com.example.pickrestaurant.people.signup.SignupActivity
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by efren on 12/07/2018.
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private var errorSnackbar: Snackbar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initUi()

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        viewModel.loadingVisibility.observe(this, Observer { progressBar.visibility = it!! })

        viewModel.errorMessage.observe(this, Observer { showError(it) })
    }

    private fun showError(errorMessage: Int?) {
        if (errorMessage!=null){
            errorSnackbar = Snackbar.make( coordinatorLayout, errorMessage, Snackbar.LENGTH_INDEFINITE)
            errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
            errorSnackbar?.show()
        } else {
            errorSnackbar?.dismiss()
        }
    }

    private fun initUi() {
        btn_login.setOnClickListener({
            viewModel.loginUser(input_email.text.toString(), input_password.text.toString())
        })

        link_signup.setOnClickListener({
            startActivity(Intent(this, SignupActivity::class.java))
        })
    }

}