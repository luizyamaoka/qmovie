package br.com.qmovie.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.qmovie.domain.Ranking
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.launch


class RankingViewModel : ViewModel() {

    var db = FirebaseFirestore.getInstance()
    private val TAG = "RankingViewModel"
    val ranking = MutableLiveData<ArrayList<Ranking>>()

    fun getRanking() {

        viewModelScope.launch {
            db.collection("ranking")
                .whereGreaterThan("pontos", 0)
                .orderBy("pontos", Query.Direction.DESCENDING)
                .limit(40)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val rankingList = arrayListOf<Ranking>()
                        for (document in task.result!!) {
                            val ranking = Ranking(
                                1,
                                document.data["nome"] as String,
                                document.data["pontos"] as Long,
                                document.data["foto"] as String)
                            rankingList.add(ranking)
                        }
                        ranking.value = rankingList
                    } else {
                        Log.w(TAG, "Error getting documents.", task.exception)
                    }
                }
        }
    }

    fun saveRanking(user: FirebaseUser, pontos: Long) {
        viewModelScope.launch {
            val ranking = Ranking(1,
                user.displayName.toString(),
                pontos,
                user.photoUrl.toString()
            )

            db.collection("ranking")
                .add(ranking.toHashMap())
                .addOnSuccessListener { documentReference ->
                    Log.d(
                        TAG,
                        "DocumentSnapshot added with ID: " + documentReference.id
                    )
                }
                .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }
        }
    }

}