package br.com.qmovie.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.qmovie.R
import br.com.qmovie.viewmodel.UserViewModel
import br.com.qmovie.viewmodel.viewModelFactory
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.login_redes.*


class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel : UserViewModel
    private val TAG = "LoginActivity"

    private val signInIntent by lazy {
        GoogleSignIn.getClient(this,
            GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()).signInIntent
    }

    private val RC_SIGN_IN = 1

    private lateinit var callbackManager : CallbackManager
    private lateinit var loginManager: LoginManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        callbackManager = CallbackManager.Factory.create()
        loginManager = LoginManager.getInstance()

        viewModel = ViewModelProvider(
            this,
            viewModelFactory { UserViewModel(this, FirebaseAuth.getInstance()) }
        ).get(UserViewModel::class.java)

        btnLogin.setOnClickListener {
            viewModel.signIn(
                etLogin.text.toString(),
                etSenha.text.toString())
        }

        tvRegister.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }

        tvEsqSenha.setOnClickListener {
            viewModel.resetPassword(etLogin.text.toString())
        }

        ibGoogle.setOnClickListener {
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        ibFacebook.setOnClickListener {
            viewModel.loginWithFacebook(callbackManager, loginManager)
        }

        viewModel.user.observe(this, Observer {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCurrentUser()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            viewModel.firebaseAuthWithGoogle(data)
        }
    }

}
