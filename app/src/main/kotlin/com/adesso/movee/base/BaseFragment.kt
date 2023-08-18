package com.adesso.movee.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.adesso.movee.BR
import com.adesso.movee.internal.extension.observeNonNull
import com.adesso.movee.internal.extension.showPopup
import com.adesso.movee.navigation.NavigationCommand
import com.adesso.movee.scene.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VM : BaseAndroidViewModel, B : ViewDataBinding> : Fragment() {

    protected lateinit var binder: B

    @get:LayoutRes
    abstract val layoutId: Int

    open fun initialize() {}

    @Suppress("UNCHECKED_CAST")
    protected open val viewModel: VM
        get() {
            val persistentViewModelClass =
                (javaClass.genericSuperclass as ParameterizedType)
                    .actualTypeArguments[0] as Class<VM>
            val lazyVM = createViewModelLazy(
                viewModelClass = persistentViewModelClass.kotlin,
                storeProducer = { viewModelStore },
                factoryProducer = {
                    (this as? HasDefaultViewModelProviderFactory)?.defaultViewModelProviderFactory
                        ?: defaultViewModelProviderFactory
                }
            )
            return lazyVM.value
        }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeNavigation()
        observeFailure()
        observeSuccess()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binder = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binder.lifecycleOwner = viewLifecycleOwner
        binder.setVariable(BR.viewModel, viewModel)
        initialize()

        return binder.root
    }

    private fun observeNavigation() {
        viewModel.navigation.observeNonNull(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { command ->
                handleNavigation(command)
            }
        }
    }

    protected open fun handleNavigation(command: NavigationCommand) {
        when (command) {
            is NavigationCommand.ToDirection -> {
                findNavController().navigate(command.directions, getExtras())
            }

            is NavigationCommand.ToDeepLink -> {
                (activity as? MainActivity)?.navController?.navigate(
                    command.deepLink.toUri(), null, getExtras()
                )
            }

            is NavigationCommand.Popup -> {
                with(command) {
                    context?.showPopup(model, callback)
                }
            }

            is NavigationCommand.Back -> findNavController().navigateUp()
        }
    }

    private fun observeFailure() {
        viewModel.failurePopup.observeNonNull(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { popupUiModel ->
                context?.showPopup(popupUiModel)
            }
        }
    }

    private fun observeSuccess() {
        viewModel.success.observeNonNull(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { message ->
                showSnackBarMessage(message)
            }
        }
    }

    private fun showSnackBarMessage(message: String) {
        Snackbar.make(binder.root, message, Snackbar.LENGTH_LONG).show()
    }

    open fun getExtras(): FragmentNavigator.Extras = FragmentNavigatorExtras()
}
