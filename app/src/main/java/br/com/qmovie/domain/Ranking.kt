package br.com.qmovie.domain

class Ranking (val id : Int, var nome : String, var pontos: Long, var photoUrl : String = "") {
    override fun toString(): String {
        return "Ranking(id=$id, foto=$photoUrl, nome='$nome', pontos=$pontos)"
    }

    fun toHashMap() : MutableMap<String, Any> {
        val ranking: MutableMap<String, Any> = HashMap()
        ranking["foto"] = photoUrl
        ranking["nome"] = nome
        ranking["pontos"] = pontos

        return ranking
    }

}