package br.com.qmovie.activity

import android.os.Bundle
import br.com.qmovie.R
import kotlinx.android.synthetic.main.activity_cadastro.*

class CadastroActivity : UserActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_cadastro)
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        btnLogin.setOnClickListener {
            val nome = etNome.text.toString()
            val sobrenome = etSobreNome.text.toString()

            viewModel.signUp(
                etEmail.text.toString(),
                etNovaSenha.text.toString(),
                "$nome $sobrenome"
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}