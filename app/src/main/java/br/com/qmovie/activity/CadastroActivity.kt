package br.com.qmovie.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.qmovie.R
import br.com.qmovie.viewmodel.UserViewModel
import br.com.qmovie.viewmodel.viewModelFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.btnLogin
import kotlinx.android.synthetic.main.login_redes.*

class CadastroActivity : AppCompatActivity() {

    private lateinit var viewModel : UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        viewModel = ViewModelProvider(
            this,
            viewModelFactory { UserViewModel(this, FirebaseAuth.getInstance()) }
        ).get(UserViewModel::class.java)

        btnLogin.setOnClickListener(){
            val nome = etNome.text.toString()
            val sobrenome = etSobreNome.text.toString()

            viewModel.signUp(
                etEmail.text.toString(),
                etNovaSenha.text.toString(),
                "$nome $sobrenome"
            )
        }

        ibGoogle.setOnClickListener(){
            //iremos fazer o login pelo google
        }

        ibOutlook.setOnClickListener(){
            //iremos fazer login pelo Outlook
        }

        ibFacebook.setOnClickListener(){
            //iremos fazer o login pelo Facebook
        }

        ibApple.setOnClickListener(){
            //iremos fazer o login pela Apple
        }

        viewModel.user.observe(this, Observer {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("user", it)
            startActivity(intent)
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.getCurrentUser()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}