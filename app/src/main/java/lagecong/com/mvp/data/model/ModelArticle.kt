package lagecong.com.mvp.data.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ModelArticle {
    @SerializedName("pagination")
    @Expose
    var pagination: ModelPagination? = null
    @SerializedName("response")
    @Expose
    var response: List<ModelArticleResponse>? = null
    @SerializedName("diagnostic")
    @Expose
    var diagnostic: ModelDiagnostic? = null


}
