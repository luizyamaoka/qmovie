package br.com.qmovie

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_confirmation_message.*

class ConfirmationMessageFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

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
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

}