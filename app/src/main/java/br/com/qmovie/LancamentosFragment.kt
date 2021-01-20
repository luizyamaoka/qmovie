package br.com.qmovie

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.qmovie.adapter.LancamentosAdapter
import br.com.qmovie.database.AppDB
import br.com.qmovie.domain.Lancamento
import br.com.qmovie.service.DatabaseRepository
import br.com.qmovie.service.RepositoryImplementation
import br.com.qmovie.service.movieService
import br.com.qmovie.viewmodel.LancamentoViewModel
import kotlinx.android.synthetic.main.fragment_jogo.view.*
import kotlinx.android.synthetic.main.fragment_lancamentos.*
import kotlinx.android.synthetic.main.fragment_lancamentos.view.*
import java.util.*

class LancamentosFragment : Fragment() {

    private lateinit var db: AppDB
    private lateinit var repo : DatabaseRepository
    lateinit var _context : Context
    lateinit var lancamentoAdapter : LancamentosAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initDB()

        repo = RepositoryImplementation(db.filmeDAO())

        val viewModel by viewModels<LancamentoViewModel>{
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return LancamentoViewModel(movieService,repo) as T
                }
            }
        }

        // Inflate the layout for this fragment
        lancamentoAdapter = LancamentosAdapter(this,viewModel)

        val view = inflater.inflate(R.layout.fragment_lancamentos, container, false)
        view.rvLancamentos.adapter = lancamentoAdapter
        if (db.doesDatabaseExist(_context,"qmovie.db")){
            Log.i("DB EXISTENTE",db.doesDatabaseExist(_context,"qmovie.db").toString())
        } else {
            viewModel.listUpcoming.observe(viewLifecycleOwner) {
                Log.i("Observe", viewModel.listUpcoming.value.toString())
                lancamentoAdapter.addUpcoming(it)
            }
            viewModel.getUpcoming()
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _context = context
    }

    fun initDB(){
        db = AppDB.invoke(activity)!!
    }

}