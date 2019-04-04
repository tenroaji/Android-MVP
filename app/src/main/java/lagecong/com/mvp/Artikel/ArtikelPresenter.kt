package lagecong.com.mvp.Artikel

import android.content.Context
import android.os.Handler
import lagecong.com.mvp.data.datasources.Artikel.ArtikelDS
import id.co.dgip.article.kotlin.datasource.ArtikelDSLocal
import lagecong.com.mvp.data.datasources.Artikel.ArtikelDSRemote
import lagecong.com.mvp.data.datasources.Artikel.ArtikelDSRepository
import lagecong.com.mvp.R
import lagecong.com.mvp.data.local.ArtikelDatabase
import lagecong.com.mvp.data.model.ModelArticleResponse
import lagecong.com.mvp.data.model.ModelPagination
import lagecong.com.mvp.utils.executors.AppExecutors


class ArticlePresenter(context: Context, category: String,view: ArticleContracts.View) :
    ArticleContracts.Presenter {


    var mRepository: ArtikelDSRepository = ArtikelDSRepository.getINSTANCE(ArtikelDSLocal.getInstance(AppExecutors(), ArtikelDatabase.getInstance(context).artikelDAO()), ArtikelDSRemote.getInstance(context.resources.getString(
        R.string.app_url
    ), context))
    private var mView: ArticleContracts.View = view
    private var mCategory = category
    private var mCurrentPage = 1
    private var mCountPage = 0
    private var mForceUpdate = true
    private var isLoading = false

    init {
        mView.setPresenter(this)
    }

    override fun refreshArtikelTerbaru() {
        if (mForceUpdate) {
            mRepository.refreshData()
            mForceUpdate = false
            mCurrentPage = 1
        }
        isLoading = true
        mView.showRefreshIndicator(true)
        mView.makeHeaderOnAdapter(200)
        mRepository.loadArtikel(mCurrentPage, mCategory, object : ArtikelDS.LoadArtikelCallback {
            override fun onSuccess(
                artikels: List<ModelArticleResponse>,
                pagination: ModelPagination?,
                loadfrom: String
            ) {
                mView.showRefreshIndicator(false)
                mView.makeHeaderOnAdapter(0)
                if (!artikels.isEmpty()) {
                    mView.showArtikels(artikels)
                } else {
                    mView.showErrorMessage(404)
                }
                isLoading = false
                if (pagination == null) {
                    if (artikels.size >= 20) {
                        mCurrentPage = artikels.size / 20
                        mCountPage = if (mRepository.getLastPage() > 1) mRepository.getLastPage() + 1 else mCurrentPage + 1
                    } else {
                        mCurrentPage = 1
                        mCountPage = 1
                    }
                } else {
                    mCurrentPage = pagination.currentPage!!
                    mCountPage = pagination.lastPage!!
                }
            }

            override fun onError(code: Int, message: String?, loadfrom: String) {
                isLoading = false
                if (loadfrom == "local")
                    mView.showToastLocal()
                mView.showRefreshIndicator(false)
                mView.makeHeaderOnAdapter(0)
                mView.showErrorMessage(code)
            }
        })
    }

    override fun loadNextPage() {
        if (isLoading) {
            return
        }

        isLoading = true
        mCurrentPage++
        mView.showLoadNextPageIndicator(true)
        mRepository.shouldLoadArticleFromRemote(mCurrentPage, mCategory, object : ArtikelDS.LoadArtikelCallback {
            override fun onSuccess(artikels: List<ModelArticleResponse>, pagination: ModelPagination?, loadfrom: String) {
                mCountPage = pagination!!.lastPage!!
                mView.showLoadNextPageIndicator(false)
                if (!artikels.isEmpty()) {
                    mView.showArtikels(artikels)
                } else {
                    if (mCurrentPage >= 2) {
                        mView.showErrorMessage(404)
                        mCurrentPage--
                    } else
                        mView.showErrorMessage(404)
                }
                isLoading = false
            }

            override fun onError(code: Int, message: String?, loadfrom: String) {
                if (mCurrentPage >= 2) {
                    mView.showErrorMessage(code)
                    mCurrentPage--
                }
                mView.showLoadNextPageIndicator(false)
                Handler().postDelayed({ isLoading = false }, 1300)
            }
        })
    }

    override fun restoreCurrentPage(currentPage: Int, countPage: Int) {
        mCurrentPage = currentPage
        mCountPage = countPage
    }

    override fun getCurrentPage(): Int {
        return mCurrentPage
    }

    override fun getCountPage(): Int {
        return mCountPage
    }

    override fun loadMore(): Boolean {
        return isLoading
    }

    override fun forceUpdate() {
        mForceUpdate = true
    }

    override fun start() {
        refreshArtikelTerbaru()
    }
}