package br.com.qmovie

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.qmovie.viewmodel.JogoViewModel
import kotlinx.android.synthetic.main.fragment_confirmation_message.view.*

class ConfirmationMessageFragment : DialogFragment() {

    private lateinit var viewModel: JogoViewModel
    private lateinit var tipoMensagem: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_confirmation_message, container, false)
        
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        viewModel = ViewModelProvider(requireActivity()).get(JogoViewModel::class.java)
        tipoMensagem = arguments?.getString("tipoMensagem")!!

        when (tipoMensagem) {
            "CONFIRMACAO_DICA_EXTRA" -> {
                view.tvConfirmacaoTitulo.text = getString(R.string.dialog_confirmacao_dica_extra_titulo)
                view.tvConfirmacaoMensagem.text = getString(R.string.dialog_confirmacao_dica_extra_mensagem)
                view.btnConfirmacao.setOnClickListener {
                    viewModel.usarDicaExtra()
                    this.dismiss()
                }
            }
            "CONFIRMACAO_DESISTIR" -> {
                view.tvConfirmacaoTitulo.text = getString(R.string.dialog_confirmacao_desistir_titulo)
                view.tvConfirmacaoMensagem.text = getString(R.string.dialog_confirmacao_desistir_mensagem)
                view.btnConfirmacao.setOnClickListener {
                    findNavController().navigate(R.id.action_confirmationMessageFragment_to_gameOverFragment)
                }
            }
        }

        view.btnFecharConfirmacao.setOnClickListener { this.dismiss() }




        return view
    }

}