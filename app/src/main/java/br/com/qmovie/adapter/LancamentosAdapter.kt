package br.com.qmovie.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import br.com.qmovie.LancamentosFragment
import br.com.qmovie.R
import br.com.qmovie.domain.Filme
import br.com.qmovie.domain.Lancamento
import br.com.qmovie.viewmodel.LancamentoViewModel
import kotlinx.android.synthetic.main.item_lancamentos.view.*
import java.text.SimpleDateFormat


class LancamentosAdapter(private val lancamentosFragment : LancamentosFragment) : RecyclerView.Adapter<LancamentosAdapter.LancamentoViewHolder>() {

     private val lancamentos = arrayListOf<Filme>()

    class LancamentoViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
        val tvTituloLancamento : TextView = view.tvTituloLancamento
        val tvDataLancamento : TextView = view.tvDataLancamento
        val btnFavoritarLancamento : ImageButton = view.btnFavoritarLancamento
        val cvItemLancamentos : CardView = view.cvItemLancamentos
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LancamentoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lancamentos, parent, false)
        return LancamentoViewHolder(view)
    }

    override fun getItemCount() = lancamentos.size

    override fun onBindViewHolder(holder: LancamentoViewHolder, position: Int) {
        val lancamento = lancamentos.get(position)
        holder.tvTituloLancamento.text = lancamento.title
        holder.tvDataLancamento.text = SimpleDateFormat("dd/MM/YY").format(lancamento.release_date)

//        holder.btnFavoritarLancamento.setOnClickListener {
//            when (lancamento.favorito) {
//                true -> {
//                    holder.btnFavoritarLancamento.setImageResource(R.drawable.ic_btn_favoritar_lancamento_false)
//                    lancamento.favorito = !lancamento.favorito
//                }
//                else -> {
//                    holder.btnFavoritarLancamento.setImageResource(R.drawable.ic_btn_favoritar_lancamento_true)
//                    lancamento.favorito = !lancamento.favorito
//                }
//            }
//        }

        holder.cvItemLancamentos.setOnClickListener {
            val bundle =  Bundle()
            bundle.putSerializable("lancamento", lancamento)
            findNavController(lancamentosFragment).navigate(
                R.id.action_lancamentosFragment_to_lancamentosPopupFragment, bundle)
        }
    }
    fun addUpcoming(list : ArrayList<Filme>){
        lancamentos.addAll(list)
        notifyDataSetChanged()
    }
}