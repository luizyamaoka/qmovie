package br.com.qmovie.viewmodel

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.*


class UserViewModel(
    private val activity: Activity,
    private val auth: FirebaseAuth) : ViewModel() {

    val user = MutableLiveData<FirebaseUser>()
    val error = MutableLiveData<String>()

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


}