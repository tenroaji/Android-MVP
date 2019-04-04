package lagecong.com.mvp.Artikel

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.acitivity_article.*
import lagecong.com.mvp.R
import lagecong.com.mvp.data.adapters.AdapterArticle
import lagecong.com.mvp.data.model.ModelArticleResponse
import lagecong.com.mvp.utils.invisible
import lagecong.com.mvp.utils.visible

class ArtikelActivity : AppCompatActivity(), ArticleContracts.View {
    companion object {
        const val KEY_ID = "id"
        const val KEY_TITLE = "title"
        const val KEY_ACTION = "action"
    }

     lateinit var mPresenter: ArticleContracts.Presenter
     lateinit var mRecyclerAdapter: AdapterArticle
    private var mCurrentPosition: Int = 0
     private var nCategory=""
    private var action= ""
    private var title_category=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivity_article)
        val iconBack = toolbar.navigationIcon
        iconBack?.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener{
            onBackPressed()
        }
        nCategory = intent.getStringExtra(KEY_ID)?:"1"
        title_category = intent.getStringExtra(KEY_TITLE)?:"LIPUTAN TERKINI"
        action = intent.getStringExtra(KEY_ACTION)?:""
        if (TextUtils.isEmpty(nCategory)){
            Toast.makeText(this, "Maaf, kategori tidak ditemukan", Toast.LENGTH_SHORT).show()
            onBackPressed()
            return
        }
        ArticlePresenter(this, nCategory, this)
        mRecyclerView.layoutManager=(LinearLayoutManager(this))
        mRecyclerAdapter = AdapterArticle()
        mRecyclerView.adapter = mRecyclerAdapter

        tvTitle.text = title_category
        val mLayoutManager = LinearLayoutManager(this)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.isNestedScrollingEnabled = true
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mRecyclerAdapter

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = mLayoutManager.itemCount
                val lastVisibleItem = mLayoutManager.findLastVisibleItemPosition()
                mCurrentPosition = lastVisibleItem
                Log.e("onscroll","total =$totalItemCount, last=$lastVisibleItem, current=$mCurrentPosition")
                if (!mPresenter.loadMore() && totalItemCount > 0 && totalItemCount <= lastVisibleItem + 2
                        && mPresenter.getCurrentPage() < mPresenter.getCountPage()) {
                    mPresenter.loadNextPage()
                }
            }
        })

        swipeRefresh.setOnRefreshListener{
            mPresenter.forceUpdate()
            mPresenter.refreshArtikelTerbaru()
        }
        mPresenter.refreshArtikelTerbaru()
        errorText.setOnClickListener {
            errorText.invisible()
            mPresenter.refreshArtikelTerbaru()
        }

    }

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }


    override fun showRefreshIndicator(show: Boolean) {
        mView?:return
        swipeRefresh.setColorSchemeResources(R.color.colorAccent)
        swipeRefresh.post { swipeRefresh.isRefreshing = show }
    }

    override fun showLoadNextPageIndicator(show: Boolean) {
        mView?:return
        mRecyclerAdapter.setLoading(show)
    }

    override fun showNoDataArtikel() {
        mView?:return
        containError.visible()
        errorText.text = "Maaf belum ada data untuk ditampilkan"
    }

    @SuppressWarnings("unchecked")
    override fun showArtikels(listArtikel: List<ModelArticleResponse>) {
        mView?:return
        if (containError.isShown){
            containError.invisible()
        }
        mRecyclerView.setBackgroundColor(ContextCompat.getColor(applicationContext,
            R.color.basicBackground
        ))
        val mArtikelData = ArrayList(listArtikel)
        mRecyclerAdapter.updateAdapter(mArtikelData)
    }

    override fun showErrorMessage(code: Int) {
        mView?:return
        if (mPresenter.getCurrentPage() > 1) {
            Toast.makeText(applicationContext, "error -> $code", Toast.LENGTH_SHORT).show()
            return
        }

        if (code == 401) {
            //NOTICE UI what must be handle for unauthorized

        } else {
            if (mRecyclerAdapter.itemCount == 0) {
                containError.visible()
                errorText.text = "Try Again - $code"
            } else
                Toast.makeText(applicationContext, "Data yang ditampilkan bukan data up to date", Toast.LENGTH_LONG).show()
        }
    }

    override fun showToastLocal() {
        Toast.makeText(applicationContext, "Data yang ditampilkan bukan data up to date", Toast.LENGTH_LONG).show()
    }

    override fun makeHeaderOnAdapter(height: Int) {
    }

    override fun goToTop() {
    }

    override fun setPresenter(presenter: ArticleContracts.Presenter) {
        mPresenter = presenter
    }

}