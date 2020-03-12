package com.example.movee.scene.splash

import android.os.Bundle
import com.example.movee.base.BaseActivity
import com.example.movee.scene.main.MainActivity

class SplashActivity : BaseActivity<SplashViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startMainActivity()
    }

    private fun startMainActivity() {
        startActivity(MainActivity.getStartIntent(this))
        finish()
    }
}