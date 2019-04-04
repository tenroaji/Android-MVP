package lagecong.com.mvp.data.model

import com.google.gson.annotations.SerializedName

class ModelDetailResponse {
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("image")
    var image: List<String>? = null

    @SerializedName("published")
    var published: String? = null

    @SerializedName("category")
    var category: String? = null

    @SerializedName("content_uri")
    var content: String? = null
}