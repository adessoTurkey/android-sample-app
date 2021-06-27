package com.adesso.movee.scene.login

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.adesso.movee.R
import com.adesso.movee.base.BaseTransparentStatusBarFragment
import com.adesso.movee.databinding.FragmentLoginBinding
import com.adesso.movee.internal.extension.observeNonNull
import com.adesso.movee.internal.util.Event

class LoginFragment : BaseTransparentStatusBarFragment<LoginViewModel, FragmentLoginBinding>() {

    override val layoutId = R.layout.fragment_login

    override fun initialize() {
        super.initialize()
        binder.composeViewLogin.setContent {
            Login()
        }
        viewModel.navigateUri.observeNonNull(viewLifecycleOwner, ::handleNavigateUriEvent)
    }

    private fun handleNavigateUriEvent(event: Event<Uri>) {
        event.getContentIfNotHandled()?.let { uri ->
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    @Composable
    fun Login() {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.ic_login_background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_movee),
                    contentDescription = null,
                    modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.margin_login_image_view_movee)
                    )
                )

                val username by viewModel.username.observeAsState("")

                TextField(
                    value = username,
                    onValueChange = { viewModel.username.postValue(it) },
                    textStyle = TextStyle(color = Color.White),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.login_hint_username),
                            color = Color.White
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = colorResource(id = R.color.cadet_blue_alpha_30),
                        backgroundColor = Color.Transparent,
                        textColor = Color.White,
                        cursorColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = dimensionResource(id = R.dimen.margin_login_image_view_movee),
                            start = dimensionResource(id = R.dimen.margin_xxl),
                            end = dimensionResource(id = R.dimen.margin_xxl)
                        ),
                )

                val password by viewModel.password.observeAsState("")
                var passwordVisibility by remember { mutableStateOf(false) }

                TextField(
                    value = password,
                    visualTransformation =
                    if (passwordVisibility) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    onValueChange = { viewModel.password.postValue(it) },
                    textStyle = TextStyle(color = Color.White),
                    trailingIcon = {
                        val image = if (passwordVisibility)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        IconButton(
                            onClick = {
                                passwordVisibility = !passwordVisibility
                            }
                        ) {
                            Icon(imageVector = image, null, tint = Color.White)
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Password
                    ),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.login_hint_password),
                            color = Color.White
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = colorResource(id = R.color.cadet_blue_alpha_30),
                        backgroundColor = Color.Transparent,
                        textColor = Color.White,
                        cursorColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = dimensionResource(id = R.dimen.margin_xxl),
                            end = dimensionResource(id = R.dimen.margin_xxl)
                        ),
                )

                Text(
                    stringResource(id = R.string.login_message_forgot_password),
                    color = Color.White,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = dimensionResource(id = R.dimen.margin_large),
                            start = dimensionResource(id = R.dimen.margin_xxl),
                            end = dimensionResource(id = R.dimen.margin_xxl)
                        )
                )

                Button(
                    onClick = {
                        Toast.makeText(context, "Login button", Toast.LENGTH_LONG).show()
                    },
                    enabled = true,
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.margin_xxl),
                            end = dimensionResource(id = R.dimen.margin_xxl),
                        )

                ) {
                    Text(
                        text = stringResource(id = R.string.login_message_login),
                        color = colorResource(id = R.color.vibrant_blue)
                    )
                }
            }
        }
    }

    @Preview
    @Composable
    fun LoginPreview() {
        Login()
    }
}
