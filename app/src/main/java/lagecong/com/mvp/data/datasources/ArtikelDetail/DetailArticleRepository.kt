package id.co.dgip.data.datasource

import lagecong.com.mvp.data.model.ModelDetailArticle

class DetailArticleRepository (DetailLocal : DetailArticleDS, DetailRemote: DetailArticleDS): DetailArticleDS {
    private var mDetailLocal: DetailArticleDS = DetailLocal
    private var mDetailRemote: DetailArticleDS = DetailRemote
    private var mCached: ModelDetailArticle? = null

    companion object {
        private var INSTANCE: DetailArticleRepository? = null

        @JvmStatic
        fun getInstance(DetailLocal: DetailArticleDS, DetailRemote: DetailArticleDS): DetailArticleRepository {
            if (INSTANCE == null)
                INSTANCE = DetailArticleRepository(DetailLocal, DetailRemote)
            return INSTANCE!!
        }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }

    override fun LoadDetailArticle(id: String, callback: DetailArticleDS.LoadDetailArticleCallback) {
        if (mCached != null) {
            callback.onSuccess(mCached!!)
            return
        }

        mDetailLocal.LoadDetailArticle(id, object : DetailArticleDS.LoadDetailArticleCallback {
            override fun onSuccess(data: ModelDetailArticle) {
                cachingArticle(data)
                callback.onSuccess(data)

            }

            override fun onError(code: Int) {
                shouldLoadMainMenuFromRemote(id, callback)

            }

        })

    }

    override fun deleteLocal() {
        mDetailLocal.deleteLocal()

    }


    override fun saveDetail(data: ModelDetailArticle, id: String) {
        mDetailLocal.saveDetail(data,id)

    }

    private fun cachingArticle(artikels: ModelDetailArticle) {
        mCached = null
        if (artikels == null) {
            return
        }
        mCached = artikels
    }

    fun changeLocal(artikels: ModelDetailArticle, id : String){
        deleteLocal()
        if(artikels==null)
            return
        saveDetail(artikels, id)
    }

    fun shouldLoadMainMenuFromRemote(url : String, callback: DetailArticleDS.LoadDetailArticleCallback) {
        mDetailRemote.LoadDetailArticle(url, object : DetailArticleDS.LoadDetailArticleCallback {
            override fun onSuccess(data: ModelDetailArticle) {
                cachingArticle(data)
                changeLocal(data, url)
                callback.onSuccess(data)

            }

            override fun onError(code: Int) {
                callback.onError(code)

            }
        })
    }

}