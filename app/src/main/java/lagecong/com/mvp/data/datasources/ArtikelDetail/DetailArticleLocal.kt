package lagecong.com.mvp.data.datasources.ArtikelDetail

import com.google.gson.Gson
import id.co.dgip.data.datasource.DetailArticleDS
import lagecong.com.mvp.data.local.ArticleDetailDao
import lagecong.com.mvp.data.local.ArticleDetailEntity
import lagecong.com.mvp.data.model.ModelDetailArticle
import lagecong.com.mvp.utils.executors.AppExecutors

class DetailArticleLocal(
    appExecutors: AppExecutors,
    dao: ArticleDetailDao
) : DetailArticleDS {

    private var mAppExecutors: AppExecutors
    private var mDetailDao: ArticleDetailDao

    companion object {
        private var INSTANCE: DetailArticleLocal? = null

        @JvmStatic
        fun getInstance(appExecutors: AppExecutors, dao: ArticleDetailDao) : DetailArticleLocal {

            if (INSTANCE == null) {
                INSTANCE = DetailArticleLocal(appExecutors, dao)
            }
            return INSTANCE!!
        }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }

    init {
        mAppExecutors = appExecutors
        mDetailDao = dao
    }

    override fun LoadDetailArticle(id : String, callback: DetailArticleDS.LoadDetailArticleCallback) {


        var runnable = Runnable {
            var data : ArticleDetailEntity? = mDetailDao.getArticleDetailById(id)
            var mData : ModelDetailArticle? = null
            if(data!=null){
                mData = Gson().fromJson(data.data_menu, ModelDetailArticle::class.java)
            } else {

            }

            mAppExecutors.mainThread().execute {
                if(mData!=null){
                    callback.onSuccess(mData)
                } else
                    callback.onError(0)
            }
        }
        mAppExecutors.diskIO().execute(runnable)
    }

    override fun deleteLocal() {
        mAppExecutors.diskIO().execute {
            mDetailDao.deleteAll()
        }

    }

    override fun saveDetail(data : ModelDetailArticle, id: String) {
        var runnable = Runnable {
            if (data!=null){
                var textJson = Gson().toJson(data)
                var index : Int
                if(!mDetailDao.articleDetail.isEmpty()) {
                    index = mDetailDao.articleDetail.size
                } else index=0
                var detailData = ArticleDetailEntity(id,textJson,index)
                mDetailDao.insertEach(detailData)
            }
        }
        mAppExecutors.diskIO().execute(runnable)

    }

}
