package br.com.qmovie

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class ConfirmationMessageFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_confirmation_message, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        when (arguments?.getString("tipoMensagem")) {
            "CONFIRMACAO_DICA_EXTRA" -> {
                view.findViewById<TextView>(R.id.tvConfirmacaoTitulo).text = getString(R.string.dialog_confirmacao_dica_extra_titulo)
                view.findViewById<TextView>(R.id.tvConfirmacaoMensagem).text = getString(R.string.dialog_confirmacao_dica_extra_mensagem)
            }
            "CONFIRMACAO_DESISTIR" -> {
                view.findViewById<TextView>(R.id.tvConfirmacaoTitulo).text = getString(R.string.dialog_confirmacao_desistir_titulo)
                view.findViewById<TextView>(R.id.tvConfirmacaoMensagem).text = getString(R.string.dialog_confirmacao_desistir_mensagem)
            }
        }

        view.findViewById<Button>(R.id.btnFecharConfirmacao).setOnClickListener { this.dismiss() }
        return view
    }

}