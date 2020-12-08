package br.com.qmovie

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import br.com.qmovie.domain.Filme
import br.com.qmovie.domain.Lancamento
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_lancamentos_popup.view.*
import java.text.SimpleDateFormat

class LancamentosPopupFragment() : DialogFragment() {

    lateinit var _context : Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_lancamentos_popup, container, false)

        val lancamento = arguments?.getSerializable("lancamento") as Filme

        val poster = "https://image.tmdb.org/t/p/w780${lancamento.poster_path}"

        view.tvTituloPopup.setText("${lancamento.title}")
        view.tvInfoDtLancamento.setText("Data de Lancamento: ${SimpleDateFormat("dd/MM/YY").format(lancamento.release_date)}")
//        view.tvInfoDiretor.setText("Diretor: ${lancamento.}")
//        view.tvInfoGenero.setText("Gênero: ${lancamento.genero}")
//        view.tvInfoClassificacao.setText("Classificação: ${lancamento.classificacao}")
        view.tvSinopseDescricao.setText("${lancamento.overview}")

        if (lancamento.poster_path.isNullOrEmpty()) {
            Glide.with(this).asBitmap()
                .load(R.drawable.poster_not_found)
                .into(view.ivCapaFilme)
        } else {
            Glide.with(this).asBitmap()
                .load(poster)
                .into(view.ivCapaFilme)
        }

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        view.btnFecharPopup.setOnClickListener { this.dismiss() }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _context = context
    }
}