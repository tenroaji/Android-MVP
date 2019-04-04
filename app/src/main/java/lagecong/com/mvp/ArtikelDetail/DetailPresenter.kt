package lagecong.com.mvp.ArtikelDetail

import android.content.Context
import id.co.dgip.data.datasource.DetailArticleDS
import id.co.dgip.data.datasource.DetailArticleRepository
import lagecong.com.mvp.R
import lagecong.com.mvp.data.datasources.ArtikelDetail.DetailArticleLocal
import lagecong.com.mvp.data.datasources.ArtikelDetail.DetailArticleRemote
import lagecong.com.mvp.data.local.ArtikelDatabase
import lagecong.com.mvp.data.model.ModelDetailArticle

import lagecong.com.mvp.utils.executors.AppExecutors

class DetailPresenter(context: Context, view: DetailContracts.View) : DetailContracts.Presenter {

    var mRepository: DetailArticleRepository? = null
    var mView: DetailContracts.View? = null
    var url: String? = ""

    init {
//        fun DetailPresenter(context: Context, view: DetailContracts.View){
        mRepository = DetailArticleRepository.getInstance(
            DetailArticleLocal.getInstance(AppExecutors(), ArtikelDatabase.getInstance(context).getArticleDetailDao()), DetailArticleRemote.getInstance(context.resources.getString(
            R.string.app_url), context))
        mView = view
        view.setPresenter(this)
//        }
    }


    override fun refreshData(requestedURL: String) {
        url = requestedURL
        mRepository!!.LoadDetailArticle(requestedURL, object : DetailArticleDS.LoadDetailArticleCallback {
            override fun onSuccess(data: ModelDetailArticle) {
                mView!!.showSwipeIndicator(false)
                mView!!.showWebViewContent(data)
                if (requestedURL.contains("posts/detail/"))
                    mView!!.showContentDate(data.response!!.published!!)
                mView!!.setUpContentSrcollingImages(data.response!!.image)
                mView!!.loadFromUrlGiven(data.response!!.content!!)
                mView!!.setTitle(data.response!!.title!!)
            }

            override fun onError(code: Int) {
                mView!!.showSwipeIndicator(false)
                mView!!.showOnError(code)
            }

        })
    }

    override fun start() {

        mView!!.showSwipeIndicator(true)
        mRepository!!.shouldLoadMainMenuFromRemote(mView!!.getUrl(), object : DetailArticleDS.LoadDetailArticleCallback {
            override fun onSuccess(data: ModelDetailArticle) {
                mView!!.showSwipeIndicator(false)
                mView!!.showWebViewContent(data)
                if (mView!!.getUrl().contains("posts/detail/"))
                mView!!.showContentDate(data.response!!.published!!)
                mView!!.setUpContentSrcollingImages(data.response!!.image)
                mView!!.loadFromUrlGiven(data.response!!.content!!)
                mView!!.setTitle(data.response!!.title!!)
            }

            override fun onError(code: Int) {
                mView!!.showSwipeIndicator(false)
                mView!!.showOnError(code)
            }

        })
    }

    override fun setUrls(Url: String?) {
        url = Url
    }


}