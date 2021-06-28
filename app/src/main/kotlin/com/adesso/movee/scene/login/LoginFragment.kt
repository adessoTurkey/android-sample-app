package com.adesso.movee.scene.login

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.adesso.movee.R
import com.adesso.movee.base.BaseTransparentStatusBarFragment
import com.adesso.movee.databinding.FragmentLoginBinding
import com.adesso.movee.internal.extension.observeNonNull
import com.adesso.movee.internal.util.Event

class LoginFragment : BaseTransparentStatusBarFragment<LoginViewModel, FragmentLoginBinding>() {

    override val layoutId = R.layout.fragment_login

    override fun initialize() {
        super.initialize()

        viewModel.navigateUri.observeNonNull(viewLifecycleOwner, ::handleNavigateUriEvent)

        binder.composeView.setContent {
            LoginComposable()
        }
    }

    private fun handleNavigateUriEvent(event: Event<Uri>) {
        event.getContentIfNotHandled()?.let { uri ->
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    @Composable
    fun LoginComposable(loginViewModel: LoginViewModel = viewModel) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            val (
                moveeIcon,
                textFieldUsername,
                textFieldPassword,
                textForgotPassword,
                buttonLogin,
                textRegister
            ) = createRefs()

            val glContentStart =
                createGuidelineFromStart(dimensionResource(id = R.dimen.margin_xxl))
            val glContentEnd = createGuidelineFromEnd(dimensionResource(id = R.dimen.margin_xxl))

            Image(
                painter = painterResource(R.drawable.ic_login_background),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Image(
                painter = painterResource(R.drawable.ic_movee),
                contentDescription = "",
                modifier = Modifier
                    .constrainAs(moveeIcon) {
                        top.linkTo(parent.top, margin = 100.dp)
                        centerHorizontallyTo(parent)
                    }
            )

            val username by loginViewModel.username.observeAsState("")

            TextField(
                value = username,
                onValueChange = { newValue -> viewModel.username.postValue(newValue) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                placeholder = { Text(stringResource(R.string.login_hint_username)) },
                colors = textFieldColors(
                    textColor = Color.White,
                    placeholderColor = Color.White,
                    backgroundColor = Color(
                        color = android.graphics.Color.parseColor("#4cabb4bd")
                    )
                ),
                modifier = Modifier
                    .constrainAs(textFieldUsername) {
                        top.linkTo(moveeIcon.bottom, margin = 100.dp)
                        start.linkTo(glContentStart)
                        end.linkTo(glContentEnd)
                        width = Dimension.fillToConstraints
                    }
            )

            val password by viewModel.password.observeAsState("")
            var passwordVisibility by remember { mutableStateOf(false) }

            TextField(
                value = password,
                onValueChange = { newValue -> viewModel.password.postValue(newValue) },
                placeholder = { Text(stringResource(R.string.login_hint_password)) },
                visualTransformation = if (passwordVisibility) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                colors = textFieldColors(
                    textColor = Color.White,
                    placeholderColor = Color.White,
                    backgroundColor = Color(
                        color = android.graphics.Color.parseColor("#4cabb4bd")
                    )
                ),
                trailingIcon = {
                    val image = if (passwordVisibility)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    IconButton(
                        onClick = {
                            passwordVisibility = !passwordVisibility
                        }
                    ) {
                        Icon(imageVector = image, "", tint = Color.White)
                    }
                },
                modifier = Modifier
                    .constrainAs(textFieldPassword) {
                        top.linkTo(textFieldUsername.bottom)
                        start.linkTo(glContentStart)
                        end.linkTo(glContentEnd)
                        width = Dimension.fillToConstraints
                    }
            )

            Text(
                text = stringResource(R.string.login_message_forgot_password),
                fontSize = 12.sp,
                color = Color.White,
                modifier = Modifier
                    .constrainAs(textForgotPassword) {
                        top.linkTo(textFieldPassword.bottom, 16.dp)
                        end.linkTo(textFieldPassword.end)
                    }
                    .clickable { viewModel.onForgotPasswordClick() }
            )

            val loginInProgress by viewModel.loginInProgress.observeAsState()

            Button(
                enabled = loginInProgress?.not() ?: true,
                onClick = { viewModel.onLoginClick() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                modifier = Modifier
                    .constrainAs(buttonLogin) {
                        top.linkTo(textForgotPassword.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(glContentStart)
                        end.linkTo(glContentEnd)
                        width = Dimension.fillToConstraints
                    }

            ) {
                val nunitoFontFamily = FontFamily(
                    Font(R.font.nunito_bold, FontWeight.Normal)
                )

                Text(
                    stringResource(id = R.string.login_message_login),
                    color = Color(
                        color = android.graphics.Color.parseColor("#003dff")
                    ),
                    fontSize = 12.sp,
                    fontFamily = nunitoFontFamily
                )
            }

            Text(
                text = stringResource(R.string.login_message_register),
                fontSize = 12.sp,
                color = Color.White,
                modifier = Modifier
                    .constrainAs(textRegister) {
                        centerHorizontallyTo(parent)
                        top.linkTo(buttonLogin.bottom, margin = 24.dp)
                        bottom.linkTo(parent.bottom, margin = 32.dp)
                    }
                    .clickable { viewModel.onRegisterClick() }
            )
        }
    }
}
