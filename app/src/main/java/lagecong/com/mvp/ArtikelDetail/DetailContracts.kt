package lagecong.com.mvp.ArtikelDetail

import lagecong.com.mvp.data.model.ModelDetailArticle
import lagecong.com.mvp.utils.BasePresenter
import lagecong.com.mvp.utils.BaseView

interface DetailContracts {
    interface View : BaseView<Presenter> {
        fun showWebViewContent(data : ModelDetailArticle)
        fun showContentDate(date : String)
        fun setUpContentSrcollingImages(images : List<String>?)
        fun showOnError(code : Int)
        fun showSwipeIndicator(show : Boolean)
        fun loadFromUrlGiven(url : String)
        fun setTitle(title : String)
        fun getUrl() : String
    }

    interface Presenter : BasePresenter {
        fun refreshData(requestedURL : String)
        fun setUrls(url : String?)
    }
}