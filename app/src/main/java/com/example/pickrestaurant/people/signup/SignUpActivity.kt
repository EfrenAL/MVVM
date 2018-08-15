package com.example.pickrestaurant.people.signup

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.pickrestaurant.people.R
import com.example.pickrestaurant.people.login.LoginActivity
import com.example.pickrestaurant.people.overview.OverviewActivity
import com.example.pickrestaurant.people.overview.profile.ProfileFragment
import com.example.pickrestaurant.people.utils.PARENT
import com.example.pickrestaurant.people.utils.SIGNUP
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Created by efren.lamolda on 12.07.18.
 */
class SignUpActivity : AppCompatActivity(), HasSupportFragmentInjector, SignUpFragment.DataPassListener, ProfileFragment.DataPassListener {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private var profileFragment: ProfileFragment = ProfileFragment()
    private var signUpFragment: SignUpFragment = SignUpFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        AndroidInjection.inject(this)

        supportFragmentManager.beginTransaction()
                .add(R.id.fr_signup, signUpFragment, null)
                .commit()
    }


    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    override fun loginSuccess() {

        val args = Bundle()
        args.putInt(PARENT, SIGNUP)
        profileFragment.arguments = args

        supportFragmentManager.beginTransaction()
                .replace(R.id.fr_signup, profileFragment, null)
                .commit()
    }

    override fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun goToMain() {
        startActivity(Intent(this, OverviewActivity::class.java))
    }
}