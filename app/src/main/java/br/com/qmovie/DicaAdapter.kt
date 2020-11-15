package br.com.qmovie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.qmovie.domain.Dica
import br.com.qmovie.domain.TipoDica
import kotlinx.android.synthetic.main.item_dica.view.*

class DicaAdapter(
    private val jogoFragment: JogoFragment,
    private val dicas: ArrayList<Dica>
) : RecyclerView.Adapter<DicaAdapter.DicaViewHolder>() {

    class DicaViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
        val tvDicaNumero : TextView = view.tvDicaNumero

        val tvDicaConteudo : TextView = view.tvDicaConteudo
        val ivDicaImagem : ImageView = view.ivDicaImagem
        val tvInterrogacao : TextView = view.tvInterrogacao
        val btnAbrirDica : Button = view.btnAbrirDica

        val btnDicaSetaDireita : Button = view.btnDicaSetaDireita
        val btnDicaSetaEsquerda : Button = view.btnDicaSetaEsquerda
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DicaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dica, parent, false)
        return DicaViewHolder(view)
    }

    override fun getItemCount() = dicas.size

    override fun onBindViewHolder(holder: DicaViewHolder, position: Int) {
        val dica = dicas.get(position)

        holder.tvDicaNumero.text = "Dica #${position + 1}"
        holder.tvDicaConteudo.text = dica.conteudo
        // TODO: definir dica de imagem

        if (position == 0) holder.btnDicaSetaEsquerda.visibility = View.INVISIBLE
        if (position == itemCount - 1) holder.btnDicaSetaDireita.visibility = View.INVISIBLE
        when (dica.esta_aberta) {
            false -> {
                holder.btnAbrirDica.visibility = View.VISIBLE
                holder.tvInterrogacao.visibility = View.VISIBLE
                holder.tvDicaConteudo.visibility = View.INVISIBLE
                holder.ivDicaImagem.visibility = View.INVISIBLE
            }
            true -> {
                holder.btnAbrirDica.visibility = View.INVISIBLE
                holder.tvInterrogacao.visibility = View.INVISIBLE
                when (dica.tipo) {
                    TipoDica.IMAGEM -> {
                        holder.tvDicaConteudo.visibility = View.INVISIBLE
                        holder.ivDicaImagem.visibility = View.VISIBLE
                    }
                    TipoDica.TEXTO -> {
                        holder.tvDicaConteudo.visibility = View.VISIBLE
                        holder.ivDicaImagem.visibility = View.INVISIBLE
                    }
                }
            }
        }

        holder.btnAbrirDica.setOnClickListener {
            dica.esta_aberta = true
            notifyItemChanged(position)
            jogoFragment.adicionaTempo(-10000L)
        }
    }
}