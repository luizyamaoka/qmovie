package br.com.qmovie.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import br.com.qmovie.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.menubar.*

class MainActivity : AppCompatActivity() {

    private var user : FirebaseUser? = null
    var isConnected : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        isOnline()
        when (intent.getStringExtra("destinationFragment")) {
            "NovoJogoFragment" -> navController.navigate(R.id.novoJogoFragment)
            "RankingFragment" -> navController.navigate(R.id.rankingFragment)
            "LancamentosFragment" -> navController.navigate(R.id.lancamentosFragment)
            "ConfiguracoesFragment" -> navController.navigate(R.id.configuracoesFragment)
            "NoConnectionFragment" -> navController.navigate(R.id.noConnectionFragment)
            else -> if (isConnected){
                navController.navigate(R.id.novoJogoFragment)
            } else {
                navController.navigate(R.id.lancamentosFragment)
            }
        }

        menuJogo.setOnClickListener {
            isOnline()
            if(isConnected) {
                navController.navigate(R.id.novoJogoFragment)
            } else{
                navController.navigate(R.id.noConnectionFragment)
            }
        }

        menuRank.setOnClickListener {
            if(isConnected) {
                navController.navigate(R.id.rankingFragment)
            } else{
                navController.navigate(R.id.noConnectionFragment)
            }
        }

        menuLanca.setOnClickListener {
            navController.navigate(R.id.lancamentosFragment)
        }

        menuConfi.setOnClickListener {
            navController.navigate(R.id.configuracoesFragment)
        }

    }

    override fun onResume() {
        super.onResume()
        user = FirebaseAuth.getInstance().currentUser

        if (user == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun isOnline(){
        val connectivityManager : ConnectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork : NetworkInfo? = connectivityManager.activeNetworkInfo
        isConnected  = activeNetwork?.isConnectedOrConnecting == true
    }
}