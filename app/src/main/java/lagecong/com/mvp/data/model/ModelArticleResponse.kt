package lagecong.com.mvp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ModelArticleResponse {
    @SerializedName("id_target")
    @Expose
    var id_target: Int? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("image")
    @Expose
    var image: String? = null
    @SerializedName("published")
    @Expose
    var published: String? = null
    @SerializedName("category")
    @Expose
    var category: String? = null
    @SerializedName("content")
    @Expose
    var content: String? = null

}
