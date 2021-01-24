package br.com.qmovie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import br.com.qmovie.activity.MainActivity
import br.com.qmovie.domain.Jogo
import br.com.qmovie.viewmodel.RankingViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_game_over.view.*

class GameOverFragment : Fragment() {

    private lateinit var jogo : Jogo
    private lateinit var rankingViewModel : RankingViewModel
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        jogo = arguments?.getSerializable("jogo") as Jogo

        rankingViewModel = ViewModelProvider(requireActivity()).get(RankingViewModel::class.java)

        rankingViewModel.saveRanking(auth.currentUser!!, jogo.getPontuacaoFinal())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game_over, container, false)

        view.btnRanking.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            intent.putExtra("destinationFragment", "RankingFragment")
            startActivity(intent)
        }

        view.btnMenuInicial.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            intent.putExtra("destinationFragment", "NovoJogoFragment")
            startActivity(intent)
        }

        view.btnCompatilhar.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type="text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "Fiz ${jogo.getPontuacaoFinal()} pontos no qMovie");
            startActivity(intent)
        }

        view.tvResultadoPontos.text = jogo.getPontuacaoFinal().toString()

        return view
    }
}