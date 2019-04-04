package lagecong.com.mvp.data.datasources.ArtikelDetail

import android.content.Context
import id.co.dgip.data.datasource.DetailArticleDS
import id.co.dgip.data.rest.ApiClient
import id.co.dgip.data.rest.ApiInterface
import lagecong.com.mvp.R
import lagecong.com.mvp.data.model.ModelDetailArticle

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailArticleRemote (baseUrl : String, context : Context) : DetailArticleDS {

    private var mBaseUrl : String
    private val mContext: Context

    init {
        mContext = context
        mBaseUrl = baseUrl
    }


    companion object {
        private var INSTANCE : DetailArticleRemote?=null

        @JvmStatic
        fun getInstance(baseUrl : String, context : Context) : DetailArticleRemote {

            if (INSTANCE == null) {
                INSTANCE = DetailArticleRemote(baseUrl, context)
            }
            return INSTANCE!!
        }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }

    override fun LoadDetailArticle(url: String , callback: DetailArticleDS.LoadDetailArticleCallback) {
        val mApi : ApiInterface = ApiClient.getClient(mContext.resources.getString(R.string.app_url)).create(ApiInterface::class.java)
        val param = HashMap<String, String>()
        param[mContext.resources.getString(R.string.app_dtcms_param)]=mContext.resources.getString(R.string.app_dtcms_key)
        val getDetail : Call<ModelDetailArticle> = mApi.getDetailArticle(url, param)
        getDetail.enqueue(object : Callback<ModelDetailArticle>{
            override fun onResponse(call: Call<ModelDetailArticle>, response: Response<ModelDetailArticle>) {
                if (response.isSuccessful){
                    if(response.body()!=null)
                        callback.onSuccess(response.body()!!)
                    else callback.onError(0)
                } else callback.onError(response.body()!!.diagnostic.code!!)
            }

            override fun onFailure(call: Call<ModelDetailArticle>, t: Throwable) {
                callback.onError(400)
            }

        })
    }

    override fun saveDetail(data: ModelDetailArticle, id : String) {
    }

    override fun deleteLocal() {
    }

}