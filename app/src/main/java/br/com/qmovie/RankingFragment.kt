package br.com.qmovie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.qmovie.adapter.RankingAdapter
import br.com.qmovie.extension.toPoints
import br.com.qmovie.viewmodel.RankingViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_ranking.view.*

class RankingFragment : Fragment() {

    private lateinit var viewModel : RankingViewModel
    private lateinit var rankingAdapter: RankingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(RankingViewModel::class.java)
        rankingAdapter = RankingAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_ranking, container, false)

        view.rvRanking.adapter = rankingAdapter

        viewModel.ranking.observe(requireActivity(), Observer {

            Log.d("Ranking", it.toString())

            if (it.size >= 1) {
                view.tvNameR1.text = it[0].nome
                view.tvPontosR1.text = it[0].pontos.toPoints()
                setImage(view.imR1, it[0].photoUrl)
            }

            if (it.size >= 2) {
                view.tvNameR2.text = it[1].nome
                view.tvPontosR2.text = it[1].pontos.toPoints()
                setImage(view.imR2, it[1].photoUrl)
            }

            if (it.size >= 3) {
                view.tvNameR3.text = it[2].nome
                view.tvPontosR3.text = it[2].pontos.toPoints()
                setImage(view.imR3, it[2].photoUrl)
            }

            if (it.size >= 4) {
                rankingAdapter.setRanking(ArrayList(it.subList(3, it.size)))
            }
        })

        viewModel.getRanking()

        return view

    }

    fun setImage(imageView: ImageView, photoUrl: String) {
        if (photoUrl != "null") {
            Glide.with(imageView.context).asBitmap()
                .load(photoUrl)
                .into(imageView)
        } else {
            imageView.setImageResource(R.drawable.circle_rank)
        }
    }

}