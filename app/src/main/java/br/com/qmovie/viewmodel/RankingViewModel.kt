package br.com.qmovie.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import br.com.qmovie.R
import br.com.qmovie.domain.Ranking

class RankingViewModel : ViewModel() {

    fun getRanking() : ArrayList<Ranking>{
        var listRanking : ArrayList<Ranking> = arrayListOf(
            Ranking(1, R.drawable.ic_launcher_foreground,"Rank1",1),
            Ranking(1,R.drawable.ic_launcher_foreground,"Rank2",99999),
            Ranking(1,R.drawable.ic_launcher_foreground,"Rank3",999),
            Ranking(1,R.drawable.ic_launcher_foreground,"Rank4",100),
            Ranking(1, R.drawable.ic_launcher_foreground,"Rank6",1),
            Ranking(1,R.drawable.ic_launcher_foreground,"Rank7",99999),
            Ranking(1,R.drawable.ic_launcher_foreground,"Rank8",999),
            Ranking(1,R.drawable.ic_launcher_foreground,"Rank9",100)
        )

        return listRanking
    }

}