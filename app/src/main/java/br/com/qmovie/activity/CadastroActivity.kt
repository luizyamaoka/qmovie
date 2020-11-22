package br.com.qmovie.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.qmovie.R
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.btnLogin
import kotlinx.android.synthetic.main.login_redes.*

class CadastroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        btnLogin.setOnClickListener(){
            val intent = Intent(this@CadastroActivity, MainActivity::class.java)
            startActivity(intent)
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
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}