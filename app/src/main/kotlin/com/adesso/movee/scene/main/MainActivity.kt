package com.adesso.movee.scene.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.adesso.movee.R
import com.adesso.movee.base.BaseBindingActivity
import com.adesso.movee.databinding.ActivityMainBinding
import com.adesso.movee.internal.extension.observeNonNull
import com.adesso.movee.internal.extension.showPopup
import com.adesso.movee.navigation.NavigationCommand
import com.adesso.movee.scene.introduction.IntroductionScreen

class MainActivity : BaseBindingActivity<MainViewModel, ActivityMainBinding>() {

    override val layoutId get() = R.layout.activity_main
    private lateinit var sharedPrefs: SharedPreferences

    val navController: NavController by lazy { findNavController(R.id.main_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPrefs = getPreferences(Context.MODE_PRIVATE)
        binder.viewModel = viewModel
        binder.lifecycleOwner = this

        observeNavigation()
        setupBottomNavigationView()
        listenDestinationChanges()
        if (isFirstLaunch()) {
            showIntroductionPage()
        }
    }

    private fun showIntroductionPage() {
        binder.introductionPage.setContent {
            binder.introductionPage.isVisible = true
            val skipIntroductionCallback = {
                binder.introductionPage.isVisible = false
            }
            IntroductionScreen(skipIntroductionCallback)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    private fun observeNavigation() {
        viewModel.navigation.observeNonNull(this) {
            it.getContentIfNotHandled()?.let { command ->
                handleNavigation(command)
            }
        }
    }

    private fun setupBottomNavigationView() {
        binder.mainBottomNav.setupWithNavController(navController)
    }

    private fun handleNavigation(command: NavigationCommand) {
        when (command) {
            is NavigationCommand.ToDirection -> navController.navigate(command.directions)
            is NavigationCommand.ToDeepLink -> navController.navigate(command.deepLink.toUri())
            is NavigationCommand.Popup -> showPopup(command.model, command.callback)
            is NavigationCommand.Back -> navController.navigateUp()
        }
    }

    private fun listenDestinationChanges() {
        navController.addOnDestinationChangedListener { _, _, args ->
            val visibility = if (shouldHideBottomNav(args)) View.GONE else View.VISIBLE

            if (binder.mainBottomNav.visibility != visibility) {
                binder.mainBottomNav.visibility = visibility
            }
        }
    }

    private fun shouldHideBottomNav(args: Bundle?): Boolean {
        return args?.getBoolean(getString(R.string.arg_hide_bottom_nav)) == true
    }

    private fun isFirstLaunch(): Boolean {
        val isFirstLaunch = sharedPrefs.getBoolean(KEY_FIRST_LAUNCH, true)
        if (isFirstLaunch) {
            sharedPrefs.edit().putBoolean(KEY_FIRST_LAUNCH, false).apply()
        }
        return true
    }

    companion object {
        private const val KEY_FIRST_LAUNCH = "first_launch"
        fun getStartIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}
