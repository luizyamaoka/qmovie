package br.com.qmovie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.menubar.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        navController.navigate(R.id.novoJogoFragment)

        menuJogo.setOnClickListener {
            var fragNovoJogo = NovoJogoFragment.newInstance()
            supportFragmentManager.beginTransaction().apply {
                add(R.id.nav_host_fragment, fragNovoJogo)
                commit()
            }
        }

        menuRank.setOnClickListener {
            var fragRank = RankingFragment.newInstance()
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.nav_host_fragment, fragRank)
                commit()
            }
        }

    }
}