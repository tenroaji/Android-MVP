package lagecong.com.mvp.ArtikelDetail

import android.annotation.TargetApi
import android.content.Context
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.webkit.*
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_article.*
import lagecong.com.mvp.R
import lagecong.com.mvp.data.adapters.ImageAdapter
import lagecong.com.mvp.data.model.ModelDetailArticle
import lagecong.com.mvp.slider.Slider
import lagecong.com.mvp.slider.model.SliderConfig
import lagecong.com.mvp.slider.model.SliderPosition
import lagecong.com.mvp.utils.WebViewJSResizerUtil
import lagecong.com.mvp.utils.invisible
import lagecong.com.mvp.utils.visible


class DetailActivity : DetailContracts.View, AppCompatActivity() {
    private var imageToolbarUrl: String? = null
    private var mPresenter: DetailContracts.Presenter? = null
    private var mContentUrl: String? = null
    private var mAdapter: ImageAdapter? = null
    private var requestedURL: String? = ""

    companion object {
        const val KEY_ACTION = "action"
        const val KEY_TARGET_ID = "id_target"
        const val KEY_URL_IMAGE = "image_url"
        const val KEY_TITLE = "title"
        const val KEY_DATE = "date"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_article)
        imageToolbarUrl = intent.getStringExtra(KEY_URL_IMAGE)

        val iconBack = toolbar.navigationIcon
        iconBack?.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary),
                PorterDuff.Mode.SRC_IN)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        requestedURL ="posts/detail/" + intent.getStringExtra(KEY_TARGET_ID)
        Log.d("urlsabet", requestedURL)
        DetailPresenter(this, this)
        mPresenter?.setUrls(requestedURL!!)
        swipeRefresh.setOnRefreshListener {
            mPresenter?.refreshData(requestedURL!!)
        }
        if (TextUtils.isEmpty(intent.getStringExtra(KEY_ACTION))) {
            Toast.makeText(this, "You shoud pass action type", Toast.LENGTH_SHORT).show()
            onBackPressed()
        }
        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(WebViewJSResizerUtil(this, webView),
                WebViewJSResizerUtil.JS_RESIZER_NAME)
        val webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                swipeRefresh.isRefreshing = newProgress < 100
                super.onProgressChanged(view, newProgress)
            }
        }
        val webViewClient = object : WebViewClient() {
            @SuppressWarnings("deprecation")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return overrideUrl(view, url)
            }

            fun overrideUrl(view: WebView?, urlRequested: String?): Boolean {
//                if (!TextUtils.equals(urlRequested, mContentUrl) && URLUtil.isValidUrl(urlRequested)) {
//                    val urlRegex = "^(http[s]?://www\\.|http[s]?://|www\\.)"
//                    val baseUrl = resources.getString(R.string.app_url)
//                    //cleaning url for easy split and macthes
//                    val ownUrl = baseUrl.replaceFirst(urlRegex.toRegex(), "")
//                            .split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//                    val cleanUrl = urlRequested?.replace(urlRegex.toRegex(), "")
//                    val targetUrl =
//                            cleanUrl!!.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//                    val extension = urlRequested.substring(urlRequested.lastIndexOf("."))
//                    if (TextUtils.equals(extension, ".jpg") || TextUtils.equals(extension, ".jpeg") || TextUtils.equals(extension, ".png")) {
//                        val newIntent = Intent(applicationContext, ImageZoom::class.java)
//                        newIntent.putExtra("url", urlRequested)
//                        startActivity(newIntent)
//                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
//                        return true
//                    } else if (cleanUrl.startsWith(ownUrl[0])) {
//                        val newActivity: Intent
//                        when {
//                            targetUrl[1].startsWith("post") -> {
//                                newActivity = Intent(applicationContext, ArtikelActivity::class.java)
//                                newActivity.putExtra(ArtikelActivity.KEY_ID, targetUrl[4])
//                                newActivity.putExtra(ArtikelActivity.KEY_TITLE, firstLetterSentences(targetUrl[3]))
//                                startActivity(newActivity)
//                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
//                            }
//                            targetUrl[1].startsWith("page") -> {
//                                Log.e("error", targetUrl[3] + " " + MenuTypeUtil.TYPE_PAGES + " " + firstLetterSentences(targetUrl[2]))
//                                newActivity = Intent(applicationContext, DetailActivity::class.java)
//                                newActivity.putExtra(DetailActivity.KEY_TARGET_ID, targetUrl[3])
//                                newActivity.putExtra(DetailActivity.KEY_ACTION, MenuTypeUtil.TYPE_PAGES)
//                                newActivity.putExtra(DetailActivity.KEY_TITLE, firstLetterSentences(targetUrl[2]))
//                                startActivity(newActivity)
//                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
//                            }
//                            targetUrl[1].startsWith("directory") -> {
//                                Log.e("error", targetUrl[3] + " " + MenuTypeUtil.TYPE_PAGES + " " + firstLetterSentences(targetUrl[2]))
//                                newActivity = Intent(applicationContext, DirectoryDate::class.java)
//                                newActivity.putExtra(DirectoryDate.KEY_DIR_TARGET_ID, targetUrl[3])
////                                newActivity.putExtra(DetailActivity.KEY_ACTION, MenuTypeUtil.TYPE_PAGES)
//                                newActivity.putExtra(DirectoryDate.KEY_TITLE, firstLetterSentences(targetUrl[2]))
//                                startActivity(newActivity)
//                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
//                            }
//                            targetUrl[1].startsWith("menu") -> {
//                                when {
//                                    targetUrl.size == 6 -> {
//                                        newActivity = Intent(applicationContext, MenuLevel2Activity::class.java)
//                                        newActivity.putExtra(MenuLevel2Activity.KEY_ID, Integer.valueOf(targetUrl[5]))
//                                        try {
//                                            newActivity.putExtra(MenuLevel2Activity.KEY_TITLE, URLDecoder.decode(targetUrl[2], "UTF-8"))
//                                            val subtitle = URLDecoder.decode(targetUrl[3], "UTF-8")
//                                            if (!TextUtils.equals(subtitle, "null") && !TextUtils.isEmpty(subtitle)) {
//                                                newActivity.putExtra(MenuLevel2Activity.KEY_SUBTITLE, subtitle)
//                                            }
//                                        } catch (e: UnsupportedEncodingException) {
//                                            newActivity.putExtra(MenuLevel2Activity.KEY_SUBTITLE, "UNKNOWN")
//                                        }
//                                        startActivity(newActivity)
//                                    }
//                                    targetUrl.size == 5 -> {
//                                        val actionType = targetUrl[3]
//                                        when {
//                                            TextUtils.equals(actionType, MenuTypeUtil.TYPE_POSTS) -> {
//                                                newActivity = Intent(applicationContext, ArtikelActivity::class.java)
//                                                newActivity.putExtra(ArtikelActivity.KEY_ID, targetUrl[4])
//                                            }
//                                            TextUtils.equals(actionType, MenuTypeUtil.TYPE_DIRECTORY) -> {
//                                                newActivity = Intent(applicationContext, DirectoryDate::class.java)
//                                                newActivity.putExtra(DirectoryDate.KEY_DIR_TARGET_ID, targetUrl[4])
//                                            }
//                                            TextUtils.equals(actionType, MenuTypeUtil.TYPE_PAGES) -> {
//                                                newActivity = Intent(applicationContext, DetailActivity::class.java)
//                                                newActivity.putExtra(KEY_TARGET_ID, targetUrl[4])
//                                                newActivity.putExtra(KEY_ACTION, MenuTypeUtil.TYPE_PAGES)
//                                            }
//                                            else -> {
//                                                newActivity = Intent(applicationContext, MenuLevel3Activity::class.java)
//                                                newActivity.putExtra(MenuLevel3Activity.KEY_ID, Integer.valueOf(targetUrl[4]))
//                                                try {
//                                                    newActivity.putExtra(MenuLevel3Activity.KEY_TITLE, URLDecoder.decode(targetUrl[2], "UTF-8"))
//                                                } catch (e: UnsupportedEncodingException) {
//                                                    newActivity.putExtra(MenuLevel3Activity.KEY_TITLE, "UNKNOWN")
//                                                }
//
//                                            }
//                                        }
//                                        try {
//                                            newActivity.putExtra(KEY_TITLE, firstLetterSentences(URLDecoder.decode(targetUrl[2], "UTF-8")))
//                                        } catch (e: UnsupportedEncodingException) {
//                                            newActivity.putExtra(KEY_TITLE, "UNKNOWN")
//                                        }
//                                        startActivity(newActivity)
//                                    }
//                                    else -> Toast.makeText(applicationContext, "Maaf alamat tujuan tidak valid", Toast.LENGTH_SHORT).show()
//                                }
//                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
//                            }
//                            else -> Toast.makeText(applicationContext, "Maaf alamat tujuan tidak valid", Toast.LENGTH_SHORT).show()
//                        }
//                    } else {
//                        CustomTabViewUtil.setView(applicationContext, urlRequested)
//                    }
//                }
                return true
            }

            @TargetApi(Build.VERSION_CODES.N)
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                return overrideUrl(webView, request.url.toString())
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                view!!.loadUrl("javascript:window.onload = function(){" + WebViewJSResizerUtil.JS_RESIZER_NAME + ".changeHeight(document.querySelector('body').offsetHeight);};")
                super.onPageFinished(view, url)
            }
        }

        webView.webChromeClient = webChromeClient
        webView.webViewClient = webViewClient

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recycleViewImage)
        container_toolbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout: AppBarLayout, i: Int ->
            val heightDiff: Int = container_toolbar.totalScrollRange - toolbar.height
            val verticalOffset: Int = Math.abs(i)
            if (verticalOffset >= heightDiff && !toolbar_title.isShown) {
                toolbar_title.visibility = View.VISIBLE
            } else if (verticalOffset <= heightDiff && toolbar_title.isShown) {
                toolbar_title.visibility = View.GONE
            }
        })

        swipeRefresh.setOnRefreshListener {
            mPresenter?.setUrls(requestedURL!!)
            mPresenter?.refreshData(requestedURL!!)
        }

        val mConfig = SliderConfig.Builder()
            .position(SliderPosition.LEFT)
            .velocityThreshold(2400F)
            .distanceThreshold(.9f)
            .edge(true)
            .touchSize(dpToPx(this,32F))
            .build()
        Slider.attach(this, mConfig)
    }

    fun dpToPx(ctx: Context, dpSize: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpSize, ctx.resources.displayMetrics)
    }
    override fun showWebViewContent(data: ModelDetailArticle) {

    }

    override fun showContentDate(date: String) {
        if (TextUtils.isEmpty(date)) {
            tvTanggal.visibility = View.GONE
            return
        }
        tvTanggal.text = date
        if (!tvTanggal.isShown)
            tvTanggal.visibility = View.VISIBLE

    }

    override fun setUpContentSrcollingImages(images: List<String>?) {
        if (images == null || images.isEmpty()) {
            containerImageSlider.invisible()
        } else {
            val urlS: String = intent.getStringExtra(KEY_URL_IMAGE)
            if (images.isEmpty()) {
                containerImageSlider.visibility = View.GONE
                return
            }
            if (!containerImageSlider.isShown) {
                containerImageSlider.visibility = View.VISIBLE
            }
            recycleViewImage.isNestedScrollingEnabled = true
            recycleViewImage.setHasFixedSize(true)
            recycleViewImage.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)

            imageTransparent.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    when (event?.action) {
                        MotionEvent.ACTION_DOWN,
                        MotionEvent.ACTION_MOVE,
                        MotionEvent.ACTION_POINTER_DOWN -> {
                            nestedScreollView.requestDisallowInterceptTouchEvent(true)
                        }
                        MotionEvent.ACTION_UP,
                        MotionEvent.ACTION_CANCEL -> {
                            nestedScreollView.requestDisallowInterceptTouchEvent(false)
                        }
                        else -> {

                        }
                    }
                    return false
                }
            })

            mAdapter = ImageAdapter(images)
            recycleViewImage.adapter = mAdapter

            if (!TextUtils.isEmpty(urlS) && URLUtil.isValidUrl(urlS)) {
                imageView.visibility = View.VISIBLE
                Picasso.get()
                        .load(images[0])
                        .placeholder(R.color.colorPrimaryDark)
                        .error(R.color.colorPrimaryDark)
                        .into(imageView)
            } else {
                if (!toolbar_title.isShown)
                    toolbar_title.visibility = View.VISIBLE
                imageView.visibility = View.GONE
                viewShadowGradient.alpha = 0f
            }
        }
    }

    override fun getUrl(): String {
        return requestedURL!!
    }

    override fun showOnError(code: Int) {
        val message = when (code) {
            500 -> {
                "Kesalahan pada server"
            }
            404 -> {
                "Data yang anda minta tidak ditemukan"
            }

            else -> {
                "Terjadi kesalahan -> " + code
            }
        }

        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

    }

    override fun setPresenter(presenter: DetailContracts.Presenter) {
        mPresenter = presenter
    }


    override fun showSwipeIndicator(show: Boolean) {
//        swipeRefresh
        swipeRefresh.post {
            swipeRefresh.isRefreshing = show
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
    }

    override fun loadFromUrlGiven(url: String) {
        if (TextUtils.isEmpty(url))
            webView.invisible()
        else {
            webView.visible()
            webView.loadUrl(url)
        }
    }

    override fun setTitle(title: String) {
        if (TextUtils.isEmpty(title))
        else {
            toolbar_title.text = title
            tvTitle.text = title
        }
    }

    override fun onStart() {
        super.onStart()
        mPresenter?.setUrls(requestedURL!!)
        mPresenter?.start()
    }
}