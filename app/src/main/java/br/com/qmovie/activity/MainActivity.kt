package br.com.qmovie.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import br.com.qmovie.*
import kotlinx.android.synthetic.main.menubar.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        when (intent.getStringExtra("destinationFragment")) {
            "NovoJogoFragment" -> navController.navigate(R.id.novoJogoFragment)
            "RankingFragment" -> navController.navigate(R.id.rankingFragment)
            "LancamentosFragment" -> navController.navigate(R.id.lancamentosFragment)
            "ConfiguracoesFragment" -> navController.navigate(R.id.configuracoesFragment)
            else -> navController.navigate(R.id.novoJogoFragment)
        }

        menuJogo.setOnClickListener {
            navController.navigate(R.id.novoJogoFragment)
        }

        menuRank.setOnClickListener {
            navController.navigate(R.id.rankingFragment)
        }

        menuLanca.setOnClickListener {
            navController.navigate(R.id.lancamentosFragment)
        }

        menuConfi.setOnClickListener {
            navController.navigate(R.id.configuracoesFragment)
        }

    }
}