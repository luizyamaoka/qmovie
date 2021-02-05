package br.com.qmovie


import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceFragmentCompat
import br.com.qmovie.activity.LoginActivity
import br.com.qmovie.activity.MainActivity
import br.com.qmovie.viewmodel.UserViewModel
import br.com.qmovie.viewmodel.viewModelFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.custom_preference2.btnLogout


class ConfiguracoesFragment : PreferenceFragmentCompat() {

    private lateinit var viewModel : UserViewModel

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.config_layout, rootKey)

        viewModel = ViewModelProvider(
            this,
            viewModelFactory {
                UserViewModel(
                    (activity as MainActivity),
                    FirebaseAuth.getInstance()
                )
            }
        ).get(UserViewModel::class.java)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        (activity as MainActivity).supportFragmentManager.beginTransaction().apply {
            detach(this@ConfiguracoesFragment)
            attach(this@ConfiguracoesFragment)
            commit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View? = super.onCreateView(inflater, container, savedInstanceState)
        view!!.setBackgroundColor(Color.rgb(25, 26, 29))
        return view
    }
}