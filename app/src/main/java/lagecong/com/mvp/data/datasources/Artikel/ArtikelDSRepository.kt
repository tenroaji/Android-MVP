package lagecong.com.mvp.data.datasources.Artikel

import android.text.TextUtils
import lagecong.com.mvp.data.model.ModelArticleResponse
import lagecong.com.mvp.data.model.ModelPagination
import java.util.*

class ArtikelDSRepository private constructor(private val mArtikelDSLocal: ArtikelDS, private val mArtikelDSRemote: ArtikelDS) :
    ArtikelDS {
    companion object {

        private var INSTANCE: ArtikelDSRepository? = null

        fun getINSTANCE(artikelDSLocal: ArtikelDS, artikelDSRemote: ArtikelDS): ArtikelDSRepository {
            INSTANCE = INSTANCE
                    ?: ArtikelDSRepository(artikelDSLocal, artikelDSRemote)
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

    private var nCurrentCategory: String? = null
    private var mCurrentPage = 1
    private var mLastPage = 1
    private var mCachedArtikel: MutableMap<Int, ModelArticleResponse>? = null

    override fun loadArtikel(page: Int, category: String, callback: ArtikelDS.LoadArtikelCallback) {
        if (mCurrentPage == page && TextUtils.equals(nCurrentCategory, category)) {
            if (mCachedArtikel != null && !mCachedArtikel!!.isEmpty()) {
                callback.onSuccess(ArrayList(mCachedArtikel!!.values), null, "local")
                return
            }
            mArtikelDSLocal.loadArtikel(page, category, object :
                ArtikelDS.LoadArtikelCallback {
                override fun onSuccess(
                    artikels: List<ModelArticleResponse>,
                    pagination: ModelPagination?,
                    loadfrom: String
                ) {
                    cachingArtikels(page, artikels)
                    callback.onSuccess(artikels, pagination, loadfrom)
                }

                override fun onError(code: Int, message: String?, loadfrom: String) {
                    shouldLoadArticleFromRemote(page, category, callback)
                }
            })
            nCurrentCategory = category
        } else {
            shouldLoadArticleFromRemote(page, category, callback)
        }
    }

    override fun getArtikelById(id: String, callback: ArtikelDS.GetArtikelCallback) {
        mArtikelDSLocal.getArtikelById(id, object :
            ArtikelDS.GetArtikelCallback {
            override fun onSuccess(artikel: ModelArticleResponse) {
                callback.onSuccess(artikel)
            }

            override fun onError(code: Int, message: String?) {
                mArtikelDSRemote.getArtikelById(id, object :
                    ArtikelDS.GetArtikelCallback {
                    override fun onSuccess(artikel: ModelArticleResponse) {
                        if (artikel != null)
                            saveArtikel(artikel)
                        callback.onSuccess(artikel)
                    }

                    override fun onError(code: Int, message: String?) {
                        callback.onError(code, message)
                    }
                })
            }
        })

    }

    override fun saveArtikel(artikel: ModelArticleResponse) {
        mArtikelDSLocal.saveArtikel(artikel)
    }

    override fun saveArtikels(artikels: List<ModelArticleResponse>) {
        mArtikelDSLocal.saveArtikels(artikels)
    }

    override fun deleteAllArtikel() {
        mArtikelDSLocal.deleteAllArtikel()
    }

    override fun getLastPage(): Int {
        return mLastPage
    }

    override fun refreshData() {
        mCurrentPage = 1
        mLastPage = 1
        if (mCachedArtikel != null)
            mCachedArtikel!!.clear()
        deleteAllArtikel()
    }

    private fun changeLocalArtikel(page: Int, artikels: List<ModelArticleResponse>) {
        if (page == 1) {
            deleteAllArtikel()
        }
        if (artikels.isEmpty()) {
            return
        }
        saveArtikels(artikels)
    }

    private fun cachingArtikels(page: Int, artikels: List<ModelArticleResponse>) {
        if (page == 1) {
            if (mCachedArtikel == null)
                mCachedArtikel = LinkedHashMap()
            mCachedArtikel!!.clear()
        }
        if (artikels.isEmpty()) {
            return
        }
        for (artikel in artikels) {
            mCachedArtikel!![artikel.id_target!!] = artikel
        }
    }

    fun shouldLoadArticleFromRemote(page: Int, category: String, callback: ArtikelDS.LoadArtikelCallback) {
        mArtikelDSRemote.loadArtikel(page, category, object :
            ArtikelDS.LoadArtikelCallback {
            override fun onSuccess(artikels: List<ModelArticleResponse>, pagination: ModelPagination?, loadfrom: String) {
                if (pagination != null) {
                    mCurrentPage = pagination.currentPage!!
                    mLastPage = pagination.lastPage!!
                }
                cachingArtikels(page, artikels)
                changeLocalArtikel(page, artikels)
                callback.onSuccess(ArrayList(mCachedArtikel!!.values), pagination, loadfrom)
            }

            override fun onError(code: Int, message: String?, loadfrom: String) {
                callback.onError(code, message, loadfrom)
            }
        })
    }


}
