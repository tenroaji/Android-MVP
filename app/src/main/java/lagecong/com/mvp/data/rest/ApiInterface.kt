package id.co.dgip.data.rest

import lagecong.com.mvp.data.model.ModelArticle
import lagecong.com.mvp.data.model.ModelDetailArticle
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Url

interface ApiInterface {
    @GET
    fun getArticle(@Url url: String, @HeaderMap parameters: Map<String, String>): Call<ModelArticle>

    @GET
    fun getDetailArticle(@Url url: String, @HeaderMap parameters: Map<String, String>): Call<ModelDetailArticle>



//    @get:GET
//    val posts:Observable<List<ModelChatUrl>
}
