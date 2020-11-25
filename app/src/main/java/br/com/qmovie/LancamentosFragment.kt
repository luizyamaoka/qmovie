package br.com.qmovie

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import br.com.qmovie.adapter.LancamentosAdapter
import br.com.qmovie.domain.Lancamento
import kotlinx.android.synthetic.main.fragment_jogo.view.*
import kotlinx.android.synthetic.main.fragment_lancamentos.view.*
import java.util.*

class LancamentosFragment : Fragment() {

    lateinit var _context : Context
    private var datinha = Date()
    private var lancamentos = arrayListOf(
        Lancamento(1, "Primeiro Lancamento", datinha, "João", "Aventura", "+12", "Essa é a sinopse do primeiro lançamento."),
        Lancamento(2, "Segundo Lancamento", datinha, "Maria", "Ação", "+14", "Essa é a sinopse do segundo lançamento."),
        Lancamento(3, "Terceiro Lancamento", datinha, "Pedro", "Suspense", "+16", "Essa é a sinopse do terceiro lançamento."),
        Lancamento(4, "Quarto Lancamento", datinha, "Roberto", "Adulto", "+18", "Essa é a sinopse do quarto lançamento.")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_lancamentos, container, false)
        view.rvLancamentos.adapter = LancamentosAdapter(this, lancamentos)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _context = context
    }
}