package br.com.qmovie.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import br.com.qmovie.R

class SplashActivity : AppCompatActivity() {

    private val splashTimeOut = 6000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //Remover a action Bar:
        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashActivity, JogoActivity::class.java)
            startActivity(intent)
        }, splashTimeOut.toLong())
    }


}