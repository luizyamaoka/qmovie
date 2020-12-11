package br.com.qmovie.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import br.com.qmovie.R
import br.com.qmovie.domain.TipoJogo
import br.com.qmovie.service.movieService
import br.com.qmovie.viewmodel.JogoViewModel

class JogoActivity : AppCompatActivity() {

    lateinit var tipoJogo: TipoJogo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.putExtra("points",0)
        setContentView(R.layout.activity_jogo)
        tipoJogo = intent.getSerializableExtra("tipoJogo") as TipoJogo

    }
}