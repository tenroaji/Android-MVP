package lagecong.com.mvp.data.datasources.Artikel

import android.content.Context
import android.text.TextUtils
import android.util.Log
import id.co.dgip.data.rest.ApiInterface
import lagecong.com.mvp.R
import lagecong.com.mvp.data.model.ModelArticle
import lagecong.com.mvp.data.model.ModelArticleResponse
import lagecong.com.mvp.utils.RetrofitUtils
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ArtikelDSRemote private constructor(private val mBaseUrl: String, private val mContext: Context) :
    ArtikelDS {

    override fun loadArtikel(page: Int, category: String, callback: ArtikelDS.LoadArtikelCallback) {
        val retrofit = RetrofitUtils.getInstanceWithLoggingInterceptor(mBaseUrl, HttpLoggingInterceptor.Level.HEADERS, TIMEOUT.toLong())
        Log.e("data", "$category $page")
        val api = retrofit.create(ApiInterface::class.java)
        val parameters = HashMap<String, String>()
        val mUrl = "posts?category=$category&page=$page"
        parameters[mContext.resources.getString(R.string.app_dtcms_param)] = mContext.resources.getString(R.string.app_dtcms_key)
        val modelCall = api.getArticle(mUrl, parameters)
        modelCall.enqueue(object : Callback<ModelArticle> {
            override fun onResponse(call: Call<ModelArticle>, response: Response<ModelArticle>) {
                if (response.code() == 200 && response.body() != null) {
                    val diagnosticModel = response.body()!!.diagnostic!!
                    val code = diagnosticModel.code
                    when (code) {
                        200 -> {
                            val articleResponse = response.body()!!.response!!
                            /*List<ModelArticleResponse> mListArticle=new ArrayList<>();*/
                            if (!articleResponse.isEmpty()) {
                                callback.onSuccess(articleResponse, response.body()!!.pagination, "remote")
                            } else {
                                callback.onError(404, "empty", "remote")
                            }
                        }
                        404, 400, 401 -> callback.onError(code, null, "remote")
                        else -> callback.onError(0, "Terjadi kesalahan internal", "remote")
                    }

                } else {
                    callback.onError(response.code(), "Terjadi kesalahan internal", "remote")
                }
            }

            override fun onFailure(call: Call<ModelArticle>, t: Throwable) {
                if (TextUtils.isEmpty(t.message) && t.message!!.contains("connection")) {
                    callback.onError(0, "Maaf, periksa konektivitas Anda", "remote")
                } else if (TextUtils.isEmpty(t.message) && t.message!!.contains("timeout")) {
                    callback.onError(0, "Maaf, timeout", "remote")
                } else {
                    callback.onError(0, "Terjadi kesalahan internal", "remote")
                }
            }
        })
    }

    override fun getArtikelById(url: String, callback: ArtikelDS.GetArtikelCallback) {

    }

    override fun saveArtikel(artikel: ModelArticleResponse) {

    }

    override fun saveArtikels(artikels: List<ModelArticleResponse>) {

    }

    override fun deleteAllArtikel() {

    }


    override fun getLastPage(): Int {
        return 0
    }

    override fun refreshData() {

    }

    companion object {
        private val TIMEOUT = 30000
        private var INSTANCE: ArtikelDSRemote? = null

        fun getInstance(baseUrl: String, context: Context): ArtikelDSRemote {
            return INSTANCE ?: ArtikelDSRemote(baseUrl, context)
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}