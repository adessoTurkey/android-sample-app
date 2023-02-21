package com.adesso.movee.scene.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.adesso.movee.R
import com.adesso.movee.base.BaseBindingActivity
import com.adesso.movee.databinding.ActivityMainBinding
import com.adesso.movee.internal.extension.showPopup
import com.adesso.movee.internal.util.EventObserver
import com.adesso.movee.navigation.NavigationCommand

class MainActivity : BaseBindingActivity<MainViewModel, ActivityMainBinding>() {

    override val layoutId get() = R.layout.activity_main

    val navController: NavController by lazy { findNavController(R.id.main_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binder.viewModel = viewModel
        binder.lifecycleOwner = this

        observeNavigation()
        setupBottomNavigationView()
        listenDestinationChanges()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    private fun observeNavigation() {
        viewModel.navigation.observe(this, navigation)
    }
    private val navigation = EventObserver<NavigationCommand> {
        handleNavigation(it)
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

    companion object {
        fun getStartIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}
