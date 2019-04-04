package id.co.dgip.data.datasource

import lagecong.com.mvp.data.model.ModelDetailArticle

interface DetailArticleDS {
    interface LoadDetailArticleCallback{
        fun onSuccess(data : ModelDetailArticle)
        fun onError(code : Int)
    }

    fun LoadDetailArticle(url : String, callback: LoadDetailArticleCallback)
    fun deleteLocal()
    fun saveDetail(data : ModelDetailArticle, id: String)
}