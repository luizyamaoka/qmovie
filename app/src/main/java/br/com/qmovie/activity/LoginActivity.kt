package br.com.qmovie.activity

import android.content.Intent
import android.os.Bundle
import br.com.qmovie.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : UserActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_login)
        super.onCreate(savedInstanceState)

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
    }

}
