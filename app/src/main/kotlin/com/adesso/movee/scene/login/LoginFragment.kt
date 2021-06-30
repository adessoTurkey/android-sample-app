package com.adesso.movee.scene.login

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            MaterialTheme {
                LoginScreen(
                    viewModel,
                    onUserNameChange = {
                        viewModel.onUserNameChange(it)
                    }, onPasswordChange = {
                        viewModel.onPasswordChange(it)
                    }
                )

            }
        }
    }

    private fun handleNavigateUriEvent(event: Event<Uri>) {
        event.getContentIfNotHandled()?.let { uri ->
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }
}

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onUserNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {
    val name: String by viewModel.username.observeAsState("")
    val password: String by viewModel.password.observeAsState("")
    var passwordVisibility by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_login_background),
            contentDescription = "Login Screen",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = dimensionResource(id = R.dimen.margin_login_image_view_movee),
                start = dimensionResource(id = R.dimen.margin_xxl),
                end = dimensionResource(id = R.dimen.margin_xxl)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.ic_movee), contentDescription = null)

        TextField(
            value = name,
            onValueChange = onUserNameChange,
            label = {
                Text(
                    text = stringResource(id = R.string.login_hint_username),
                    color = Color.White
                )
            },
            textStyle = TextStyle(color = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.margin_login_image_view_movee)),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent
            ),
        )

        TextField(
            value = password,
            onValueChange = onPasswordChange,
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(
                    text = stringResource(id = R.string.login_hint_password),
                    color = Color.White
                )
            },
            textStyle = TextStyle(color = Color.White),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent
            ),

            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisibility)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(imageVector = image, "", tint = Color.White)
                }
            }
        )

        HyperLinkView(
            text = stringResource(id = R.string.login_message_forgot_password),
            link = stringResource(id = R.string.login_forgot_password_link),
            contentAlignment = Alignment.BottomEnd,
            paddingTop = dimensionResource(id = R.dimen.margin_large),
            paddingBottom = 0.dp
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                onClick = {
                    viewModel.onLoginClick()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = colorResource(id = R.color.vibrant_blue)
                ),
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Text(text = stringResource(id = R.string.login_message_login))
            }

            HyperLinkView(
                text = stringResource(id = R.string.login_message_register),
                link = stringResource(id = R.string.login_register_link),
                contentAlignment = Alignment.Center,
                paddingTop = dimensionResource(id = R.dimen.margin_extra_large),
                paddingBottom = dimensionResource(id = R.dimen.margin_xxl)
            )
        }
    }
}

@Composable
fun HyperLinkView(
    text: String,
    link: String,
    contentAlignment: Alignment,
    paddingTop: Dp,
    paddingBottom: Dp
) {
    val hyperLinkText = buildAnnotatedString {
        append(text)
        addStringAnnotation(
            tag = "URL",
            annotation = link,
            start = 0,
            end = text.length
        )
    }

    val uriHandler = LocalUriHandler.current

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = contentAlignment) {
        ClickableText(
            modifier = Modifier
                .padding(top = paddingTop, bottom = paddingBottom),
            text = hyperLinkText,
            style = TextStyle(color = Color.White, fontSize = 12.sp),
            onClick = { offset ->
                hyperLinkText.getStringAnnotations(
                    tag = "URL", start = offset,
                    end = offset
                )
                    .firstOrNull()?.let { annotation ->
                        uriHandler.openUri(annotation.item)
                    }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        // Movie()
    }
}
