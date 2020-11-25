package br.com.qmovie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.qmovie.R
import br.com.qmovie.domain.Ranking
import br.com.qmovie.viewmodel.RankingViewModel
import kotlinx.android.synthetic.main.card_ranking.view.*

class RankingAdapter (private val viewModel : RankingViewModel, private val listRanking : ArrayList<Ranking>) : RecyclerView.Adapter<RankingAdapter.RankingViewHolder>() {
    class RankingViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView)  {
        var tvPosicao : TextView = itemView.findViewById(R.id.tvPosicao)
        var imPosicao : ImageView = itemView.findViewById(R.id.imPosicao)
        var tvNomePosicao : TextView = itemView.findViewById(R.id.tvNomePosicao)
        var tvPontosPosicao : TextView = itemView.findViewById(R.id.tvPontosPosicao)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_ranking,parent,false)
        return RankingViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        var ranking : Ranking = listRanking.get(position)
        holder.tvPosicao.text = ranking.id.toString()
        holder.imPosicao.setImageResource(ranking.foto)
        holder.tvNomePosicao.text = ranking.nome
        holder.tvPontosPosicao.text = ranking.pontos.toString()
    }

    override fun getItemCount() = listRanking.size
}