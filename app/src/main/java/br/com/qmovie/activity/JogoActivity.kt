package br.com.qmovie.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import br.com.qmovie.R
import br.com.qmovie.domain.Jogo
import br.com.qmovie.domain.TipoJogo
import br.com.qmovie.service.movieService
import br.com.qmovie.viewmodel.JogoViewModel

class JogoActivity : AppCompatActivity() {

//    lateinit var tipoJogo: TipoJogo
    var jogo: Jogo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogo)

        jogo = intent.getSerializableExtra("jogo") as Jogo?

        if (jogo == null) {
            var tipoJogo = intent.getSerializableExtra("tipoJogo") as TipoJogo
            jogo = Jogo(
                tipoJogo,
                180000L,
                180000L,
                arrayListOf()
            )
        }

    }
}