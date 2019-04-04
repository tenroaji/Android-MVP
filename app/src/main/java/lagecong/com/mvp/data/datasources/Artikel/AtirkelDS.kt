package lagecong.com.mvp.data.datasources.Artikel

import lagecong.com.mvp.data.model.ModelArticleResponse
import lagecong.com.mvp.data.model.ModelPagination

interface ArtikelDS {
    interface LoadArtikelCallback {
        fun onSuccess(artikels: List<ModelArticleResponse>, pagination: ModelPagination?, loadfrom: String)
        fun onError(code: Int, message: String?, loadfrom: String)
    }

    interface GetArtikelCallback {
        fun onSuccess(artikel: ModelArticleResponse)
        fun onError(code: Int, message: String?)
    }

    fun loadArtikel(page: Int, category: String, callback: LoadArtikelCallback)
    fun getArtikelById(url: String, callback: GetArtikelCallback)
    fun saveArtikel(artikel: ModelArticleResponse)
    fun saveArtikels(artikels: List<ModelArticleResponse>)
    fun deleteAllArtikel()
    fun getLastPage(): Int
    fun refreshData()
}
