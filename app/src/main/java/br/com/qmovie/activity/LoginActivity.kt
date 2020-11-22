package br.com.qmovie.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import br.com.qmovie.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.login_redes.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener(){
            val intent = Intent(this@LoginActivity, JogoActivity::class.java)
            startActivity(intent)
        }

        tvRegister.setOnClickListener(){
            //ira para a tela de registro
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
}
