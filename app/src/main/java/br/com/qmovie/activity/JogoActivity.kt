package br.com.qmovie.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.qmovie.R
import br.com.qmovie.domain.Jogo
import br.com.qmovie.domain.TipoJogo
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class JogoActivity : AppCompatActivity() {

    var jogo: Jogo? = null
    private var user : FirebaseUser? = null

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

    override fun onResume() {
        super.onResume()
        user = FirebaseAuth.getInstance().currentUser

        if (user == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}