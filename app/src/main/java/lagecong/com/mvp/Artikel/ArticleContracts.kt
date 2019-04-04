package lagecong.com.mvp.Artikel


import lagecong.com.mvp.data.model.ModelArticleResponse
import lagecong.com.mvp.utils.BasePresenter
import lagecong.com.mvp.utils.BaseView

interface ArticleContracts {
    interface View : BaseView<Presenter> {
        fun showRefreshIndicator(show: Boolean)

        fun showLoadNextPageIndicator(show: Boolean)

        fun showNoDataArtikel()

        fun showArtikels(listArtikel: List<ModelArticleResponse>)

        fun showErrorMessage(code: Int)

        fun showToastLocal()

        fun makeHeaderOnAdapter(height: Int)

        fun goToTop()
    }

    interface Presenter : BasePresenter {

        fun getCurrentPage(): Int

        fun getCountPage(): Int

        fun refreshArtikelTerbaru()

        fun loadNextPage()

        fun restoreCurrentPage(currentPage: Int, countPage: Int)

        fun loadMore(): Boolean

        fun forceUpdate()

    }
}
