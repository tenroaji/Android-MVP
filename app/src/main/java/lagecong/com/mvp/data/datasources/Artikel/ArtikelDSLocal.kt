package id.co.dgip.article.kotlin.datasource

import com.google.gson.Gson
import lagecong.com.mvp.data.datasources.Artikel.ArtikelDS
import lagecong.com.mvp.data.local.ArtikelDAO
import lagecong.com.mvp.data.local.ArtikelEntity
import lagecong.com.mvp.data.model.ModelArticleResponse
import lagecong.com.mvp.utils.executors.AppExecutors
import java.util.*

class ArtikelDSLocal private constructor(private val mAppExecutors: AppExecutors, private val mArtikelDao: ArtikelDAO) :
    ArtikelDS {

    companion object {

        private var INSTANCE: ArtikelDSLocal? = null

        fun getInstance(appExecutors: AppExecutors, lelangDAO: ArtikelDAO): ArtikelDSLocal {
            INSTANCE ?:
                synchronized(ArtikelDSLocal::class.java) {
                    INSTANCE = INSTANCE
                            ?: ArtikelDSLocal(appExecutors, lelangDAO)
                }
            return INSTANCE!!
        }


        fun destroyInstance() {
            INSTANCE = null
        }
    }

    override fun loadArtikel(page: Int, category: String, callback: ArtikelDS.LoadArtikelCallback) {
        val runnable = Runnable {
            val mArtikels = mArtikelDao.dataArtikel
            val mListArtikel = ArrayList<ModelArticleResponse>()
            if (!mArtikels.isEmpty()) {
                for (artikel in mArtikels) {
                    val mArtikel = Gson().fromJson(artikel.dataArtikel, ModelArticleResponse::class.java)
                    if (mArtikel.category == category)
                        mListArtikel.add(mArtikel)
                }
            }

            mAppExecutors.mainThread().execute {
                if (mListArtikel.isEmpty()) {
                    callback.onError(0, null, "local")
                } else {
                    callback.onSuccess(mListArtikel, null, "local")
                }
            }
        }
        mAppExecutors.diskIO().execute(runnable)
    }

    override fun getArtikelById(url: String, callback: ArtikelDS.GetArtikelCallback) {

    }

    override fun saveArtikel(artikel: ModelArticleResponse) {
        val runnable = Runnable {
            val data = Gson().toJson(artikel)
            val artikelData = ArtikelEntity(artikel.id_target!!, data, artikel.published!!)
            mArtikelDao.insertArtikel(artikelData)
        }
        mAppExecutors.diskIO().execute(runnable)
    }

    override fun saveArtikels(artikels: List<ModelArticleResponse>) {
        val runnable = Runnable {
            if (!artikels.isEmpty()) {
                val mListArtikel = ArrayList<ArtikelEntity>()
                for (artikel in artikels) {
                    val data = Gson().toJson(artikel)
                    val artikelData = ArtikelEntity(artikel.id_target!!, data, artikel.published?:"")
                    mListArtikel.add(artikelData)
                }
                mArtikelDao.insertsArtikels(mListArtikel)
            }
        }
        mAppExecutors.diskIO().execute(runnable)
    }

    override fun deleteAllArtikel() {
        mAppExecutors.diskIO().execute { mArtikelDao.deleteAllArtikel() }
    }

    override fun getLastPage(): Int {
        return 0
    }

    override fun refreshData() {

    }


}