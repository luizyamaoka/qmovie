package br.com.qmovie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.qmovie.R
import br.com.qmovie.domain.Ranking
import br.com.qmovie.extension.toPoints
import com.bumptech.glide.Glide

class RankingAdapter : RecyclerView.Adapter<RankingAdapter.RankingViewHolder>() {

    var listRanking = arrayListOf<Ranking>()

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
        holder.tvPosicao.text = "${position + 3}"

        holder.tvNomePosicao.text = ranking.nome
        holder.tvPontosPosicao.text = ranking.pontos.toPoints()
        if (ranking.photoUrl != "null") {
            Glide.with(holder.imPosicao.context).asBitmap()
                .load(ranking.photoUrl)
                .into(holder.imPosicao)
        } else {
            holder.imPosicao.setImageResource(R.drawable.circle_rank)
        }

    }

    override fun getItemCount() = listRanking.size

    fun setRanking(ranking : ArrayList<Ranking>) {
        listRanking = ranking
        notifyDataSetChanged()
    }
}