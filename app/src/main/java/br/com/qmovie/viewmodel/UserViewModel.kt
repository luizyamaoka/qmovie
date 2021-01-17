package br.com.qmovie.viewmodel

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*


class UserViewModel(
    private val activity: Activity,
    private val auth: FirebaseAuth) : ViewModel() {

    val user = MutableLiveData<FirebaseUser>()
    val error = MutableLiveData<String>()

    val TAG = "UserViewModel"

    fun getCurrentUser() {
        val firebaseUser = auth.currentUser
        if (firebaseUser != null)
            user.value = auth.currentUser
    }

    fun signUp(email: String, password: String, name: String) {
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
                            is FirebaseAuthUserCollisionException -> error.value = "Email ja cadastrado"
                            is FirebaseAuthWeakPasswordException -> error.value = "Senha muito fraca"
                            is FirebaseAuthInvalidCredentialsException -> error.value = "Formato invalido do email"
                        }
                    }
                }
            }
    }

    fun signIn(email: String, password: String) {
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
                            is FirebaseAuthInvalidCredentialsException -> error.value = "Formato invalido do email"
                            is FirebaseAuthInvalidUserException ->  error.value = "Usuario nao encontrado"
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


}