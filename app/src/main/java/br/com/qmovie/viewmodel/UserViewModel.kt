package br.com.qmovie.viewmodel

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*


class UserViewModel(
    private val activity: Activity,
    private val auth: FirebaseAuth
) : ViewModel() {

    val user = MutableLiveData<FirebaseUser>()
    val error = MutableLiveData<String>()

    val TAG = "UserViewModel"

    fun getCurrentUser() {
        val firebaseUser = auth.currentUser
        if (firebaseUser != null)
            user.value = auth.currentUser
    }

    fun signUp(email: String, password: String, name: String) {
        when {
            email == "" -> error.value = "Formato invalido do email"
            password == "" -> error.value = "Senha nao pode ser vazia"
            name == " " -> error.value = "Preencha o nome"
            else -> {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity) { task ->
                        when (task.isSuccessful) {
                            true -> {
                                Log.d("UserViewModel", "createUserWithEmail:success")
                                val profileUpdates =
                                    UserProfileChangeRequest.Builder()
                                        .setDisplayName(name)
                                        .build()

                                auth.currentUser!!.updateProfile(profileUpdates)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Log.d("UserViewModel", "User profile updated.")
                                            getCurrentUser()
                                        }
                                    }
                            }
                            else -> {
                                Log.w("UserViewModel", "createUserWithEmail:failure", task.exception)
                                when (task.exception) {
                                    is FirebaseAuthUserCollisionException -> error.value =
                                        "Email ja cadastrado"
                                    is FirebaseAuthWeakPasswordException -> error.value =
                                        "Senha muito fraca"
                                    is FirebaseAuthInvalidCredentialsException -> error.value =
                                        "Formato invalido do email"
                                }
                            }
                        }
                    }
            }
        }
    }

    fun signIn(email: String, password: String) {
        when {
            email == "" -> error.value = "Formato invalido do email"
            password == "" -> error.value = "Senha vazia"
            else -> {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity) { task ->
                        when (task.isSuccessful) {
                            true -> {
                                Log.d("UserViewModel", "signinUserWithEmail:success")
                                getCurrentUser()
                            }
                            else -> {
                                Log.w("UserViewModel", "signinUserWithEmail:failure", task.exception)
                                when (task.exception) {
                                    is FirebaseAuthInvalidCredentialsException -> error.value =
                                        "Formato invalido do email"
                                    is FirebaseAuthInvalidUserException -> error.value =
                                        "Usuario nao encontrado"
                                }
                            }
                        }
                    }
            }
        }
    }

    fun firebaseAuthWithGoogle(data: Intent?) {

        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener(activity) { task ->
                    when (task.isSuccessful) {
                        true -> {
                            Log.d(TAG, "signInWithCredential:success")
                            getCurrentUser()
                        }
                        else -> Log.w(TAG, "signInWithCredential:failure", task.exception)
                    }
                }
        } catch (e: ApiException) {
            Log.w(TAG, "Google sign in failed", e)
        }
    }

    fun logout() {
        auth.signOut()
    }

    fun loginWithFacebook(callbackManager: CallbackManager, loginManager: LoginManager) {
        Log.i(TAG, "fblogin")

        // Set permissions
        loginManager.logInWithReadPermissions(activity, listOf("email", "public_profile"))
        loginManager.registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Log.d(TAG, "On success")
                    val credential = FacebookAuthProvider.getCredential(loginResult.accessToken.token)
                    auth.signInWithCredential(credential)
                        .addOnCompleteListener(activity) { task ->
                            when (task.isSuccessful) {
                                true -> {
                                    Log.d(TAG, "signInWithCredential:success")
                                    getCurrentUser()
                                }
                                false -> Log.w(TAG, "signInWithCredential:failure", task.exception)
                            }
                        }
                }

                override fun onCancel() {
                    Log.d(TAG, "On cancel")
                }

                override fun onError(error: FacebookException) {
                    Log.d(TAG, error.toString())
                }
            })
    }

    fun resetPassword(email: String) {
        when {
            email == "" -> error.value = "Preencha o seu email"
            else -> {
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "Email sent")
                            error.value = "Enviamos um email com instrucoes para resetar sua senha"
                        }
                    }
            }
        }
    }


}