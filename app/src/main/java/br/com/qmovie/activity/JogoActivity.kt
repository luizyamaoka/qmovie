package br.com.qmovie.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import br.com.qmovie.R
import br.com.qmovie.domain.TipoJogo

class JogoActivity : AppCompatActivity() {

    lateinit var tipoJogo: TipoJogo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogo)
        tipoJogo = intent.getSerializableExtra("tipoJogo") as TipoJogo
    }
}